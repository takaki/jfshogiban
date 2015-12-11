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

package org.media_as.takaki.jfshogiban.protocol.usi.init;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public final class WaitUsiOK implements UsiState {
    private static final Logger LOG = LoggerFactory.getLogger(WaitUsiOK.class);
    private final String name;

    public WaitUsiOK(final String name) {
        this.name = name;
    }

    @SuppressWarnings("MethodWithMultipleReturnPoints")
    @Override
    public UsiState next(final BlockingQueue<String> out,
                         final BlockingQueue<String> in) throws InterruptedException {
        final String line = in.take();
        if (StringUtils.equals(line, "usiok")) {
            out.add("isready");
            return new WaitReadyok(name);
        } else {
            return line.startsWith("id name") ? new WaitUsiOK(
                    line.split(" ")[2]) : new WaitUsiOK(name);
        }
    }
}
