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

package org.media_as.takaki.jfshogiban.main;

import org.media_as.takaki.jfshogiban.channel.IMoveChannel;
import org.media_as.takaki.jfshogiban.core.Kyokumen;
import org.media_as.takaki.jfshogiban.move.IMovement;
import org.media_as.takaki.jfshogiban.tostr.CsaConverter;
import org.media_as.takaki.jfshogiban.tostr.IStringConverter;
import org.media_as.takaki.jfshogiban.tostr.SfenConverter;

import java.io.PrintWriter;
import java.util.List;

public final class PlayEnd implements IMain {

    private final List<IMovement> movements;

    public PlayEnd(final Kyokumen startpos, final List<IMovement> movements,
                   final IMoveChannel channelSente,
                   final IMoveChannel channelGote) {
        this.movements = movements;
    }

    @Override
    public IMain next() {
        throw new RuntimeException("Don't call PlayEnd#next");
    }

    @Override
    public int getMoves() {
        return movements.size();
    }

}
