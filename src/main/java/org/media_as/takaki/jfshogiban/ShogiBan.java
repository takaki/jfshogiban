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
import java.util.stream.Collector;
import java.util.stream.IntStream;

@SuppressWarnings("HardCodedStringLiteral")
public final class ShogiBan {
    // 1-index
    public static final int HEIGHT = 9;
    public static final int WIDTH = 9;

    @SuppressWarnings("FieldNotUsedInToString")
    private final List<Optional<IPiece>> board;

    public static ShogiBan startPosition() {
        return initialize().set(1, 1, Koma.GOTE_KYOSHA)
                .set(2, 1, Koma.GOTE_KEIMA).set(3, 1, Koma.GOTE_GIN)
                .set(4, 1, Koma.GOTE_KIN).set(5, 1, Koma.GOTE_GYOKU)
                .set(6, 1, Koma.GOTE_KIN).set(7, 1, Koma.GOTE_GIN)
                .set(8, 1, Koma.GOTE_KEIMA).set(9, 1, Koma.GOTE_KYOSHA)
                .set(2, 2, Koma.GOTE_KAKU).set(8, 2, Koma.GOTE_HISYA)
                .set(1, 3, Koma.GOTE_FU).set(2, 3, Koma.GOTE_FU)
                .set(3, 3, Koma.GOTE_FU).set(4, 3, Koma.GOTE_FU)
                .set(5, 3, Koma.GOTE_FU).set(6, 3, Koma.GOTE_FU)
                .set(7, 3, Koma.GOTE_FU).set(8, 3, Koma.GOTE_FU)
                .set(9, 3, Koma.GOTE_FU).set(1, 7, Koma.SENTE_FU)
                .set(2, 7, Koma.SENTE_FU).set(3, 7, Koma.SENTE_FU)
                .set(4, 7, Koma.SENTE_FU).set(5, 7, Koma.SENTE_FU)
                .set(6, 7, Koma.SENTE_FU).set(7, 7, Koma.SENTE_FU)
                .set(8, 7, Koma.SENTE_FU).set(9, 7, Koma.SENTE_FU)
                .set(1, 9, Koma.SENTE_KYOSHA).set(2, 9, Koma.SENTE_KEIMA)
                .set(3, 9, Koma.SENTE_GIN).set(4, 9, Koma.SENTE_KIN)
                .set(5, 9, Koma.SENTE_GYOKU).set(6, 9, Koma.SENTE_KIN)
                .set(7, 9, Koma.SENTE_GIN).set(8, 9, Koma.SENTE_KEIMA)
                .set(9, 9, Koma.SENTE_KYOSHA).set(8, 8, Koma.SENTE_KAKU)
                .set(2, 8, Koma.SENTE_HISYA);
    }

    public static ShogiBan initialize() {
        final List<Optional<IPiece>> board = new ArrayList<>(WIDTH * HEIGHT);
        IntStream.range(0, HEIGHT * WIDTH)
                .forEach(i -> board.add(Optional.empty()));
        return new ShogiBan(board);
    }

    private ShogiBan(final List<Optional<IPiece>> board) {
        this.board = Collections.unmodifiableList(board);
    }

    public Optional<IPiece> get(final int x, final int y) {
        return board.get(calcIndex(x, y));
    }

    public boolean isEmpty(final int x, final int y) {
        return !get(x, y).isPresent();
    }

    public ShogiBan set(final int x, final int y, final IPiece koma) {
        final List<Optional<IPiece>> board = new ArrayList<>(this.board);
        board.set(calcIndex(x, y), Optional.of(koma));
        return new ShogiBan(board);
    }

    public ShogiBan remove(final int x, final int y) {
        final List<Optional<IPiece>> board = new ArrayList<>(this.board);
        board.set(calcIndex(x, y), Optional.empty());
        return new ShogiBan(board);
    }

    // 3四 is 19.
    private static int calcIndex(final int x, final int y) {
        if (x < 1 || x > 9 || y < 1 || y > 9) {
            throw new IllegalArgumentException(
                    String.format("Out of bound x = %d, y = %d", x, y));
        }
        return x - 1 + (y - 1) * HEIGHT;
    }

    public String convertString(final IStringConverter converter) {
        return converter.convertShogiban(this);
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


    // FIXME: ugly
    public static ShogiBan sfen(final String sfen) throws IllegalMoveException {
        int x = 9, y = 1;
        ShogiBan shogiBan = ShogiBan.initialize();
        for (int i = 0; i < sfen.length(); i++) {
            char ch = sfen.charAt(i);
            boolean promote = false;
            if (Character.isDigit(ch)) {
                int n = Character.getNumericValue(ch);
                x -= n;
            } else if (ch == '/') {
                x = 9;
                y++;
            } else {
                if (ch == '+') {
                    i++;
                    promote = true;
                }
                ch = sfen.charAt(i);
                if (!SFEN_PIECE.containsKey(ch)) {
                    throw new IllegalMoveException("Unknown=" + ch + x + y);
                }
                final IPiece piece = promote ? SFEN_PIECE.get(ch)
                        .promotion() : SFEN_PIECE.get(ch);
                shogiBan = shogiBan.set(x, y, piece);
                x--;
            }
        }

        return shogiBan;
    }
}
