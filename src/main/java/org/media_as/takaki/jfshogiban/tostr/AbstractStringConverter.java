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

package org.media_as.takaki.jfshogiban.tostr;

import org.media_as.takaki.jfshogiban.core.Koma;
import org.media_as.takaki.jfshogiban.piece.IPiece;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public abstract class AbstractStringConverter implements IStringConverter {
    protected static final List<IPiece> SENTE_LIST = Collections
            .unmodifiableList(Arrays.asList(Koma.SENTE_FU, Koma.SENTE_KYOSHA,
                    Koma.SENTE_KEIMA, Koma.SENTE_GIN, Koma.SENTE_KIN,
                    Koma.SENTE_KAKU, Koma.SENTE_HISYA));
    protected static final List<IPiece> GOTE_LIST = Collections
            .unmodifiableList(Arrays.asList(Koma.GOTE_FU, Koma.GOTE_KYOSHA,
                    Koma.GOTE_KEIMA, Koma.GOTE_GIN, Koma.GOTE_KIN,
                    Koma.GOTE_KAKU, Koma.GOTE_HISYA));

}
