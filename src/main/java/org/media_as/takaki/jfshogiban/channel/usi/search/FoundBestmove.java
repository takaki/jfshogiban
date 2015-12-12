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

package org.media_as.takaki.jfshogiban.channel.usi.search;

import java.util.concurrent.BlockingQueue;

public final class FoundBestmove implements EndSearchState {

    private final String bestmove;

    public FoundBestmove(final String bestmove) {
        this.bestmove = bestmove;
    }

    @Override
    public String getMessage() {
        return bestmove;
    }

    @Override
    public BestmoveState next(final BlockingQueue<String> in) {
        throw new RuntimeException("FoundBestmove should not call");
    }
}
