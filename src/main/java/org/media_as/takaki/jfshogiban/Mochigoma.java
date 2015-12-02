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

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public final class Mochigoma {
    private static final EnumSet<Koma> MOCHI_GOMA = EnumSet
            .of(Koma.SENTE_HISYA, Koma.SENTE_KAKU, Koma.SENTE_KIN,
                    Koma.SENTE_GIN, Koma.SENTE_KEIMA, Koma.SENTE_KYOSHA,
                    Koma.SENTE_FU, Koma.GOTE_HISYA, Koma.GOTE_KAKU,
                    Koma.GOTE_KIN, Koma.GOTE_GIN, Koma.GOTE_KEIMA,
                    Koma.GOTE_KYOSHA, Koma.GOTE_FU);

    private final Map<Koma, Integer> komaMap;

    public static Mochigoma initialize() {
        return new Mochigoma(
                MOCHI_GOMA.stream().collect(Collectors.toMap(koma -> koma, koma -> 0)));
    }

    private Mochigoma(final Map<Koma, Integer> komaMap) {
        this.komaMap = Collections.unmodifiableMap(komaMap);
    }

    public Mochigoma push(final Koma koma) throws IllegalMoveException {
        if (!MOCHI_GOMA.contains(koma)) {
            throw new IllegalMoveException();
        }
        final Map<Koma, Integer> komaMap = new EnumMap<>(this.komaMap);
        komaMap.computeIfPresent(koma, (p, n) -> n + 1);
        return new Mochigoma(komaMap);
    }

    public Mochigoma remove(final Koma koma) throws IllegalMoveException {
        if (!MOCHI_GOMA.contains(koma)) {
            throw new IllegalMoveException();
        }
        if (komaMap.get(koma) <= 0) {
            throw new IllegalMoveException();
        }
        final Map<Koma, Integer> komaMap = new EnumMap<>(this.komaMap);
        komaMap.computeIfPresent(koma, (p, n) -> n - 1);
        return new Mochigoma(komaMap);
    }

    public int count(final Koma koma) {
        return komaMap.getOrDefault(koma, 0);
    }

}
