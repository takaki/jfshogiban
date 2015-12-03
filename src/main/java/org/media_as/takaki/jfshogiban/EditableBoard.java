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

public final class EditableBoard {
    private final ShogiBan shogiBan;
    private final Mochigoma mochigoma;

    public static EditableBoard initialize() {
        return new EditableBoard(ShogiBan.initialize(), Mochigoma.initialize());
    }

    public static EditableBoard startPosition() throws IllegalMoveException {
        return new EditableBoard(ShogiBan.startPosition(), Mochigoma.initialize());
    }

    private EditableBoard(final ShogiBan shogiBan, final Mochigoma mochigoma) {
        this.shogiBan = shogiBan;
        this.mochigoma = mochigoma;
    }

    public Koma get(final int x, final int y) throws IllegalMoveException {
        return shogiBan.get(x, y);
    }

    public EditableBoard set(final int x, final int y,
                             final Koma koma) throws IllegalMoveException {
        return new EditableBoard(shogiBan.set(x, y, koma), mochigoma);
    }

    public EditableBoard remove(final int x,
                                final int y) throws IllegalMoveException {
        return new EditableBoard(shogiBan.remove(x, y), mochigoma);
    }

    public EditableBoard move(final int fx, final int fy, final int tx,
                              final int ty) throws IllegalMoveException {
        return new EditableBoard(shogiBan.move(fx, fy, tx, ty), mochigoma);
    }

    public EditableBoard pushMochigoma(final Koma koma) throws IllegalMoveException {
        return new EditableBoard(shogiBan, mochigoma.push(koma));
    }

    public EditableBoard removeMochigoma(
            final Koma koma) throws IllegalMoveException {
        return new EditableBoard(shogiBan, mochigoma.remove(koma));
    }

    public EditableBoard capture(final int x, final int y,
                                 final Player player) throws IllegalMoveException {
        final Koma koma = shogiBan.get(x, y);
        return remove(x, y).pushMochigoma(koma.changeCaptured(player));
    }

    public EditableBoard drop(final int x, final int y,
                              final Koma koma) throws IllegalMoveException {
        return removeMochigoma(koma).set(x, y, koma);
    }

    public int countMochigoma(final Koma koma) {
        return mochigoma.count(koma);
    }
}
