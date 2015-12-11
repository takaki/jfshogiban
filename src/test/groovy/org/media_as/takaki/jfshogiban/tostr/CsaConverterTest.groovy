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

package org.media_as.takaki.jfshogiban.tostr

import org.media_as.takaki.jfshogiban.core.Koma
import org.media_as.takaki.jfshogiban.core.Mochigoma
import org.media_as.takaki.jfshogiban.core.ShogiBan
import spock.lang.Specification

class CsaConverterTest extends Specification {
    def converter = new CsaConverter()

    def "ShogiBan test"() {
        expect:
        ShogiBan.startPosition().convertString(converter) ==
                "P1-KY-KE-GI-KI-OU-KI-GI-KE-KY\n" +
                "P2 * -HI *  *  *  *  * -KA * \n" +
                "P3-FU-FU-FU-FU-FU-FU-FU-FU-FU\n" +
                "P4 *  *  *  *  *  *  *  *  * \n" +
                "P5 *  *  *  *  *  *  *  *  * \n" +
                "P6 *  *  *  *  *  *  *  *  * \n" +
                "P7+FU+FU+FU+FU+FU+FU+FU+FU+FU\n" +
                "P8 * +KA *  *  *  *  * +HI * \n" +
                "P9+KY+KE+GI+KI+OU+KI+GI+KE+KY\n"
    }

    def "Mochigoma "() {
        expect:
        Mochigoma.initialize().push(Koma.SENTE_FU).push(Koma.SENTE_FU).push(Koma.GOTE_KEIMA).
                convertString(converter) ==
                "P+00FU00FU\nP-00KE\n"
    }


}
