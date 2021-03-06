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

package org.media_as.takaki.jfshogiban.core;

import org.media_as.takaki.jfshogiban.exception.IllegalMoveException;
import org.media_as.takaki.jfshogiban.piece.IPiece;
import org.media_as.takaki.jfshogiban.tostr.IStringConverter;

import java.util.Optional;

/* Kyokumen understands Shogi normalMove rule. */
public final class Kyokumen {
    private final ShogiBan shogiBan;
    private final Mochigoma mochigoma;
    private final Player turn;

    public static Kyokumen initialize() {
        return new Kyokumen(ShogiBan.initialize(), Mochigoma.initialize(),
                Player.SENTEBAN);
    }


    public static Kyokumen startPosition() {
        return new Kyokumen(ShogiBan.startPosition(), Mochigoma.initialize(),
                Player.SENTEBAN);
    }

    public Kyokumen(final ShogiBan shogiBan, final Mochigoma mochigoma,
                    final Player turn) {
        this.shogiBan = shogiBan;
        this.mochigoma = mochigoma;
        this.turn = turn;
    }

    public Player getTurn() {
        return turn;
    }

    public boolean isEmpty(final int x, final int y) {
        return shogiBan.isEmpty(x, y);
    }

    public Optional<IPiece> get(final int x, final int y) {
        return shogiBan.get(x, y);
    }

    public IPiece pick(final int x, final int y) {
        if (isEmpty(x, y)) {
            throw new IllegalMoveException("Can't pick empty.");
        }
        return shogiBan.get(x, y).get();
    }

    public Kyokumen normalMove(final int fx, final int fy, final int tx,
                               final int ty) {
        checkMove(fx, fy, tx, ty);
        return new Kyokumen(moveImpl(fx, fy, tx, ty, pick(fx, fy)),
                isEmpty(tx, ty) ? mochigoma : mochigoma
                        .push(pick(tx, ty).captured(turn)), turn.next());
    }

    public Kyokumen promotionMove(final int fx, final int fy, final int tx,
                                  final int ty) {
        checkMove(fx, fy, tx, ty);
        return new Kyokumen(moveImpl(fx, fy, tx, ty, pick(fx, fy).promotion()),
                isEmpty(tx, ty) ? mochigoma : mochigoma
                        .push(pick(tx, ty).captured(turn)), turn.next());
    }

    private ShogiBan moveImpl(final int fx, final int fy, final int tx,
                              final int ty, final IPiece koma) {
        return shogiBan.remove(fx, fy).set(tx, ty, koma);
    }

    // TODO: Sennichite
    // TODO: Mate
    // TODO: checkmate
    // TODO: stalemate
    private void checkMove(final int fx, final int fy, final int tx,
                           final int ty) {
        if (!isOwner(fx, fy)) {
            throw new IllegalMoveException("Don't move not own piece.");
        }
        if (!pick(fx, fy).checkMove(fx, fy, tx, ty, shogiBan)) {
            throw new IllegalMoveException("Move does not keep rule.");
        }
        if (!isEmpty(tx, ty) && isOwner(tx, ty)) {
            throw new IllegalMoveException("Don't capture own piece.");
        }
    }

    public Kyokumen drop(final int tx, final int ty,
                         final IPiece koma) {
        return new Kyokumen(shogiBan.set(tx, ty, koma), mochigoma.remove(koma),
                turn.next());
    }

    private boolean isOwner(final int x,
                            final int y) {
        return pick(x, y).isOwner(turn);
    }

    public int countMochigoma(final IPiece koma) {
        return mochigoma.count(koma);
    }

    public String convertString(final IStringConverter converter) {
        return converter.convertKyokumen(shogiBan, mochigoma, turn);
    }

}
