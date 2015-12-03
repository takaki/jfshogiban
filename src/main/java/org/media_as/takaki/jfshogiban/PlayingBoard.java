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

// PlayingBoard understands Shogi move rule.
public final class PlayingBoard {
    private final Kyokumen kyokumen;
    private final Player turn;

    public static PlayingBoard startPosition() throws IllegalMoveException {
        return new PlayingBoard(Kyokumen.startPosition(), Player.SENTEBAN);
    }

    private PlayingBoard(final Kyokumen kyokumen, final Player turn) {
        this.kyokumen = kyokumen;
        this.turn = turn;
    }

    public Player getTurn() {
        return turn;
    }

    public Koma get(final int x, final int y) throws IllegalMoveException {
        return kyokumen.get(x, y);
    }

    public PlayingBoard move(final int fx, final int fy, final int tx,
                             final int ty) throws IllegalMoveException {

        if (!get(fx, fy).isOwn(turn)) {
            throw new IllegalMoveException();
        }
        return get(tx, ty).isOwn(turn.next()) ? new PlayingBoard(
                kyokumen.capture(tx, ty, turn).move(fx, fy, tx, ty),
                turn.next()) : new PlayingBoard(kyokumen.move(fx, fy, tx, ty),
                turn.next());
    }

    public int countMochigoma(Koma koma) {
        return kyokumen.countMochigoma(koma);
    }
}
