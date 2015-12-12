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

package org.media_as.takaki.jfshogiban.channel.usi

import org.media_as.takaki.jfshogiban.core.Kyokumen
import spock.lang.Ignore
import spock.lang.Specification

import java.nio.file.Paths

class UsiChannelTest extends Specification {

    @Ignore
    def "run channel"() {
        def channel = new UsiChannel(Paths.get("/home/takaki/tmp/gpsfish/src"), "gpsfish")
        expect:
        channel.getMovement(Kyokumen.startPosition()) != null
    }
}
