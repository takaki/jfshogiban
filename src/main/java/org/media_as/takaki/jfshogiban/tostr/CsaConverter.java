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

import org.apache.commons.lang3.StringUtils;
import org.media_as.takaki.jfshogiban.core.Koma;
import org.media_as.takaki.jfshogiban.core.Mochigoma;
import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.core.ShogiBan;
import org.media_as.takaki.jfshogiban.piece.BasePiece;
import org.media_as.takaki.jfshogiban.piece.IPiece;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CsaConverter extends AbstractStringConverter {

    private static final Map<IPiece, String> PIECE = new HashMap<>(28);

    static {
        PIECE.put(Koma.SENTE_FU, "+FU");
        PIECE.put(Koma.SENTE_KYOSHA, "+KY");
        PIECE.put(Koma.SENTE_KEIMA, "+KE");
        PIECE.put(Koma.SENTE_GIN, "+GI");
        PIECE.put(Koma.SENTE_KIN, "+KI");
        PIECE.put(Koma.SENTE_KAKU, "+KA");
        PIECE.put(Koma.SENTE_HISYA, "+HI");
        PIECE.put(Koma.SENTE_GYOKU, "+OU");
        PIECE.put(Koma.SENTE_TOKIN, "+TO");
        PIECE.put(Koma.SENTE_NARIKYO, "+NY");
        PIECE.put(Koma.SENTE_NARIKEI, "+NK");
        PIECE.put(Koma.SENTE_NARIGIN, "+NG");
        PIECE.put(Koma.SENTE_UMA, "+UM");
        PIECE.put(Koma.SENTE_RYU, "+RY");

        PIECE.put(Koma.GOTE_FU, "-FU");
        PIECE.put(Koma.GOTE_KYOSHA, "-KY");
        PIECE.put(Koma.GOTE_KEIMA, "-KE");
        PIECE.put(Koma.GOTE_GIN, "-GI");
        PIECE.put(Koma.GOTE_KIN, "-KI");
        PIECE.put(Koma.GOTE_KAKU, "-KA");
        PIECE.put(Koma.GOTE_HISYA, "-HI");
        PIECE.put(Koma.GOTE_GYOKU, "-OU");
        PIECE.put(Koma.GOTE_TOKIN, "-TO");
        PIECE.put(Koma.GOTE_NARIKYO, "-NY");
        PIECE.put(Koma.GOTE_NARIKEI, "-NK");
        PIECE.put(Koma.GOTE_NARIGIN, "-NG");
        PIECE.put(Koma.GOTE_UMA, "-UM");
        PIECE.put(Koma.GOTE_RYU, "-RY");

    }

    @Override
    public String convertPlayer(final Player player) {
        return player == Player.SENTEBAN ? "+" : "-";
    }

    @Override
    public String convertPiece(final BasePiece piece) {
        if (PIECE.containsKey(piece)) {
            return PIECE.get(piece);
        } else {
            throw new IllegalArgumentException("Unknown piece" + piece);
        }
    }

    @Override
    public String convertMochigoma(final Mochigoma mochigoma) {
        return String.join("", "P+", mochigomaLine(mochigoma, SENTE_LIST),
                System.lineSeparator(), "P-",
                mochigomaLine(mochigoma, GOTE_LIST), System.lineSeparator());

    }

    private String mochigomaLine(final Mochigoma mochigoma,
                                 final Collection<IPiece> list) {
        return list.stream().map(p -> StringUtils.repeat(String
                        .join("", "00", p.convertString(this).substring(1, 3)),
                mochigoma.count(p))).collect(Collectors.joining());
    }

    @Override
    public String convertBanmen(final ShogiBan shogiBan,
                                final Mochigoma mochigoma) {
        return String.join("", shogiBan.convertString(this),
                mochigoma.convertString(this));
    }

    @Override
    public String convertShogiban(final ShogiBan shogiban) {
        return IntStream.rangeClosed(1, 9).mapToObj(y -> String
                .join("", "P", Integer.toString(y), IntStream.rangeClosed(1, 9)
                        .mapToObj(x -> shogiban.get(10 - x, y)
                                .map(p -> p.convertString(this)).orElse(" * "))
                        .collect(Collectors.joining()), System.lineSeparator()))
                .collect(Collectors.joining());
    }

    @Override
    public String convertKyokumen(final ShogiBan shogiBan,
                                  final Mochigoma mochigoma,
                                  final Player turn) {
        return String.join("", shogiBan.convertString(this),
                mochigoma.convertString(this), turn.convert(this),
                System.lineSeparator());
    }

}
