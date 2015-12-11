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

package org.media_as.takaki.jfshogiban.action;

import org.media_as.takaki.jfshogiban.Kyokumen;

public final class PromoteMove implements IMovement {
    private final int fx;
    private final int fy;
    private final int tx;
    private final int ty;

    public PromoteMove(final int fx, final int fy, final int tx, final int ty) {
        this.fx = fx;
        this.fy = fy;
        this.tx = tx;
        this.ty = ty;
    }

    @Override
    public Kyokumen action(final Kyokumen kyokumen) {
        return kyokumen.promotionMove(fx, fy, tx, ty);
    }

    @Override
    public String toString() {
        return String.format("PromoteMove: %d%d%d%d", fx, fy, tx, ty);
    }

}
