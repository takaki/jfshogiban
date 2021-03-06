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
import org.media_as.takaki.jfshogiban.exception.IllegalMoveException;

public final class KomaGyoku extends AbstractPiece implements IPiece, CheckerGyoku {
    public KomaGyoku(final Player owner) {
        super(owner);
    }

    @Override
    public KomaGyoku captured(final Player owner) {
        return new KomaGyoku(owner);
    }

    @Override
    public KomaGyoku promotion() {
        throw new IllegalMoveException("Gyoku can't promotion.");
    }

    @Override
    public boolean checkMove(final int fx, final int fy, final int tx,
                             final int ty, final ShogiBan shogiBan) {
        // 王取りのチェックをどこかに
        return checkGyokuMove(fx, fy, tx, ty);
    }


}
