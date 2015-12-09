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

import java.io.PrintStream;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class WaitBestmove {

    private final Optional<String> bestmove;

    public WaitBestmove(final Optional<String> bestmove) {
        this.bestmove = bestmove;
    }

    public void sendPosition(final PrintStream out, final String position) {
        out.println("position startpos moves 7g7f"); // TODO
        out.println("go");
        out.flush();
    }

    public WaitBestmove readResponse(final PrintStream out,
                                     final BlockingQueue<String> in) throws InterruptedException {
        if(bestmove.isPresent()) {
            return null; // TODO
        }
        final String line = in.take();
        if (StringUtils.startsWith(line, "bestmove")) {
            System.out.println(getClass() + "<" + line);
            final String[] split = line.split(" ");
            return new WaitBestmove(Optional.of(split[1]));
        } else {
            System.out.println(getClass() + "<" + line);
            return new WaitBestmove(Optional.empty());

        }
    }

    public Optional<String> getBestMove() {
        System.out.println(
                getClass() + (bestmove.isPresent() ? ":bestmove is " + bestmove
                        .get() : ":empty"));
        return bestmove;
    }
}
