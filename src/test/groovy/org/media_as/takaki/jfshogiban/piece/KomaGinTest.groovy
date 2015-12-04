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

package org.media_as.takaki.jfshogiban.piece

import org.media_as.takaki.jfshogiban.Banmen
import org.media_as.takaki.jfshogiban.IllegalMoveException
import org.media_as.takaki.jfshogiban.Koma
import spock.lang.Specification

class KomaGinTest extends Specification {
    def banmen = Banmen.initialize();

    def "sente keep rule"() {
        def piece = Koma.SENTE_GIN
        expect:
        piece.checkMove(fx, fy, tx, ty, banmen)
        where:
        fx | fy | tx | ty
        5  | 5  | 6  | 4
        5  | 5  | 5  | 4
        5  | 5  | 4  | 4
        5  | 5  | 6  | 6
        5  | 5  | 4  | 4

    }

    def "sente does not keep rule"() {
        def piece = Koma.SENTE_GIN
        expect:
        !piece.checkMove(fx, fy, tx, ty, banmen)
        where:
        fx | fy | tx | ty
        5  | 5  | 4  | 5
        5  | 5  | 6  | 5
        5  | 5  | 5  | 3
    }

    def "gote keep rule"() {
        def piece = Koma.GOTE_GIN
        expect:
        piece.checkMove(fx, fy, tx, ty, banmen)
        where:
        fx | fy | tx | ty
        5  | 5  | 6  | 4
        5  | 5  | 4  | 4
        5  | 5  | 6  | 6
        5  | 5  | 5  | 6
        5  | 5  | 4  | 6

    }

}