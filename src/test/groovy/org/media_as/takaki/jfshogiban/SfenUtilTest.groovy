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

class SfenUtilTest extends Specification {
    def "mochigoma"() {
        expect:
        SfenUtil.mochigoma("B2Pp").count(Koma.SENTE_FU) == 2
        SfenUtil.mochigoma("B2Pp").count(Koma.GOTE_FU) == 1
        SfenUtil.mochigoma("B2Pp").count(Koma.SENTE_KAKU) == 1
    }

    def "shogiban"() {
        def sfen = "ln1gk1snl/1r1s2gb1/p2ppp1+p1/2p6/1p6P/2P2Pp2/PPBPPS3/2GS3R1/LN2KG1NL"
        expect:
        SfenUtil.shogiban("lnsgkgsnl/1r5b1/ppppppppp/9/9/9/PPPPPPPPP/1B5R1/LNSGKGSNL").get(2, 2).get() == Koma.GOTE_KAKU
        SfenUtil.shogiban(sfen).get(2, 2).get() == Koma.GOTE_KAKU
        SfenUtil.shogiban(sfen).get(2, 3).get() == Koma.GOTE_TOKIN
        SfenUtil.shogiban(sfen).convertString(new SfenConverter()) == sfen
    }


}
