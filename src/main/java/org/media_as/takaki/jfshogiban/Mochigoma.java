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

import org.media_as.takaki.jfshogiban.piece.*;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Mochigoma {
    private final Map<BasePiece, Integer> mochigoma;
    private static final Collection<BasePiece> MOCHIGOMA = new HashSet<>(
            Arrays.asList(Koma.SENTE_FU, Koma.SENTE_KYOSHA, Koma.SENTE_KEIMA,
                    Koma.SENTE_GIN, Koma.SENTE_KIN, Koma.SENTE_KAKU,
                    Koma.SENTE_HISYA, Koma.GOTE_FU, Koma.GOTE_KYOSHA,
                    Koma.GOTE_KEIMA, Koma.GOTE_GIN, Koma.GOTE_KIN,
                    Koma.GOTE_KAKU, Koma.GOTE_HISYA));

    public static Mochigoma initialize() {
        return new Mochigoma(MOCHIGOMA.stream()
                .collect(Collectors.toMap(Function.identity(), koma -> 0)));
    }

    public Mochigoma(final Map<BasePiece, Integer> mochigoma) {
        this.mochigoma = Collections.unmodifiableMap(mochigoma);
    }

    public Mochigoma push(final BasePiece koma) throws IllegalMoveException {
        if (!MOCHIGOMA.contains(koma)) {
            //noinspection HardCodedStringLiteral
            throw new IllegalMoveException(
                    String.format("Can't push %s to mochigoma.", koma));
        }
        final Map<BasePiece, Integer> komaMap = new HashMap<>(mochigoma);
        komaMap.computeIfPresent(koma, (p, n) -> n + 1);
        return new Mochigoma(komaMap);
    }

    public Mochigoma remove(final BasePiece koma) throws IllegalMoveException {
        if (count(koma) <= 0) {
            //noinspection HardCodedStringLiteral
            throw new IllegalMoveException(String.format("%s is empty.", koma));
        }
        final Map<BasePiece, Integer> mochigoma = new HashMap<>(this.mochigoma);
        mochigoma.computeIfPresent(koma, (p, n) -> n - 1);
        return new Mochigoma(mochigoma);
    }

    public int count(final BasePiece koma) {
        return mochigoma.getOrDefault(koma, 0);
    }

}
