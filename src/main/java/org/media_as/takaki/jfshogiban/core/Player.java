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

package org.media_as.takaki.jfshogiban.core;

import org.media_as.takaki.jfshogiban.tostr.IStringConverter;

public enum Player {
    SENTEBAN, GOTEBAN;

    public Player next() {
        return this == SENTEBAN ? GOTEBAN : SENTEBAN;
    }

    public int sign() {
        return this == SENTEBAN ? 1 : -1;
    }

    public String convert(final IStringConverter converter) {
        return converter.convertPlayer(this);
    }
}
