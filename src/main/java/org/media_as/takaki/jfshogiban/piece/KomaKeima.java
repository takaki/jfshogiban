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
import org.media_as.takaki.jfshogiban.Player;

public final class KomaKeima extends BasePiece {
    public KomaKeima(final Player owner) {
        super(owner);
    }

    @Override
    public boolean canSet(final int y) {
        return !(owner == Player.SENTEBAN && y <= 2 || owner == Player.GOTEBAN && y >= 8);
    }

    @Override
    public KomaNarikei promotion() {
        return new KomaNarikei(owner);
    }

    @Override
    public boolean isKeepRule(int fx, int fy, int tx, int ty, Banmen banmen) throws IllegalMoveException {
        if (Math.abs(fx - tx) == 1 && (fy - ty) * owner.sign() == 2) {
            return true;
        }
        return false;
    }

    @Override
    public KomaKeima captured(final Player owner) {
        return new KomaKeima(owner);
    }

    @Override
    public String toString() {
        return toCSA("KE");
    }

}
