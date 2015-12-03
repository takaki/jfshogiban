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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("HardCodedStringLiteral")
public final class ShogiBan {
    private static final int HEIGHT = 9;
    private static final int WIDTH = 9;
    private static final EnumSet<Koma> ILLEGAL_SENTE = EnumSet
            .of(Koma.SENTE_FU, Koma.SENTE_KYOSHA, Koma.SENTE_KEIMA);
    private static final EnumSet<Koma> ILLEGAL_GOTE = EnumSet
            .of(Koma.GOTE_FU, Koma.GOTE_KYOSHA, Koma.GOTE_KEIMA);

    private static final Map<Koma, String> STRING_MAP = new EnumMap<>(
            Koma.class);

    static {
        STRING_MAP.put(Koma.SENTE_HISYA, "+HI");
        STRING_MAP.put(Koma.SENTE_RYU, "+RY");
        STRING_MAP.put(Koma.GOTE_HISYA, "-HI");
        STRING_MAP.put(Koma.GOTE_RYU, "-RY龍");
        STRING_MAP.put(Koma.SENTE_KAKU, "+KA");
        STRING_MAP.put(Koma.SENTE_UMA, "+UM");
        STRING_MAP.put(Koma.GOTE_KAKU, "-KA");
        STRING_MAP.put(Koma.GOTE_UMA, "-UM");
        STRING_MAP.put(Koma.SENTE_KIN, "+KI");
        STRING_MAP.put(Koma.GOTE_KIN, "-KI");
        STRING_MAP.put(Koma.SENTE_GIN, "+GI");
        STRING_MAP.put(Koma.SENTE_NARIGIN, "+NG");
        STRING_MAP.put(Koma.GOTE_GIN, "-GI");
        STRING_MAP.put(Koma.GOTE_NARIGIN, "-NG");
        STRING_MAP.put(Koma.SENTE_KEIMA, "+KE");
        STRING_MAP.put(Koma.SENTE_NARIKEI, "+NK");
        STRING_MAP.put(Koma.GOTE_KEIMA, "-KE");
        STRING_MAP.put(Koma.GOTE_NARIKEI, "-NK");
        STRING_MAP.put(Koma.SENTE_KYOSHA, "+KY");
        STRING_MAP.put(Koma.SENTE_NARIKYO, "+NK");
        STRING_MAP.put(Koma.GOTE_KYOSHA, "-KY");
        STRING_MAP.put(Koma.GOTE_NARIKYO, "-NK");
        STRING_MAP.put(Koma.SENTE_FU, "+FU");
        STRING_MAP.put(Koma.SENTE_TOKIN, "+TO");
        STRING_MAP.put(Koma.GOTE_FU, "-FU");
        STRING_MAP.put(Koma.GOTE_TOKIN, "-TO");
        STRING_MAP.put(Koma.SENTE_GYOKU, "+OU");
        STRING_MAP.put(Koma.GOTE_GYOKU, "-OU");
        STRING_MAP.put(Koma.EMPTY, " * ");
    }

    @SuppressWarnings("FieldNotUsedInToString")
    private final List<Koma> board;

    public static ShogiBan startPosition() throws IllegalMoveException {
        return initialize().set(0, 0, Koma.GOTE_KYOSHA)
                .set(1, 0, Koma.GOTE_KEIMA).set(2, 0, Koma.GOTE_GIN)
                .set(3, 0, Koma.GOTE_KIN).set(4, 0, Koma.GOTE_GYOKU)
                .set(5, 0, Koma.GOTE_KIN).set(6, 0, Koma.GOTE_GIN)
                .set(7, 0, Koma.GOTE_KEIMA).set(8, 0, Koma.GOTE_KYOSHA)
                .set(1, 1, Koma.GOTE_KAKU).set(7, 1, Koma.GOTE_HISYA)
                .set(0, 2, Koma.GOTE_FU).set(1, 2, Koma.GOTE_FU)
                .set(2, 2, Koma.GOTE_FU).set(3, 2, Koma.GOTE_FU)
                .set(4, 2, Koma.GOTE_FU).set(5, 2, Koma.GOTE_FU)
                .set(6, 2, Koma.GOTE_FU).set(7, 2, Koma.GOTE_FU)
                .set(8, 2, Koma.GOTE_FU).set(0, 6, Koma.SENTE_FU)
                .set(1, 6, Koma.SENTE_FU).set(2, 6, Koma.SENTE_FU)
                .set(3, 6, Koma.SENTE_FU).set(4, 6, Koma.SENTE_FU)
                .set(5, 6, Koma.SENTE_FU).set(6, 6, Koma.SENTE_FU)
                .set(7, 6, Koma.SENTE_FU).set(8, 6, Koma.SENTE_FU)
                .set(0, 8, Koma.SENTE_KYOSHA).set(1, 8, Koma.SENTE_KEIMA)
                .set(2, 8, Koma.SENTE_GIN).set(3, 8, Koma.SENTE_KIN)
                .set(4, 8, Koma.SENTE_GYOKU).set(5, 8, Koma.SENTE_KIN)
                .set(6, 8, Koma.SENTE_GIN).set(7, 8, Koma.SENTE_KEIMA)
                .set(8, 8, Koma.SENTE_KYOSHA).set(7, 7, Koma.SENTE_KAKU)
                .set(1, 7, Koma.SENTE_HISYA);
    }

    public static ShogiBan initialize() {
        return new ShogiBan(
                IntStream.range(0, HEIGHT * WIDTH).mapToObj(i -> Koma.EMPTY)
                        .collect(Collectors.toList()));
    }

    private ShogiBan(final List<Koma> board) {
        this.board = Collections.unmodifiableList(board);
    }

    public Koma get(final int x, final int y) throws IllegalMoveException {
        return board.get(calcIndex(x, y));
    }

    public ShogiBan set(final int x, final int y,
                        final Koma koma) throws IllegalMoveException {
        if (!isEmpty(x, y)) {
            throw new IllegalMoveException("Try to put on other piece.");
        }
        if (ILLEGAL_SENTE.contains(koma) && y == 0 ||
                koma == Koma.SENTE_KEIMA && y <= 1 ||
                ILLEGAL_GOTE.contains(koma) && y == 8 ||
                koma == Koma.GOTE_KEIMA && y >= 7) {
            throw new IllegalMoveException("Out of bound.");
        }
        final List<Koma> board = new ArrayList<>(this.board);
        board.set(calcIndex(x, y), koma);
        return new ShogiBan(board);
    }

    public ShogiBan remove(final int x,
                           final int y) throws IllegalMoveException {
        if (isEmpty(x, y)) {
            throw new IllegalMoveException("Try to remove piece from empty.");
        }
        final List<Koma> board = new ArrayList<>(this.board);
        board.set(calcIndex(x, y), Koma.EMPTY);
        return new ShogiBan(board);
    }

    public boolean isEmpty(final int x,
                           final int y) throws IllegalMoveException {
        return get(x, y) == Koma.EMPTY;
    }

    public ShogiBan move(final int fx, final int fy, final int tx,
                         final int ty) throws IllegalMoveException {
        return remove(fx, fy).set(tx, ty, get(fx, fy));
    }

    // 3四 is 19.
    private static int calcIndex(final int x,
                                 final int y) throws IllegalMoveException {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            throw new IllegalMoveException(
                    String.format("Out of bound x = %d, y = %d", x, y));
        }
        return x + y * HEIGHT;
    }

    @SuppressWarnings({"FieldRepeatedlyAccessedInMethod", "MethodWithMultipleLoops"})
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(120);
        for (int y = 0; y < HEIGHT; y++) {
            //noinspection MagicCharacter
            builder.append('P').append(y + 1);
            for (int x = WIDTH - 1; x >= 0; x--) {
                try {
                    builder.append(STRING_MAP.get(get(x, y)));
                } catch (final IllegalMoveException ignored) {
                }
            }
            //noinspection MagicCharacter,HardcodedLineSeparator
            builder.append('\n');
        }
        return builder.toString();
    }
}
