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

package org.media_as.takaki.jfshogiban;

import org.media_as.takaki.jfshogiban.action.IMovement;
import org.media_as.takaki.jfshogiban.protocol.usi.Sfen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PlayMove {
    private final Kyokumen kyokumen;
    private final boolean finished;

    public PlayMove(final Kyokumen kyokumen, final boolean finished) {
        this.kyokumen = kyokumen;
        this.finished = finished;
    }

    public PlayMove getNextPlayMove(
            final IMovement movement) throws IllegalMoveException {
        return new PlayMove(movement.action(kyokumen), movement.isFinished());
    }

    public String toCSA() {
        return kyokumen.toCSA();
    }

    public String toSfen() {
        return String.join(" ", kyokumen.toSfen());
    }

    public boolean isFinished() {
        return finished;
    }

    public Player getTurn() {
        return kyokumen.getTurn();
    }
}
