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

import org.media_as.takaki.jfshogiban.piece.BasePiece;

import java.util.Optional;

public final class Banmen {
    private final ShogiBan shogiBan;
    private final Mochigoma mochigoma;

    public static Banmen initialize() {
        return new Banmen(ShogiBan.initialize(), Mochigoma.initialize());
    }

    public static Banmen startPosition() throws IllegalMoveException {
        return new Banmen(ShogiBan.startPosition(), Mochigoma.initialize());
    }

    private Banmen(final ShogiBan shogiBan, final Mochigoma mochigoma) {
        this.shogiBan = shogiBan;
        this.mochigoma = mochigoma;
    }

    public Optional<BasePiece> get(final int x,
                                   final int y) throws IllegalMoveException {
        return shogiBan.get(x, y);
    }

    public boolean isEmpty(final int x,
                           final int y) throws IllegalMoveException {
        return shogiBan.isEmpty(x, y);
    }

    public BasePiece pick(final int x,
                          final int y) throws IllegalMoveException {
        if (isEmpty(x, y)) {
            throw new IllegalMoveException("Can't pick empty.");
        }
        return shogiBan.get(x, y).get();
    }

    public Banmen set(final int x, final int y,
                      final BasePiece koma) throws IllegalMoveException {
        if (!isEmpty(x, y)) {
            throw new IllegalMoveException("Try to put on other piece.");
        }
        return new Banmen(shogiBan.set(x, y, koma), mochigoma);
    }

    public Banmen remove(final int x, final int y) throws IllegalMoveException {
        if (isEmpty(x, y)) {
            throw new IllegalMoveException("Try to remove piece from empty.");
        }
        return new Banmen(shogiBan.remove(x, y), mochigoma);
    }

    public Banmen move(final int fx, final int fy, final int tx,
                       final int ty) throws IllegalMoveException {
        return remove(fx, fy).set(tx, ty, pick(fx, fy));
    }

    public Banmen capture(final int x, final int y,
                          final Player player) throws IllegalMoveException {
        return remove(x, y).pushMochigoma(pick(x, y).captured(player), player);
    }

    public Banmen pushMochigoma(final BasePiece koma,
                                Player player) throws IllegalMoveException {
        return new Banmen(shogiBan, mochigoma.push(koma, player));
    }

    public Banmen removeMochigoma(final BasePiece koma,
                                  Player player) throws IllegalMoveException {
        return new Banmen(shogiBan, mochigoma.remove(koma, player));
    }

    public Banmen drop(final int x, final int y, final BasePiece koma,
                       Player player) throws IllegalMoveException {
        if (!koma.canDrop(y)) {
            throw new IllegalMoveException("Can't drop " + koma);
        }
        return removeMochigoma(koma, player).set(x, y, koma);
    }

    public int countMochigoma(final BasePiece koma, Player player) {
        return mochigoma.count(koma, player);
    }
}
