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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.media_as.takaki.jfshogiban.Koma.*;

public class KomaDai {
    private static final EnumSet<Koma> MOCHI_GOMA = EnumSet
            .of(SENTE_HISYA, SENTE_KAKU, SENTE_KIN, SENTE_GIN, SENTE_KEIMA,
                    SENTE_KYOSHA, SENTE_FU, GOTE_HISYA, GOTE_KAKU, GOTE_KIN,
                    GOTE_GIN, GOTE_KEIMA, GOTE_KYOSHA, GOTE_FU);

    private final Map<Koma, Integer> komaMap;

    public static KomaDai initialize() {
        return new KomaDai(
                MOCHI_GOMA.stream().collect(Collectors.toMap(s -> s, s -> 0)));
    }

    private KomaDai(Map<Koma, Integer> komaMap) {
        this.komaMap = Collections.unmodifiableMap(komaMap);
    }

    public KomaDai put(Koma koma) throws IllegalMoveException {
        if (!MOCHI_GOMA.contains(koma)) {
            throw new IllegalMoveException();
        }
        final Map<Koma, Integer> komaMap = new HashMap<>(this.komaMap);
        komaMap.computeIfPresent(koma, (p, n) -> n + 1);
        return new KomaDai(komaMap);
    }

    public KomaDai remove(Koma koma) throws IllegalMoveException {
        if (!MOCHI_GOMA.contains(koma)) {
            throw new IllegalMoveException();
        }
        if (komaMap.get(koma) <= 0) {
            throw new IllegalMoveException();
        }
        final Map<Koma, Integer> komaMap = new HashMap<>(this.komaMap);
        komaMap.computeIfPresent(koma, (p, n) -> n - 1);
        return new KomaDai(komaMap);
    }

    public int count(Koma koma) {
        return komaMap.getOrDefault(koma, 0);
    }

}
