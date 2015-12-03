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

class PlayingBoardTest extends Specification {
    def board = PlayingBoard.startPosition()

    def "can't move empty"() {
        when:
        board.move(4, 4, 5, 5)

        then:
        thrown(IllegalMoveException)
    }

    def "move own piece"() {
        when:
        def b2 = board.move(6, 6, 6, 5)

        then:
        b2.get(6, 5) == Koma.SENTE_FU
    }

    def "can't move not own piece"() {
        when:
        board.move(0, 2, 0, 3)

        then:
        thrown(IllegalMoveException)
    }

    def "change turn"() {
        when:
        def b2 = board.move(6, 6, 6, 5)

        then:
        b2.getTurn() == Player.GOTEBAN;
    }

    def "capture piece"() {
        when:
        def b2 = board.move(0,6,0,5).move(0,2,0,3).move(0,5,0,4).move(0,3,0,4)
        then:
        b2.countMochigoma(Koma.GOTE_FU) == 1
    }

    def "can't capture own piece"() {
        when:
        board.move(0,8,0,6)
        then:
        thrown(IllegalMoveException)
    }

}
