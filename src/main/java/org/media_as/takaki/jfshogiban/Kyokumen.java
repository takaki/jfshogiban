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

public final class Kyokumen {
    private final ShogiBan shogiBan;
    private final KomaDai komaDai;

    public static Kyokumen initialize() {
        return new Kyokumen(ShogiBan.initialize(), KomaDai.initialize());
    }


    private Kyokumen(final ShogiBan shogiBan, final KomaDai komaDai) {
        this.shogiBan = shogiBan;
        this.komaDai = komaDai;
    }

    public Kyokumen move(final int fx, final int fy, final int tx,
                         final int ty) throws IllegalMoveException {
        return new Kyokumen(shogiBan.move(fx, fy, tx, ty), komaDai);
    }

    public Kyokumen set(final int x, final int y,
                        final Koma koma) throws IllegalMoveException {
        if (shogiBan.get(x, y) != Koma.EMPTY) {
            throw new IllegalMoveException();
        }
        return new Kyokumen(shogiBan.set(x, y, koma), komaDai);
    }

    public Koma get(final int x, final int y) throws IllegalMoveException {
        return shogiBan.get(x, y);
    }

    public Kyokumen remove(final int x,
                           final int y) throws IllegalMoveException {
        return new Kyokumen(shogiBan.remove(x, y), komaDai);
    }

    public Kyokumen putKomaDai(final Koma koma) throws IllegalMoveException {
        return new Kyokumen(shogiBan, komaDai.push(koma));
    }

    public Kyokumen removeKomaDai(final Koma koma) throws IllegalMoveException {
        return new Kyokumen(shogiBan, komaDai.remove(koma));
    }

    public int countDai(final Koma koma) {
        return komaDai.count(koma);
    }
}
