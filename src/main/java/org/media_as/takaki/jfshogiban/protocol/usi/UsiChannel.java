/*
 * jfshogiban: GUI Shogi playing board
 * Copyright (C) 2015 TANIGUCHI Takaki <takaki@asis.media-as.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.media_as.takaki.jfshogiban.protocol.usi;

import com.codepoetics.protonpack.StreamUtils;
import org.apache.commons.lang3.StringUtils;
import org.media_as.takaki.jfshogiban.Player;
import org.media_as.takaki.jfshogiban.action.*;
import org.media_as.takaki.jfshogiban.piece.*;
import org.media_as.takaki.jfshogiban.tostr.IStringConverter;
import org.media_as.takaki.jfshogiban.IllegalMoveException;
import org.media_as.takaki.jfshogiban.PlayMove;
import org.media_as.takaki.jfshogiban.tostr.SfenConverter;
import org.media_as.takaki.jfshogiban.protocol.IMoveChannel;
import org.media_as.takaki.jfshogiban.protocol.usi.init.EndInit;
import org.media_as.takaki.jfshogiban.protocol.usi.init.InitUsi;
import org.media_as.takaki.jfshogiban.protocol.usi.init.UsiState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Stream;

@SuppressWarnings("ClassNamePrefixedWithPackageName")
public class UsiChannel implements IMoveChannel {
    private static final Logger LOG = LoggerFactory.getLogger(UsiChannel.class);

    private final PrintStream out; // TODO wrapper
    private final BlockingQueue<String> in = new LinkedBlockingQueue<>();
    private String name; // TODO

    public UsiChannel(final Path directory,
                      final String exe) throws IOException {
//        String path ="/home/takaki/tmp/gpsfish/src/gpsfish" ;
        final String path = "/home/takaki/tmp/apery/bin/apery";
        final ProcessBuilder processBuilder = new ProcessBuilder(
                directory.resolve(exe).toString());
        processBuilder.directory(directory.toFile());
        final Process start = processBuilder.start();

        out = new PrintStream(
                new BufferedOutputStream(start.getOutputStream()));

        final ScheduledExecutorService ses = Executors
                .newSingleThreadScheduledExecutor();
        ses.schedule(() -> {
            try (Scanner scanner = new Scanner(start.getInputStream())) {
                while (scanner.hasNextLine()) {
                    final String add = scanner.nextLine();
                    LOG.debug("< {}", add);
                    in.add(add);
                }
            }
        }, 0, TimeUnit.MILLISECONDS);

        final Stream<UsiState> iterate = Stream
                .iterate(new InitUsi(this), state0 -> {
                    try {
                        return state0.readResponse(out, in);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });
        StreamUtils.takeUntil(iterate,
                state0 -> state0.getClass().equals(EndInit.class))
                .reduce((x, y) -> y);
    }

    @Override
    public IMovement getMovement(
            final PlayMove playMove) throws IllegalMoveException {
        final IStringConverter converter = new SfenConverter();
        LOG.debug(playMove.convertString(converter));
        final String position = String
                .join(" ", "position sfen", playMove.convertString(converter));
        final WaitBestmove waitBestmove = new WaitBestmove(Optional.empty());
        waitBestmove.sendPosition(out, position);
        final Stream<WaitBestmove> iterate = Stream
                .iterate(waitBestmove, state0 -> {
                    try {
                        return state0.readResponse(out, in);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

        final String bestmove = StreamUtils
                .takeUntil(iterate, state0 -> state0 == null)
                .reduce((a, b) -> b).get().getBestMove().get();
        return toMovement(bestmove, playMove.getTurn());
    }

    private IMovement toMovement(final String bestmove, final Player turn) {
        if (StringUtils.equals(bestmove, "resign")) {
            return new ResignMove();
        }
        if (Character.isDigit(bestmove.charAt(0))) {
            final int fx = Character.getNumericValue(bestmove.charAt(0));
            final int fy = Character.getNumericValue(bestmove.charAt(1)) - 9;
            final int tx = Character.getNumericValue(bestmove.charAt(2));
            final int ty = Character.getNumericValue(bestmove.charAt(3)) - 9;
            // FIXME: check '+'
            return bestmove.length() > 4 ? new PromoteMove(fx, fy, tx,
                    ty) : new NormalMove(fx, fy, tx, ty);
        } else {
            final char c = bestmove.charAt(0);
            final int tx = Character.getNumericValue(bestmove.charAt(2));
            final int ty = Character.getNumericValue(bestmove.charAt(3)) - 9;
            switch (c) {
                case 'P':
                    return new DropMove(tx, ty, new KomaFu(turn));
                case 'L':
                    return new DropMove(tx, ty, new KomaKyosha(turn));
                case 'N':
                    return new DropMove(tx, ty, new KomaKeima(turn));
                case 'S':
                    return new DropMove(tx, ty, new KomaGin(turn));
                case 'G':
                    return new DropMove(tx, ty, new KomaKin(turn));
                case 'B':
                    return new DropMove(tx, ty, new KomaKaku(turn));
                case 'R':
                    return new DropMove(tx, ty, new KomaHisha(turn));
                default:
                    throw new RuntimeException("FIX ME: bestmove is " + bestmove);
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return String.join(":", getClass().getSimpleName(), name);
    }
}
