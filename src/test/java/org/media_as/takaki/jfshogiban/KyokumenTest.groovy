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
    def kyokumen = Kyokumen.initialize()

    def "put and check"() {
        when:
        def kyokumen2 = kyokumen.put(0, 0, Koma.GOTE_KYOSHA)
        then:
        kyokumen2.get(0, 0) == Koma.GOTE_KYOSHA
    }

    def "put and move"() {
        when:
        def kyokumen2 = kyokumen.put(0, 8, Koma.SENTE_FU).move(0, 8, 0, 7)
        then:
        kyokumen2.get(0, 8) == Koma.EMPTY
        kyokumen2.get(0, 7) == Koma.SENTE_FU
    }

    def "put and exception"() {
        when:
        kyokumen.put(0, 8, Koma.SENTE_FU).put(0, 8, Koma.SENTE_FU)
        then:
        thrown(IllegalMoveException)
    }

    def "move and exception"() {
        when:
        kyokumen.put(0, 8, Koma.SENTE_FU).put(0, 7, Koma.SENTE_KYOSHA).move(0, 8, 0, 7)
        then:
        thrown(IllegalMoveException)
    }
}
