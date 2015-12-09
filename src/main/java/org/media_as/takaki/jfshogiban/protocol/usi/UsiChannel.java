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
import org.media_as.takaki.jfshogiban.IllegalMoveException;
import org.media_as.takaki.jfshogiban.PlayMove;
import org.media_as.takaki.jfshogiban.Player;
import org.media_as.takaki.jfshogiban.action.IMovement;
import org.media_as.takaki.jfshogiban.action.NormalMove;
import org.media_as.takaki.jfshogiban.protocol.IMoveChannel;
import org.media_as.takaki.jfshogiban.protocol.usi.init.EndInit;
import org.media_as.takaki.jfshogiban.protocol.usi.init.InitUsi;
import org.media_as.takaki.jfshogiban.protocol.usi.init.UsiState;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("ClassNamePrefixedWithPackageName")
public class UsiChannel implements IMoveChannel {

    private final PrintStream out;
    private final BlockingQueue<String> in = new LinkedBlockingQueue<>();

    public UsiChannel() throws IOException {
        final ProcessBuilder processBuilder = new ProcessBuilder(
                "/home/takaki/tmp/gpsfish/src/gpsfish");
        final Process start = processBuilder.start();

        out = new PrintStream(
                new BufferedOutputStream(start.getOutputStream()));

        final ScheduledExecutorService ses = Executors
                .newSingleThreadScheduledExecutor();
        ses.schedule(() -> {
            try (Scanner scanner = new Scanner(start.getInputStream())) {
                while (scanner.hasNextLine()) {
                    in.add(scanner.nextLine());
                }
            }
        }, 0, TimeUnit.MILLISECONDS);

        final UsiState state;
        final Stream<UsiState> iterate = Stream
                .iterate(new InitUsi(), state0 -> {
                    try {
                        return state0.readResponse(out, in);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });
        StreamUtils.takeUntil(iterate,
                state0 -> state0.getClass().equals(EndInit.class))
                .reduce((a, b) -> b);
    }

    @Override
    public IMovement getMovement(PlayMove playMove,
                                 Player player) throws IllegalMoveException {
        final String position = "";
        final WaitBestmove waitBestmove = new WaitBestmove(Optional.empty());
        waitBestmove.sendPosition(out, position);
        final Stream<WaitBestmove> iterate = Stream
                .iterate(waitBestmove, state0 -> {
                    try {
                        return state0.readResponse(out, in);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });
//        final Optional<String> bestmove = StreamUtils
//                .takeUntil(iterate, state0 -> state0.getBestMove().isPresent())
//                .reduce((a, b) -> b).get()
//                .getBestMove();

        final String bestmove = StreamUtils
                .takeUntil(iterate, state0 -> state0 == null)
                .reduce((a, b) -> b).get().getBestMove().get();
        return toMovement(bestmove);
    }

    private IMovement toMovement(String command) {
        if (Character.isDigit(command.charAt(0))) {
            int fx = Character.getNumericValue(command.charAt(0));
            int fy = Character.getNumericValue(command.charAt(1)) - 9;
            int tx = Character.getNumericValue(command.charAt(2));
            int ty = Character.getNumericValue(command.charAt(3)) - 9;
            return new NormalMove(fx, fy, tx, ty);
        } else {
            throw new RuntimeException("FIX ME");
        }
    }

    @Override
    public UsiChannel getNextChannel() {
        return this;
    }
}
