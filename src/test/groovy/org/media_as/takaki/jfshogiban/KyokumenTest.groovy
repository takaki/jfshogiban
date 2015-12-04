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

class KyokumenTest extends Specification {
    def start = Kyokumen.startPosition()

    def "can't move empty"() {
        when:
        start.move(4, 4, 5, 5)

        then:
        thrown(IllegalMoveException)
    }

    def "move own piece"() {
        when:
        def b2 = start.move(7, 7, 7, 6)

        then:
        b2.pick(7, 6) == Koma.SENTE_FU
    }

    def "can't move not own piece"() {
        when:
        start.move(1, 3, 1, 4)

        then:
        thrown(IllegalMoveException)
    }

    def "change turn"() {
        when:
        def b2 = start.move(7, 7, 7, 6)

        then:
        b2.getTurn() == Player.GOTEBAN;
    }

    def "capture piece"() {
        when:
        def b2 = start.move(1, 7, 1, 6).move(1, 3, 1, 4).move(1, 6, 1, 5).move(1, 4, 1, 5)
        then:
        b2.countMochigoma(Koma.GOTE_FU) == 1
    }

    def "can't capture own piece"() {
        when:
        start.move(1, 9, 1, 7)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Fu move rule"() {
        when:
        start.move(7, 7, 7, 5)
        then:
        thrown(IllegalMoveException)

        when:
        start.move(1, 7, 1, 6).move(1, 3, 1, 2)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Kyosha move rule"() {
        when:
        start.move(1, 9, 1, 8)
        start.move(1, 9, 1, 8).move(1, 1, 1, 2)
        then:
        notThrown(IllegalMoveException)

        when:
        start.move(1, 9, 1, 8).move(1, 1, 1, 6)
        then:
        thrown(IllegalMoveException)

        when:
        start.move(0, 8, 5, 5)
        then:
        thrown(IllegalMoveException)

        when:
        start.move(1, 9, 1, 6)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Keima move rule"() {
        when:
        start.move(7, 7, 7, 6).move(3, 3, 3, 4).move(8, 9, 7, 7)
        then:
        notThrown(IllegalMoveException)

        when:
        start.move(7, 7, 7, 8).move(3, 3, 3, 4).move(8, 9, 8, 8)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Gin move rule"() {
        when:
        start.move(3, 9, 5, 8)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Kin move rule"() {
        when:
        start.move(4, 9, 6, 8)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Kaku move rule"() {
        when:
        start.move(8, 8, 5, 5)
        then:
        thrown(IllegalMoveException)

        when:
        start.move(7, 7, 7, 6).move(3,3,3,4).move(8,8,5,5)
        then:
        notThrown(IllegalMoveException)

        when:
        start.move(8, 8, 7, 8)
        then:
        thrown(IllegalMoveException)

        when:
        start.move(8, 8, 6, 6)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Hisha move rule"() {
        when:
        start.move(2, 8, 2, 6)
        then:
        thrown(IllegalMoveException)
        when:
        start.move(2, 8, 9, 8)
        then:
        thrown(IllegalMoveException)
    }
}
