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

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ShogiBan {
    // 3四 is 19.

    private static final int HEIGHT = 9;
    private static final int WIDTH = 9;
    private static final EnumSet<Koma> ILLEGAL_SENATE = EnumSet
            .of(Koma.SENTE_FU, Koma.SENTE_KYOSHA, Koma.SENTE_KEIMA);
    private static final EnumSet<Koma> ILLEGAL_GOTE = EnumSet
            .of(Koma.GOTE_FU, Koma.GOTE_KYOSHA, Koma.GOTE_KEIMA);

    private final List<Koma> board;

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
        if (ILLEGAL_SENATE.contains(koma) && y == 0 ||
                koma == Koma.SENTE_KEIMA && y <= 1 ||
                ILLEGAL_GOTE.contains(koma) && y == 8 ||
                koma == Koma.GOTE_KEIMA && y >= 7) {
            throw new IllegalMoveException();
        }
        final List<Koma> board = new ArrayList<>(this.board);
        board.set(calcIndex(x, y), koma);
        return new ShogiBan(board);
    }

    public ShogiBan remove(final int x,
                           final int y) throws IllegalMoveException {
        final List<Koma> board = new ArrayList<>(this.board);
        board.set(calcIndex(x, y), Koma.EMPTY);
        return new ShogiBan(board);
    }

    public boolean isEmpty(final int x,
                           final int y) throws IllegalMoveException {
        return board.get(calcIndex(x, y)) == Koma.EMPTY;
    }

    public ShogiBan move(final int fx, final int fy, final int tx,
                         final int ty) throws IllegalMoveException {
        if (isEmpty(fx, fy) || !isEmpty(tx, ty)) {
            throw new IllegalMoveException();
        }
        final Koma from = get(fx, fy);
        return remove(fx, fy).set(tx, ty, from);
    }

    private static int calcIndex(final int x,
                                 final int y) throws IllegalMoveException {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            throw new IllegalMoveException();
        }
        return x + y * HEIGHT;
    }

}
