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

package org.media_as.takaki.jfshogiban.piece;

import org.media_as.takaki.jfshogiban.Banmen;
import org.media_as.takaki.jfshogiban.IllegalMoveException;

import java.util.stream.IntStream;

public interface CheckerHisha {
    default boolean checkHishaMove(final int fx, final int fy, final int tx,
                                   final int ty, final Banmen banmen) {
        return fx == tx && IntStream.rangeClosed(1, Math.abs(fy - ty) - 1)
                .allMatch(diff -> {
                    try {
                        return banmen
                                .isEmpty(fx, fy + diff * (fy > ty ? -1 : 1));
                    } catch (final IllegalMoveException ignored) {
                        return false;
                    }
                }) || fy == ty && IntStream
                .rangeClosed(1, Math.abs(fx - tx) - 1).allMatch(diff -> {
                    try {
                        return banmen
                                .isEmpty(fx + diff * (fx > tx ? -1 : 1), fy);
                    } catch (final IllegalMoveException ignored) {
                        return false;
                    }
                });
    }

}