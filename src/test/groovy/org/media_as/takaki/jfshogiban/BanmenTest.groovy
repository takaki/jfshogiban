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

import org.media_as.takaki.jfshogiban.core.Koma
import org.media_as.takaki.jfshogiban.core.Player
import org.media_as.takaki.jfshogiban.exception.IllegalMoveException
import org.media_as.takaki.jfshogiban.misc.Banmen
import spock.lang.Specification

class BanmenTest extends Specification {
    def board = Banmen.initialize()

    def "put on piece and remove piece"() {
        when:
        def kyokumen2 = board.set(1, 1, Koma.GOTE_KYOSHA)
        then:
        kyokumen2.pick(1, 1) == Koma.GOTE_KYOSHA

        when:
        def kyokumen3 = kyokumen2.remove(1, 1)
        then:
        kyokumen3.isEmpty(1, 1)
    }

    def "put on piece and move that piece"() {
        when:
        def kyokumen2 = board.set(1, 9, Koma.SENTE_FU).move(1, 9, 1, 8)
        then:
        kyokumen2.isEmpty(1, 9)
        kyokumen2.pick(1, 8) == Koma.SENTE_FU
    }

    def "can't put on piece on other piece"() {
        when:
        board.set(1, 9, Koma.SENTE_FU).set(1, 9, Koma.SENTE_FU)
        then:
        thrown(IllegalMoveException)
    }

    def "can't move piece on other piece"() {
        when:
        board.set(1, 9, Koma.SENTE_FU).set(1, 8, Koma.SENTE_KYOSHA).move(1, 9, 1, 8)
        then:
        thrown(IllegalMoveException)
    }

    def "captured piece is moved to KomaDai"() {
        when:
        def k2 = board.set(9, 9, Koma.SENTE_FU).capture(9, 9, Player.SENTEBAN)
        then:
        k2.isEmpty(9, 9)
        k2.countMochigoma(Koma.SENTE_FU) == 1
    }

    def "Can't capture empty"() {
        when:
        board.capture(1,1,Player.SENTEBAN)
        then:
        thrown(IllegalMoveException)
    }

    def "drop piece"() {
        when:
        def k2 = board.pushMochigoma(Koma.SENTE_FU).drop(9, 9, Koma.SENTE_FU)
        then:
        k2.pick(9, 9) == Koma.SENTE_FU
        k2.countMochigoma(Koma.SENTE_FU) == 0
    }

    def "Can't drop when empty mochigoma"() {
        when:
        board.drop(9, 9, Koma.SENTE_FU)
        then:
        thrown(IllegalMoveException)
    }

    def "Can't drop on other piece"() {
        when:
        board.pushMochigoma(Koma.SENTE_FU).set(9, 9, Koma.SENTE_FU).drop(9, 9, Koma.SENTE_FU)
        then:
        thrown(IllegalMoveException)
    }

    def "can't place some kinds piece on illegal locatioin"() {
        when:
        board.set(1, 1, Koma.SENTE_FU)
        then:
        thrown(IllegalMoveException)

        when:
        board.set(1, 9, Koma.GOTE_FU)
        then:
        thrown(IllegalMoveException)
    }

    def "can't remove empty location"() {
        when:
        board.remove(1, 1)
        then:
        thrown(IllegalMoveException)
    }

    def "can't move empty location"() {
        when:
        board.move(1, 1, 2, 2)
        then:
        thrown(IllegalMoveException)
    }

}
