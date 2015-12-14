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
import org.media_as.takaki.jfshogiban.piece.AbstractPiece;
import org.media_as.takaki.jfshogiban.piece.IPiece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class SfenConverter implements IStringConverter {
    private static final Map<IPiece, String> PIECE_SFEN = new HashMap<>(28);

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
        PIECE_SFEN.put(Koma.GOTE_KAKU, "b");
        PIECE_SFEN.put(Koma.GOTE_HISYA, "r");
        PIECE_SFEN.put(Koma.GOTE_GYOKU, "k");
        PIECE_SFEN.put(Koma.GOTE_TOKIN, "+p");
        PIECE_SFEN.put(Koma.GOTE_NARIKYO, "+l");
        PIECE_SFEN.put(Koma.GOTE_NARIKEI, "+n");
        PIECE_SFEN.put(Koma.GOTE_NARIGIN, "+s");
        PIECE_SFEN.put(Koma.GOTE_UMA, "+b");
        PIECE_SFEN.put(Koma.GOTE_RYU, "+r");

    }

    private static final String[] INDEX_MAP = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};

    @Override
    public String convertPlayer(final Player player) {
        return player == Player.SENTEBAN ? "b" : "w";
    }

    @Override
    public String convertPiece(final AbstractPiece piece) {
        return PIECE_SFEN.get(piece);
    }

    @Override
    public String convertMochigoma(final Mochigoma mochigoma) {
        final Collection<IPiece> all = new ArrayList<>(Koma.SENTE_LIST);
        all.addAll(Koma.GOTE_LIST);
        final String tmp = all.stream().filter(p -> mochigoma.count(p) > 0)
                .map(p -> String.join("", mochigoma.count(p) > 1 ? Integer
                        .toString(mochigoma.count(p)) : "", PIECE_SFEN.get(p)))
                .collect(Collectors.joining());
        return tmp.isEmpty() ? "-" : tmp;
    }

    @Override
    public String convertBanmen(final ShogiBan shogiBan,
                                final Mochigoma mochigoma) {
        return String.join(":", shogiBan.convertString(this),
                mochigoma.convertString(this));
    }

    @Override
    public String convertShogiban(final ShogiBan shogiban) {
        final String tmp = IntStream.rangeClosed(1, 9).mapToObj(
                y -> IntStream.rangeClosed(1, 9).mapToObj(
                        x -> shogiban.get(10 - x, y)
                                .map(p -> p.convertString(this)).orElse("_"))
                        .collect(Collectors.joining()))
                .collect(Collectors.joining("/"));

        return IntStream.rangeClosed(1, 9).mapToObj(Integer::valueOf)
                .reduce(tmp, (s, i) -> s
                        .replace(StringUtils.repeat("_", 10 - i),
                                Integer.toString(10 - i)), (x, y) -> y);
    }

    @Override
    public String convertKyokumen(final ShogiBan shogiBan,
                                  final Mochigoma mochigoma,
                                  final Player turn) {
        return String
                .join(" ", shogiBan.convertString(this), turn.convert(this),
                        mochigoma.convertString(this), "1");
    }

    @Override
    public String convertNormalMove(final int fx, final int fy, final int tx,
                                    final int ty) {
        return String.format("%s%s%s%s", fx, INDEX_MAP[fy - 1], tx,
                INDEX_MAP[ty - 1]);
    }

    @Override
    public String convertPromoteMove(final int fx, final int fy, final int tx,
                                     final int ty) {
        return String.format("%s%s%s%s+", fx, INDEX_MAP[fy - 1], tx,
                INDEX_MAP[ty - 1]);
    }

    @Override
    public String convertDropMove(final int tx, final int ty,
                                  final IPiece koma) {
        return String.format("%s*%s%s",
                StringUtils.upperCase(koma.convertString(this)), tx,
                INDEX_MAP[ty - 1]); // FIXME
    }


}
