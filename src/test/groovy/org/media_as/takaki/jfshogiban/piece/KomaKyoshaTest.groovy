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

import org.media_as.takaki.jfshogiban.IllegalMoveException
import org.media_as.takaki.jfshogiban.Player
import spock.lang.Specification

class KomaKyoshaTest extends Specification {
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
}
