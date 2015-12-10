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

package org.media_as.takaki.jfshogiban;


import org.media_as.takaki.jfshogiban.piece.IPiece;
import org.media_as.takaki.jfshogiban.tostr.IStringConverter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class Mochigoma {
    private static final List<IPiece> SENTE_LIST = Arrays
            .asList(Koma.SENTE_FU, Koma.SENTE_KYOSHA, Koma.SENTE_KEIMA,
                    Koma.SENTE_GIN, Koma.SENTE_KIN, Koma.SENTE_KAKU,
                    Koma.SENTE_HISYA);
    private static final List<IPiece> GOTE_LIST = Arrays
            .asList(Koma.GOTE_FU, Koma.GOTE_KYOSHA, Koma.GOTE_KEIMA,
                    Koma.GOTE_GIN, Koma.GOTE_KIN, Koma.GOTE_KAKU,
                    Koma.GOTE_HISYA);

    private static final List<IPiece> MOCHIGOMA;

    static {
        MOCHIGOMA = new ArrayList<>(SENTE_LIST);
        MOCHIGOMA.addAll(GOTE_LIST);
    }

    private final Map<IPiece, Integer> mochigoma;

    public static Mochigoma initialize() {
        return new Mochigoma(MOCHIGOMA.stream()
                .collect(Collectors.toMap(Function.identity(), koma -> 0)));
    }

    public Mochigoma(final Map<IPiece, Integer> mochigoma) {
        this.mochigoma = Collections.unmodifiableMap(mochigoma);
    }

    public Mochigoma push(final IPiece koma) throws IllegalMoveException {
        if (!MOCHIGOMA.contains(koma)) {
            //noinspection HardCodedStringLiteral
            throw new IllegalMoveException(
                    String.format("Can't push %s to mochigoma.", koma));
        }
        final Map<IPiece, Integer> komaMap = new HashMap<>(mochigoma);
        komaMap.computeIfPresent(koma, (p, n) -> n + 1);
        return new Mochigoma(komaMap);
    }

    public Mochigoma remove(final IPiece koma) throws IllegalMoveException {
        if (count(koma) <= 0) {
            //noinspection HardCodedStringLiteral
            throw new IllegalMoveException(String.format("%s is empty.", koma));
        }
        final Map<IPiece, Integer> mochigoma = new HashMap<>(this.mochigoma);
        mochigoma.computeIfPresent(koma, (p, n) -> n - 1);
        return new Mochigoma(mochigoma);
    }

    public int count(final IPiece koma) {
        return mochigoma.getOrDefault(koma, 0);
    }

    public String convertString(final IStringConverter converter) {
        return converter.convertMochigoma(this);
    }

    private static final Map<Character, IPiece> SFEN_PIECE = new HashMap<>(14);

    static {
        SFEN_PIECE.put('P', Koma.SENTE_FU);
        SFEN_PIECE.put('L', Koma.SENTE_KYOSHA);
        SFEN_PIECE.put('N', Koma.SENTE_KEIMA);
        SFEN_PIECE.put('S', Koma.SENTE_GIN);
        SFEN_PIECE.put('G', Koma.SENTE_KIN);
        SFEN_PIECE.put('B', Koma.SENTE_KAKU);
        SFEN_PIECE.put('R', Koma.SENTE_HISYA);

        SFEN_PIECE.put('p', Koma.GOTE_FU);
        SFEN_PIECE.put('l', Koma.GOTE_KYOSHA);
        SFEN_PIECE.put('n', Koma.GOTE_KEIMA);
        SFEN_PIECE.put('s', Koma.GOTE_GIN);
        SFEN_PIECE.put('g', Koma.GOTE_KIN);
        SFEN_PIECE.put('b', Koma.GOTE_KAKU);
        SFEN_PIECE.put('r', Koma.GOTE_HISYA);
    }

    // FIXME: ugly
    public static Mochigoma sfen(
            final String sfen) throws IllegalMoveException {
        Mochigoma mochigoma = Mochigoma.initialize();
        for (int i = 0; i < sfen.length(); i++) {
            char c = sfen.charAt(i);
            final int n;
            if (Character.isDigit(c)) {
                n = Character.getNumericValue(c);
                i++;
                c = sfen.charAt(i);
            } else {
                n = 1;
            }
            for (int j = 0; j < n; j++) {
                if (!SFEN_PIECE.containsKey(c)) {
                    throw new IllegalMoveException("Unknown=" + c);
                }
                mochigoma = mochigoma.push(SFEN_PIECE.get(c));
            }
        }
        return mochigoma;

    }
}
