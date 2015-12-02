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

public class Kyokumen {
    private final ShogiBan shogiBan;
    private final Mochigoma mochigoma;

    public static Kyokumen initialize() {
        return new Kyokumen(ShogiBan.initialize(), Mochigoma.initialize());
    }

    private Kyokumen(ShogiBan shogiBan, Mochigoma mochigoma) {
        this.shogiBan = shogiBan;
        this.mochigoma = mochigoma;
    }

    public Kyokumen move(int fx, int fy, int tx,
                         int ty) throws IllegalMoveException {
        Koma from = shogiBan.get(fx, fy);
        if (from == Koma.EMPTY || shogiBan.get(tx, ty) != Koma.EMPTY) {
            throw new IllegalMoveException();
        }
        return new Kyokumen(shogiBan.put(fx, fy, Koma.EMPTY).put(tx, ty, from),
                mochigoma);
    }

    public Kyokumen put(int x, int y, Koma koma) throws IllegalMoveException {
        if (shogiBan.get(x, y) != Koma.EMPTY) {
            throw new IllegalMoveException();
        }
        return new Kyokumen(shogiBan.put(x, y, koma), mochigoma);
    }

    public Koma get(int x, int y) throws IllegalMoveException {
        return shogiBan.get(x, y);
    }
}
