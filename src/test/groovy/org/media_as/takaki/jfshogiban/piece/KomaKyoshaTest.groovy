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
import org.media_as.takaki.jfshogiban.IllegalMoveException
import org.media_as.takaki.jfshogiban.Koma
import org.media_as.takaki.jfshogiban.Player
import spock.lang.Specification

class KomaKyoshaTest extends Specification {
    def banmen = Banmen.initialize();

    def sente = new KomaKyosha(Player.SENTEBAN)
    def gote = new KomaKyosha(Player.GOTEBAN)

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "sente can't set illegal place"() {
        expect:
        sente.canSet(y) == result
        where:
        y | result
        1 | false
        2 | true
        8 | true
        9 | true
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "gote can't set illegal place"() {
        expect:
        gote.canSet(y) == result
        where:
        y | result
        1 | true
        2 | true
        8 | true
        9 | false
    }

    def "sente keep Kyosha move rule"() {
        def piece = Koma.SENTE_KYOSHA
        expect:
        piece.checkMove(fx, fy, tx, ty, banmen.set(1, 7, Koma.SENTE_FU))
        where:
        fx | fy | tx | ty
        1  | 9  | 1  | 8
    }

    def "sente Does not keep rule"() {
        def piece = Koma.SENTE_KYOSHA
        expect:
        !piece.checkMove(fx, fy, tx, ty, banmen.set(1, 7, Koma.SENTE_FU))
        where:
        fx | fy | tx | ty
        1  | 9  | 1  | 3
        0  | 8  | 5  | 5
    }

    def "gote keep Kyosha move rule"() {
        def piece = Koma.GOTE_KYOSHA
        expect:
        piece.checkMove(fx, fy, tx, ty, banmen.set(1, 7, Koma.SENTE_FU))
        where:
        fx | fy | tx | ty
        1  | 1  | 1  | 2
        1  | 1  | 1  | 5
        1  | 1  | 1  | 6
        1  | 1  | 1  | 7
    }

    def "gote Does not keep rule"() {
        expect:
        def piece = Koma.GOTE_KYOSHA
        !piece.checkMove(fx, fy, tx, ty, banmen.set(1, 7, Koma.SENTE_FU))
        where:
        fx | fy | tx | ty
        1  | 1  | 1  | 8
        1  | 1  | 5  | 5
    }

}
