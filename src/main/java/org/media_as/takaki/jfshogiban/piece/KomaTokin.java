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

public final class KomaTokin extends AbstractPiece implements IPiece, CheckerKin {

    public KomaTokin(final Player owner) {
        super(owner);
    }

    @Override
    public KomaFu captured(final Player owner) {
        return new KomaFu(owner);
    }

    @Override
    public KomaTokin promotion() {
        return new KomaTokin(getOwner());
    }

    @Override
    public boolean checkMove(final int fx, final int fy, final int tx,
                             final int ty, final ShogiBan shogiBan) {
        return checkRuleKin(fx, fy, tx, ty, sign());
    }

}
