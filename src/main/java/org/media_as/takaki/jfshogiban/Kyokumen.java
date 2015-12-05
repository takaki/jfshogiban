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

import java.util.Optional;

/* Kyokumen understands Shogi move rule. */
public final class Kyokumen {
    private final Banmen banmen;
    private final Player turn;

    public static Kyokumen startPosition() throws IllegalMoveException {
        return new Kyokumen(Banmen.startPosition(), Player.SENTEBAN);
    }

    public Kyokumen(final Banmen banmen, final Player turn) {
        this.banmen = banmen;
        this.turn = turn;
    }

    public Player getTurn() {
        return turn;
    }

    public Optional<IPiece> get(final int x,
                                final int y) throws IllegalMoveException {
        return banmen.get(x, y);
    }

    public IPiece pick(final int x, final int y) throws IllegalMoveException {
        return banmen.pick(x, y);
    }

    public Kyokumen move(final int fx, final int fy, final int tx,
                         final int ty) throws IllegalMoveException {
        checkMove(fx, fy, tx, ty);
        return new Kyokumen((banmen.isEmpty(tx  , ty) ? banmen : capture(tx, ty))
                .move(fx, fy, tx, ty), turn.next());
    }

    public Kyokumen promotion(final int fx, final int fy, final int tx,
                              final int ty) throws IllegalMoveException {
        checkMove(fx, fy, tx, ty);
        return new Kyokumen((banmen.isEmpty(tx, ty) ? banmen : capture(tx, ty))
                .promotion(fx, fy, tx, ty), turn.next());
    }

    private void checkMove(final int fx, final int fy, final int tx,
                           final int ty) throws IllegalMoveException {
        if (!isOwner(fx, fy)) {
            throw new IllegalMoveException("Don't move not own piece.");
        }
        if (!pick(fx, fy).checkMove(fx, fy, tx, ty, banmen)) {
            throw new IllegalMoveException("Move does not keep rule.");
        }
    }

    private Banmen capture(final int tx,
                           final int ty) throws IllegalMoveException {
        if (isOwner(tx, ty)) {
            throw new IllegalMoveException("Don't capture own piece.");
        }
        return banmen.capture(tx, ty, turn);
    }

    public Kyokumen drop(final int tx, final int ty,
                         final IPiece koma) throws IllegalMoveException {
        return new Kyokumen(banmen.set(tx, ty, koma), turn.next());
    }

    private boolean isOwner(final int x,
                            final int y) throws IllegalMoveException {
        return pick(x, y).isOwner(turn);
    }

    private boolean isEmpty(final int x,
                            final int y) throws IllegalMoveException {
        return banmen.isEmpty(x, y);
    }

    public int countMochigoma(final IPiece koma) {
        return banmen.countMochigoma(koma);
    }
}
