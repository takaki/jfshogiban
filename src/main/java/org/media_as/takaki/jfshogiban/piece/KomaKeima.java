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

import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.core.ShogiBan;

public final class KomaKeima extends AbstractPiece implements IPiece {
    public KomaKeima(final Player owner) {
        super(owner);
    }

    @Override
    public boolean canSet(final int x, final int y, final ShogiBan banmen) {
        return isOwner(Player.SENTEBAN) && y >= 3 || isOwner(
                Player.GOTEBAN) && y <= 7;
    }

    @Override
    public KomaNarikei promotion() {
        return new KomaNarikei(getOwner());
    }

    @Override
    public boolean checkMove(final int fx, final int fy, final int tx,
                             final int ty, final ShogiBan shogiBan) {
        return Math.abs(fx - tx) == 1 && (fy - ty) * sign() == 2;
    }

    @Override
    public KomaKeima captured(final Player owner) {
        return new KomaKeima(owner);
    }


}
