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
import java.util.List;
import java.util.stream.IntStream;

public class ShogiBan {
    // 3四 is 26.

    private static final int HEIGHT = 9;
    private static final int WIDTH = 9;

    private final List<Koma> board;

    public static ShogiBan initialize() {
        return new ShogiBan();
    }

    private ShogiBan() {
        board = new ArrayList<>(HEIGHT * WIDTH);
        IntStream.range(0, HEIGHT * WIDTH)
                .forEach(i -> board.add(Koma.EMPTY));
    }

    private ShogiBan(List<Koma> board) {
        this.board = board;
    }

    public Koma get(int x, int y) throws IllegalMoveException {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            throw new IllegalMoveException();
        }
        return board.get(x + HEIGHT * y);
    }

    public ShogiBan put(int x, int y,
                        Koma koma) throws IllegalMoveException {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            throw new IllegalMoveException();
        }
        final List<Koma> board = new ArrayList<>(this.board);
        board.set(x + HEIGHT * y, koma);
        return new ShogiBan(board);
    }

}
