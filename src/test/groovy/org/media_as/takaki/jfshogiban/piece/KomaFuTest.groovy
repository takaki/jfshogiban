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

package org.media_as.takaki.jfshogiban.piece

import org.media_as.takaki.jfshogiban.Banmen
import org.media_as.takaki.jfshogiban.Koma
import org.media_as.takaki.jfshogiban.Player
import org.media_as.takaki.jfshogiban.ShogiBan
import spock.lang.Specification

@SuppressWarnings("GroovyPointlessBoolean")
class KomaFuTest extends Specification {
    def banmen = ShogiBan.initialize();

    def sente = new KomaFu(Player.SENTEBAN)
    def gote = new KomaFu(Player.GOTEBAN)

    def "sente can't set illegal place"() {
        expect:
        sente.canSet(1, y, banmen) == result
        where:
        y || result
        1 || false
        2 || true
        8 || true
        9 || true
    }

    def "gote can't set illegal place"() {
        expect:
        gote.canSet(1, y, banmen) == result
        where:
        y || result
        1 || true
        2 || true
        8 || true
        9 || false
    }

    def "Check Sente Fu rule"() {
        def piece = Koma.SENTE_FU
        expect:
        piece.checkMove(fx, fy, tx, ty, banmen) == result
        where:
        fx | fy | tx | ty || result
        1  | 9  | 1  | 8  || true
        1  | 9  | 1  | 3  || false
        0  | 8  | 5  | 5  || false
    }

    def "Check Gote Fu move rule"() {
        def piece = Koma.GOTE_FU
        expect:
        piece.checkMove(fx, fy, tx, ty, banmen) == result
        where:
        fx | fy | tx | ty || result
        1  | 1  | 1  | 2  || true
        1  | 1  | 1  | 8  || false
        1  | 1  | 5  | 5  || false
    }

    def "Check Ni-Fu"() {
        expect:
        piece.canSet(x, y, banmen.set(1, 7, Koma.SENTE_FU).set(2, 7, Koma.GOTE_FU)) == result
        where:
        piece         | x | y || result
        Koma.SENTE_FU | 1 | 8 || false
        Koma.SENTE_FU | 2 | 7 || true
        Koma.GOTE_FU  | 1 | 8 || true
        Koma.GOTE_FU  | 2 | 8 || false
    }


}
