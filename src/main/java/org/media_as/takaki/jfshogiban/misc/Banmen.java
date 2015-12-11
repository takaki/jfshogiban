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

package org.media_as.takaki.jfshogiban.misc;

import org.media_as.takaki.jfshogiban.core.Mochigoma;
import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.core.ShogiBan;
import org.media_as.takaki.jfshogiban.exception.IllegalMoveException;
import org.media_as.takaki.jfshogiban.piece.IPiece;
import org.media_as.takaki.jfshogiban.tostr.CsaConverter;
import org.media_as.takaki.jfshogiban.tostr.IStringConverter;

import java.util.Optional;

public final class Banmen {
    private final ShogiBan shogiBan;
    private final Mochigoma mochigoma;

    public static Banmen initialize() {
        return new Banmen(ShogiBan.initialize(), Mochigoma.initialize());
    }

    public static Banmen startPosition() {
        return new Banmen(ShogiBan.startPosition(), Mochigoma.initialize());
    }

    private Banmen(final ShogiBan shogiBan, final Mochigoma mochigoma) {
        this.shogiBan = shogiBan;
        this.mochigoma = mochigoma;
    }

    public Optional<IPiece> get(final int x, final int y) {
        return shogiBan.get(x, y);
    }

    public boolean isEmpty(final int x, final int y) {
        return shogiBan.isEmpty(x, y);
    }

    public IPiece pick(final int x, final int y) {
        if (isEmpty(x, y)) {
            throw new IllegalMoveException("[Banmen] Can't pick empty.");
        }
        return shogiBan.get(x, y).get();
    }

    public Banmen set(final int x, final int y, final IPiece koma) {
        if (!isEmpty(x, y)) {
            throw new IllegalMoveException(
                    String.format("Try to put %s on other piece.", koma));
        }
        if (!koma.canSet(x, y, shogiBan)) {
            throw new IllegalMoveException(
                    String.format("%sCan't set %s [%d-%d] illegal location.",
                            convertString(new CsaConverter()), koma, x, y));
        }

        return new Banmen(shogiBan.set(x, y, koma), mochigoma);
    }

    public Banmen remove(final int x, final int y) {
        if (isEmpty(x, y)) {
            throw new IllegalMoveException("Try to remove piece from empty.");
        }
        return new Banmen(shogiBan.remove(x, y), mochigoma);
    }

    public Banmen move(final int fx, final int fy, final int tx, final int ty) {
        return remove(fx, fy).set(tx, ty, pick(fx, fy));
    }

    public Banmen promotion(final int fx, final int fy, final int tx,
                            final int ty) {
        return remove(fx, fy).set(tx, ty, pick(fx, fy).promotion());
    }

    public Banmen capture(final int x, final int y, final Player player) {
        return remove(x, y).pushMochigoma(pick(x, y).captured(player));
    }

    public Banmen pushMochigoma(final IPiece koma) {
        return new Banmen(shogiBan, mochigoma.push(koma));
    }

    public Banmen removeMochigoma(final IPiece koma) {
        return new Banmen(shogiBan, mochigoma.remove(koma));
    }

    public Banmen drop(final int x, final int y, final IPiece koma) {
        return removeMochigoma(koma).set(x, y, koma);
    }

    public int countMochigoma(final IPiece koma) {
        return mochigoma.count(koma);
    }

    public String convertString(final IStringConverter converter) {
        return converter.convertBanmen(shogiBan, mochigoma);
    }

}
