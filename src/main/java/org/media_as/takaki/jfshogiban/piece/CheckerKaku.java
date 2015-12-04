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

public interface CheckerKaku {
    default boolean checkKakuMove(final int fx, final int fy, final int tx,
                                  final int ty,
                                  final Banmen banmen) throws IllegalMoveException {
        final int diffX = fx > tx ? -1 : 1;
        final int diffY = fy > ty ? -1 : 1;
        return Math.abs(fx - tx) == Math.abs(fy - ty) && IntStream
                .rangeClosed(1, Math.abs(fx - tx)).allMatch(diff -> {
                    try {
                        return banmen
                                .isEmpty(fx + diff * diffX, fy + diff * diffY);
                    } catch (final IllegalMoveException ignored) {
                        return false;
                    }
                });
    }
}
