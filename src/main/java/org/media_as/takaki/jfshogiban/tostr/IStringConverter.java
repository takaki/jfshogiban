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

package org.media_as.takaki.jfshogiban.tostr;

import org.media_as.takaki.jfshogiban.core.Mochigoma;
import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.core.ShogiBan;
import org.media_as.takaki.jfshogiban.piece.BasePiece;
import org.media_as.takaki.jfshogiban.piece.IPiece;

public interface IStringConverter {

    String convertPlayer(Player player);

    String convertPiece(BasePiece piece);

    String convertMochigoma(Mochigoma mochigoma);

    String convertBanmen(ShogiBan shogiBan, Mochigoma mochigoma);

    String convertShogiban(ShogiBan shogiban);

    String convertKyokumen(ShogiBan shogiBan, Mochigoma mochigoma, Player turn);

    String convertPromoteMove(int fx, int fy, int tx, int ty);

    String convertNormalMove(int fx, int fy, int tx, int ty);

    String convertDropMove(int tx, int ty, IPiece koma);
}
