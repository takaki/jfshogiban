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

package org.media_as.takaki.jfshogiban.protocol.usi;

import org.media_as.takaki.jfshogiban.Koma;
import org.media_as.takaki.jfshogiban.Player;
import org.media_as.takaki.jfshogiban.piece.IPiece;

import java.util.HashMap;
import java.util.Map;

public class Sfen {
    public static final Map<IPiece, String> PIECE_SFEN = new HashMap<>();

    static {
        PIECE_SFEN.put(Koma.SENTE_FU, "P");
        PIECE_SFEN.put(Koma.SENTE_KYOSHA, "L");
        PIECE_SFEN.put(Koma.SENTE_KEIMA, "N");
        PIECE_SFEN.put(Koma.SENTE_GIN, "S");
        PIECE_SFEN.put(Koma.SENTE_KIN, "G");
        PIECE_SFEN.put(Koma.SENTE_KAKU, "B");
        PIECE_SFEN.put(Koma.SENTE_HISYA, "R");
        PIECE_SFEN.put(Koma.SENTE_GYOKU, "K");
        PIECE_SFEN.put(Koma.SENTE_TOKIN, "+P");
        PIECE_SFEN.put(Koma.SENTE_NARIKYO, "+L");
        PIECE_SFEN.put(Koma.SENTE_NARIKEI, "+N");
        PIECE_SFEN.put(Koma.SENTE_NARIGIN, "+S");
        PIECE_SFEN.put(Koma.SENTE_UMA, "+B");
        PIECE_SFEN.put(Koma.SENTE_RYU, "+R");

        PIECE_SFEN.put(Koma.GOTE_FU, "p");
        PIECE_SFEN.put(Koma.GOTE_KYOSHA, "l");
        PIECE_SFEN.put(Koma.GOTE_KEIMA, "n");
        PIECE_SFEN.put(Koma.GOTE_GIN, "s");
        PIECE_SFEN.put(Koma.GOTE_KIN, "g");
        PIECE_SFEN.put(Koma.GOTE_KAKU, "n");
        PIECE_SFEN.put(Koma.GOTE_HISYA, "r");
        PIECE_SFEN.put(Koma.GOTE_GYOKU, "k");
        PIECE_SFEN.put(Koma.GOTE_TOKIN, "+p");
        PIECE_SFEN.put(Koma.GOTE_NARIKYO, "+l");
        PIECE_SFEN.put(Koma.GOTE_NARIKEI, "+n");
        PIECE_SFEN.put(Koma.GOTE_NARIGIN, "+s");
        PIECE_SFEN.put(Koma.GOTE_UMA, "+b");
        PIECE_SFEN.put(Koma.GOTE_RYU, "+r");
    }

}
