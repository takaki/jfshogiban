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

package org.media_as.takaki.jfshogiban.main

import org.media_as.takaki.jfshogiban.channel.IMoveChannel
import org.media_as.takaki.jfshogiban.channel.Terminal
import org.media_as.takaki.jfshogiban.core.Kyokumen
import org.media_as.takaki.jfshogiban.move.IMovement
import org.media_as.takaki.jfshogiban.move.NormalMove
import spock.lang.Specification

class PlayMainTest extends Specification {
    def playmain = new PlayMain(Kyokumen.startPosition(), new ArrayList<IMovement>(), new Terminal(), new Terminal())

    def "playmain moves"() {
        expect:
        playmain.getMoves() == 0
    }

    def "moves"() {
        def moves = Arrays.asList(new NormalMove(7, 7, 7, 6), new NormalMove(8, 3, 8, 4))
        when:
        new PlayMain(Kyokumen.startPosition(), moves, new Terminal(), new Terminal())
        then:
        notThrown(RuntimeException)
    }
}
