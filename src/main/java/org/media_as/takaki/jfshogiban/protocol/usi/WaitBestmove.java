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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public class WaitBestmove {
    private static final Logger LOG = LoggerFactory
            .getLogger(WaitBestmove.class);
    private final Optional<String> bestmove;

    public WaitBestmove(final Optional<String> bestmove) {
        this.bestmove = bestmove;
    }

    public void sendPosition(final PrintStream out, final String position) {
        LOG.debug("> {}", position);
        out.println(position); // TODO
        out.println("go byoyomi 500");
        out.flush();
    }

    public WaitBestmove readResponse(final PrintStream out,
                                     final BlockingQueue<String> in) throws InterruptedException {
        if (bestmove.isPresent()) {
            return null; // TODO: ugly
        }
        final String line = in.take();
        if (StringUtils.startsWith(line, "bestmove")) {
            LOG.debug("< {}", line);
            final String[] split = line.split(" ");
            return new WaitBestmove(Optional.of(split[1]));
        } else {
            LOG.debug("< {}", line);
            return new WaitBestmove(Optional.empty());

        }
    }

    public Optional<String> getBestMove() {
        LOG.debug(bestmove.isPresent() ? "bestmove is " + bestmove
                .get() : "empty");
        return bestmove;
    }
}
