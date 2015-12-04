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

package org.media_as.takaki.jfshogiban.piece;

import org.media_as.takaki.jfshogiban.IllegalMoveException;
import org.media_as.takaki.jfshogiban.Player;

import java.util.Objects;

public abstract class BasePiece {
    protected final Player owner;

    public BasePiece(final Player owner) {
        this.owner = owner;
    }

    public boolean isOwn(Player player) {
        return player == owner;
    }

    public boolean canDrop(final int y) {
        return true;
    }

    public abstract BasePiece captured(final Player owner);

    public abstract BasePiece promotion() throws IllegalMoveException;

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(), owner);
    }

    @SuppressWarnings("MethodWithMultipleReturnPoints")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        //noinspection InstanceofInterfaces
        if (!(obj instanceof BasePiece)) {
            return false;
        }
        final BasePiece kpp = (BasePiece) obj;
        //noinspection AccessingNonPublicFieldOfAnotherObject
        return kpp.getClass() == this.getClass() && kpp.owner == owner;

    }


    public String toCSA(final String fu) {
        return owner + fu;
    }
}