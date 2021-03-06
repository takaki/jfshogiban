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

package org.media_as.takaki.jfshogiban.core

import org.media_as.takaki.jfshogiban.exception.IllegalMoveException
import spock.lang.Specification

class KyokumenTest extends Specification {
    def start = Kyokumen.startPosition()

    def "can't move empty"() {
        when:
        start.normalMove(4, 4, 5, 5)

        then:
        thrown(IllegalMoveException)
    }

    def "move own piece"() {
        when:
        def b2 = start.normalMove(7, 7, 7, 6)

        then:
        b2.pick(7, 6) == Koma.SENTE_FU
        b2.get(7, 7) == Optional.empty()
    }

    def "can't move not own piece"() {
        when:
        start.normalMove(1, 3, 1, 4)

        then:
        thrown(IllegalMoveException)
    }

    def "change turn"() {
        when:
        def b2 = start.normalMove(7, 7, 7, 6)

        then:
        b2.getTurn() == Player.GOTEBAN;
    }

    def "capture piece"() {
        when:
        def b2 = start.normalMove(1, 7, 1, 6).normalMove(1, 3, 1, 4).normalMove(1, 6, 1, 5).normalMove(1, 4, 1, 5)
        then:
        b2.countMochigoma(Koma.GOTE_FU) == 1
    }

    def "can't capture own piece"() {
        when:
        start.normalMove(1, 9, 1, 7)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Fu move rule"() {
        when:
        start.normalMove(7, 7, 7, 5)
        then:
        thrown(IllegalMoveException)

        when:
        start.normalMove(1, 7, 1, 6).normalMove(1, 3, 1, 2)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Keima move rule"() {
        when:
        start.normalMove(7, 7, 7, 6).normalMove(3, 3, 3, 4).normalMove(8, 9, 7, 7)
        then:
        notThrown(IllegalMoveException)

        when:
        start.normalMove(7, 7, 7, 8).normalMove(3, 3, 3, 4).normalMove(8, 9, 8, 8)
        then:
        thrown(IllegalMoveException)
    }


    def "Keep Kin move rule"() {
        when:
        start.normalMove(4, 9, 6, 8)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Kaku move rule"() {
        when:
        start.normalMove(8, 8, 5, 5)
        then:
        thrown(IllegalMoveException)

        when:
        start.normalMove(7, 7, 7, 6).normalMove(3, 3, 3, 4).normalMove(8, 8, 5, 5)
        then:
        notThrown(IllegalMoveException)

        when:
        start.normalMove(8, 8, 7, 8)
        then:
        thrown(IllegalMoveException)

        when:
        start.normalMove(8, 8, 6, 6)
        then:
        thrown(IllegalMoveException)
    }

    def "Keep Hisha move rule"() {
        when:
        start.normalMove(2, 8, 2, 6)
        then:
        thrown(IllegalMoveException)
        when:
        start.normalMove(2, 8, 9, 8)
        then:
        thrown(IllegalMoveException)
    }

    def "Fu promotion"() {
        def test = new Kyokumen(ShogiBan.initialize().set(2, 4, Koma.SENTE_FU), Mochigoma.initialize(), Player.SENTEBAN)
        expect:
        test.promotionMove(2, 4, 2, 3).pick(2, 3) == Koma.SENTE_TOKIN
        test.promotionMove(2, 4, 2, 3).isEmpty(2, 4)
    }


}
