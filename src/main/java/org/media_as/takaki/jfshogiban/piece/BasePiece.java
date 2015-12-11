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

import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.core.ShogiBan;
import org.media_as.takaki.jfshogiban.tostr.IStringConverter;

import java.util.Objects;

public abstract class BasePiece implements IPiece {
    private final Player owner;

    @Override
    public final boolean isOwner(final Player player) {
        return owner == player;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public boolean canSet(final int x, final int y, final ShogiBan banmen) {
        return true;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), owner);
    }

    @SuppressWarnings({"MethodWithMultipleReturnPoints", "InstanceofInterfaces", "LocalVariableOfConcreteClass", "AccessingNonPublicFieldOfAnotherObject"})
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BasePiece)) {
            return false;
        }
        final BasePiece bp = (BasePiece) obj;
        return bp.getClass() == getClass() && bp.owner == owner;

    }

    protected BasePiece(final Player owner) {
        this.owner = owner;
    }

    protected final int sign() {
        return owner.sign();
    }

    protected final Player getOwner() { // XXX
        return owner;
    }

    @Override
    public final String convertString(final IStringConverter converter) {
        return converter.convertPiece(this);
    }

    @Override
    public final String toString() {
        return String.join(":", getClass().getSimpleName(), owner.toString());
    }
}