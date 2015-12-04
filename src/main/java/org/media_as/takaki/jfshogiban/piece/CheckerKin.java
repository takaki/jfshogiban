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

import org.media_as.takaki.jfshogiban.IllegalMoveException;
import org.media_as.takaki.jfshogiban.Player;

/**
 * Created by takaki on 15/12/04.
 */
public interface CheckerKin {
    default boolean checkRuleKin(int fx, int fy, int tx, int ty,
                                 Player owner) throws IllegalMoveException

    {
        if (Math.abs(fx - ty) <= 1 && fy - ty == owner.sign()) {
            return true;
        }
        if (Math.abs(fx - ty) == 1 && fy == ty) {
            return true;
        }
        return fx - ty == 0 && fy - ty == -owner.sign();

    }

}
