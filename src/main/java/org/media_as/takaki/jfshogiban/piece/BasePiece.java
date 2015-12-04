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

import org.media_as.takaki.jfshogiban.Player;

import java.util.Objects;

public abstract class BasePiece implements IPiece {
    private final Player owner;

    protected BasePiece(final Player owner) {
        this.owner = owner;
    }

    @Override
    public final boolean isOwner(final Player player) {
        return owner == player;
    }

    protected final int sign() {
        return owner.sign();
    }

    protected final Player getOwner() { // XXX
        return owner;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public boolean canSet(final int y) {
        return true;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), owner);
    }

    @SuppressWarnings("MethodWithMultipleReturnPoints")
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        //noinspection InstanceofInterfaces
        if (!(obj instanceof BasePiece)) {
            return false;
        }
        //noinspection LocalVariableOfConcreteClass
        final BasePiece bp = (BasePiece) obj;
        //noinspection AccessingNonPublicFieldOfAnotherObject
        return bp.getClass() == getClass() && bp.owner == owner;

    }


    protected final String toCSA(final String str) {
        //noinspection StringConcatenationMissingWhitespace
        return owner + str;
    }
}