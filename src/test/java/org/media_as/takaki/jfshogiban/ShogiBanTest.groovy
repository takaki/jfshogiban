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

package org.media_as.takaki.jfshogiban

import spock.lang.Specification

class ShogiBanTest extends Specification {
    def board = ShogiBan.initialize()

    def "get"() {
        expect:
        //noinspection GroovyAssignabilityCheck
        board.get(x, y) == Koma.EMPTY
        where:
        [x, y] << [0..8, 0..8].combinations()
    }

    def "get throw error"() {
        when:
        board.get(-1, 0)
        then:
        thrown(IllegalMoveException)
        when:
        board.get(2, 9)
        then:
        thrown(IllegalMoveException)
    }

    def "put and error"() {
        when:
        board.put(0, 9, Koma.GOTE_KAKU)
        then:
        thrown(IllegalMoveException)
    }
    def "put and check"() {
        when:
        def board2 = board.put(1, 1, Koma.GOTE_KAKU)
        then:
        board2.get(1, 1) == Koma.GOTE_KAKU
    }

}
