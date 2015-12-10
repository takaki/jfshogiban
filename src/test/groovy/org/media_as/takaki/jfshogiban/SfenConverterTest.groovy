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

import org.media_as.takaki.jfshogiban.tostr.SfenConverter
import spock.lang.Specification

class SfenConverterTest extends Specification {
    def converter = new SfenConverter()

    def "ShogiBan test"() {
        expect:
        ShogiBan.startPosition().convertString(converter) == "lnsgkgsnl/1r5b1/ppppppppp/9/9/9/PPPPPPPPP/1B5R1/LNSGKGSNL"
    }

    def "Mochigoma test"() {
        expect:
        Mochigoma.initialize().push(Koma.SENTE_FU).push(Koma.SENTE_FU).push(Koma.GOTE_KEIMA).
                convertString(converter) == "2Pn"
    }

    def "Kyokumen test"() {
        expect:
        Kyokumen.startPosition().convertString(converter) == "lnsgkgsnl/1r5b1/ppppppppp/9/9/9/PPPPPPPPP/1B5R1/LNSGKGSNL b - 1"
    }
}
