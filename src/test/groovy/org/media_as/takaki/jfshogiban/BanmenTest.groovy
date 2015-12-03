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

class BanmenTest extends Specification {
    def board = Banmen.initialize()

    def "put on piece and remove piece"() {
        when:
        def kyokumen2 = board.set(0, 0, Koma.GOTE_KYOSHA)
        then:
        kyokumen2.get(0, 0) == Koma.GOTE_KYOSHA

        when:
        def kyokumen3 = kyokumen2.remove(0, 0)
        then:
        kyokumen3.get(0, 0) == Koma.EMPTY
    }

    def "put on piece and move that piece"() {
        when:
        def kyokumen2 = board.set(0, 8, Koma.SENTE_FU).move(0, 8, 0, 7)
        then:
        kyokumen2.get(0, 8) == Koma.EMPTY
        kyokumen2.get(0, 7) == Koma.SENTE_FU
    }

    def "can't put on piece on other piece"() {
        when:
        board.set(0, 8, Koma.SENTE_FU).set(0, 8, Koma.SENTE_FU)
        then:
        thrown(IllegalMoveException)
    }

    def "can't move piece on other piece"() {
        when:
        board.set(0, 8, Koma.SENTE_FU).set(0, 7, Koma.SENTE_KYOSHA).move(0, 8, 0, 7)
        then:
        thrown(IllegalMoveException)
    }

    def "captured piece is moved to KomaDai"() {
        when:
        def k2 = board.set(8, 8, Koma.SENTE_FU).capture(8, 8, Player.SENTEBAN)
        then:
        k2.get(8, 8) == Koma.EMPTY
        k2.countMochigoma(Koma.SENTE_FU) == 1
    }

    def "Can't capture empty"() {
        when:
        board.capture(0,0,Player.SENTEBAN)
        then:
        thrown(IllegalMoveException)
    }

    def "drop piece"() {
        when:
        def k2 = board.pushMochigoma(Koma.SENTE_FU).drop(8, 8, Koma.SENTE_FU)
        then:
        k2.get(8, 8) == Koma.SENTE_FU
        k2.countMochigoma(Koma.SENTE_FU) == 0
    }

    def "Can't drop when empty mochigoma"() {
        when:
        board.drop(8, 8, Koma.SENTE_FU)
        then:
        thrown(IllegalMoveException)
    }

    def "Can't drop on other piece"() {
        when:
        board.pushMochigoma(Koma.SENTE_FU).set(8, 8, Koma.SENTE_FU).drop(8, 8, Koma.SENTE_FU)
        then:
        thrown(IllegalMoveException)
    }

}
