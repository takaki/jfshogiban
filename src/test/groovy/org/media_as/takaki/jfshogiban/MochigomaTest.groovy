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

class MochigomaTest extends Specification {
    def mochigoma = Mochigoma.initialize()

    def "put and get"() {
        when:
        def mochigoma2 = mochigoma.push(Koma.SENTE_FU)
        then:
        mochigoma2.remove(Koma.SENTE_FU)
    }

    def "get throw Exception"() {
        when:
        mochigoma.remove(Koma.SENTE_FU)

        then:
        thrown(IllegalMoveException)

        when:
        mochigoma.remove(Koma.SENTE_KYOSHA)

        then:
        thrown(IllegalMoveException)
    }

    def "count"() {
        when:
        def mochigoma2 = mochigoma.push(Koma.SENTE_FU)

        then:
        mochigoma2.count(Koma.SENTE_FU) == 1

        when:
        def mochigoma3 = mochigoma.push(Koma.SENTE_FU)
                .push(Koma.SENTE_FU)
                .push(Koma.SENTE_FU)
                .push(Koma.SENTE_KYOSHA)
                .push(Koma.SENTE_KEIMA)
                .remove(Koma.SENTE_KYOSHA)

        then:
        mochigoma3.count(Koma.SENTE_FU) == 3
        mochigoma3.count(Koma.SENTE_KYOSHA) == 0
        mochigoma3.count(Koma.SENTE_KEIMA) == 1
    }

}
