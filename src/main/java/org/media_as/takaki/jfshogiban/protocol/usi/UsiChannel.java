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

import org.apache.commons.lang3.StringUtils;
import org.media_as.takaki.jfshogiban.core.Kyokumen;
import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.move.*;
import org.media_as.takaki.jfshogiban.piece.*;
import org.media_as.takaki.jfshogiban.protocol.IMoveChannel;
import org.media_as.takaki.jfshogiban.protocol.usi.init.EndState;
import org.media_as.takaki.jfshogiban.protocol.usi.init.FinishFail;
import org.media_as.takaki.jfshogiban.protocol.usi.init.StartUsi;
import org.media_as.takaki.jfshogiban.protocol.usi.init.UsiState;
import org.media_as.takaki.jfshogiban.protocol.usi.search.BestmoveState;
import org.media_as.takaki.jfshogiban.protocol.usi.search.EndSearchState;
import org.media_as.takaki.jfshogiban.protocol.usi.search.WaitBestmove;
import org.media_as.takaki.jfshogiban.tostr.IStringConverter;
import org.media_as.takaki.jfshogiban.tostr.SfenConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public final class UsiChannel implements IMoveChannel {
    private static final Logger LOG = LoggerFactory.getLogger(UsiChannel.class);

    private final BlockingQueue<String> in = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> out = new LinkedBlockingQueue<>();
    private final String name; // FIXME

    public UsiChannel(final Path directory,
                      final String exe) throws IOException {
        final ProcessBuilder processBuilder = new ProcessBuilder(
                directory.resolve(exe).toString());
        processBuilder.directory(directory.toFile());
        final Process start = processBuilder.start();

        final ExecutorService ses = Executors.newFixedThreadPool(2);
        ses.execute(() -> {
            try (Scanner scanner = new Scanner(start.getInputStream())) {
                while (scanner.hasNextLine()) {
                    final String add = scanner.nextLine();
                    LOG.debug("< {}", add);
                    in.add(add);
                }
            }
        });
        ses.execute(() -> {
            try (final PrintStream writer = new PrintStream(
                    new BufferedOutputStream(start.getOutputStream()))) {
                while (true) {
                    final String take = out.take();
                    LOG.debug("> {}", take);
                    writer.println(take);
                    writer.flush();
                }
            } catch (final InterruptedException e) {
                throw new RuntimeException(e); // FIXME
            }
        });

        final Stream<UsiState> iterate = Stream
                .iterate(new StartUsi(), state0 -> {
                    try {
                        return state0.next(out, in);
                    } catch (final InterruptedException e) {
                        return new FinishFail(e);
                    }
                });
        final UsiState first = iterate
                .filter(usiState -> usiState instanceof EndState).findFirst()
                .get();
        name = ((EndState) first).getMessage();
    }

    @Override
    public IMovement getMovement(final Kyokumen kyokumen) {
        final IStringConverter converter = new SfenConverter();
        final String sfen = kyokumen.convertString(converter);
        final String position = String.join(" ", "position sfen", sfen);
        out.add(position);
        out.add("go byoyomi 1000");
        final Stream<BestmoveState> iterate = Stream
                .iterate(new WaitBestmove(), state0 -> {
                    try {
                        return state0.next(in);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e); // FIXME
                        // return new EndSearchState????
                    }
                });
        final BestmoveState found = iterate
                .filter(usiState -> usiState instanceof EndSearchState)
                .findFirst().get();
        final String bestmove = found.getMessage();
        return toMovement(bestmove, kyokumen.getTurn());
    }

    private static IMovement toMovement(final String bestmove,
                                        final Player turn) {
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
                    throw new RuntimeException(
                            "FIX ME: bestmove is " + bestmove);
            }
        }
    }

    @Override
    public String getPlayerName() {
        return String.join(":", getClass().getSimpleName(), name);
    }
}
