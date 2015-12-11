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

import org.apache.commons.lang3.StringUtils;
import org.media_as.takaki.jfshogiban.piece.IPiece;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("MagicCharacter")
public enum SfenParser {
    ;
    private static final Map<Character, IPiece> SFEN_PIECE = new HashMap<>(14);

    static {
        SFEN_PIECE.put('P', Koma.SENTE_FU);
        SFEN_PIECE.put('L', Koma.SENTE_KYOSHA);
        SFEN_PIECE.put('N', Koma.SENTE_KEIMA);
        SFEN_PIECE.put('S', Koma.SENTE_GIN);
        SFEN_PIECE.put('G', Koma.SENTE_KIN);
        SFEN_PIECE.put('B', Koma.SENTE_KAKU);
        SFEN_PIECE.put('R', Koma.SENTE_HISYA);
        SFEN_PIECE.put('K', Koma.SENTE_GYOKU);

        SFEN_PIECE.put('p', Koma.GOTE_FU);
        SFEN_PIECE.put('l', Koma.GOTE_KYOSHA);
        SFEN_PIECE.put('n', Koma.GOTE_KEIMA);
        SFEN_PIECE.put('s', Koma.GOTE_GIN);
        SFEN_PIECE.put('g', Koma.GOTE_KIN);
        SFEN_PIECE.put('b', Koma.GOTE_KAKU);
        SFEN_PIECE.put('r', Koma.GOTE_HISYA);
        SFEN_PIECE.put('k', Koma.GOTE_GYOKU);
    }

    public static Kyokumen kyokumen(
            final String sfen) {
        final String[] token = sfen.split(" ");
        return new Kyokumen(shogiBan(token[0]), mochigoma(token[2]),
                player(token[1]));

    }

    public static Mochigoma mochigoma(
            final String input) {
        return StringUtils.equals(input, "-") ? Mochigoma
                .initialize() : mochigomaImpl(Mochigoma.initialize(), input);
    }

    private static Mochigoma mochigomaImpl(final Mochigoma mochigoma,
                                           final String input) {
        return input.isEmpty() ? mochigoma : parseMochigoma(mochigoma, input);
    }

    private static Mochigoma parseMochigoma(final Mochigoma mochigoma,
                                            final String input) {
        final char ch = input.charAt(0);
        return Character.isDigit(ch) ? mochigomaImpl(
                pushMochigoma(mochigoma, Character.getNumericValue(ch),
                        input.charAt(1)), input.substring(2)) : mochigomaImpl(
                pushMochigoma(mochigoma, 1, ch), input.substring(1));
    }

    private static Mochigoma pushMochigoma(final Mochigoma mochigoma,
                                           final int num,
                                           final char ch) {
        return num == 0 ? mochigoma : pushMochigoma(
                mochigoma.push(SFEN_PIECE.get(ch)), num - 1, ch);
    }

    public static ShogiBan shogiBan(
            final String sfen) {
        return shogiBanImpl(ShogiBan.initialize(), 9, 1, sfen);
    }

    @SuppressWarnings({"TailRecursion", "IfStatementWithTooManyBranches", "MethodWithMultipleReturnPoints", "IfMayBeConditional", "HardcodedFileSeparator"})
    private static ShogiBan shogiBanImpl(final ShogiBan shogiban, final int x,
                                         final int y,
                                         final String sfen) {
        if (sfen.isEmpty()) {
            return shogiban;
        } else {
            final char ch = sfen.charAt(0);
            if (Character.isDigit(ch)) {
                return shogiBanImpl(shogiban, x - Character.getNumericValue(ch),
                        y, sfen.substring(1));
            } else if (ch == '/') {
                return shogiBanImpl(shogiban, 9, y + 1, sfen.substring(1));
            } else if (ch == '+') {
                return shogiBanImpl(shogiban.set(x, y,
                        SFEN_PIECE.get(sfen.charAt(1)).promotion()), x - 1, y,
                        sfen.substring(2));
            } else {
                return shogiBanImpl(shogiban.set(x, y, SFEN_PIECE.get(ch)),
                        x - 1, y, sfen.substring(1));
            }
        }

    }

    public static Player player(final CharSequence sfen) {
        return StringUtils.equals(sfen, "b") ? Player.SENTEBAN : Player.GOTEBAN;
    }
}
