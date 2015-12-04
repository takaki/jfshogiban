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

    private static final Set<Class<? extends BasePiece>> MOCHI_GOMA = new HashSet<>(
            Arrays.asList(KomaHisha.class, KomaKaku.class, KomaKin.class,
                    KomaGin.class, KomaKeima.class, KomaKyosha.class,
                    KomaFu.class));
    private final Map<KomaPlayerPair, Integer> mochigoma;


    public static Mochigoma initialize() {
        return new Mochigoma(MOCHI_GOMA.stream().flatMap(komaClass -> Arrays
                .asList(new KomaPlayerPair(komaClass, Player.SENTEBAN),
                        new KomaPlayerPair(komaClass, Player.GOTEBAN)).stream())
                .collect(Collectors.toMap(Function.identity(), koma -> 0)));
    }

    public Mochigoma(final Map<KomaPlayerPair, Integer> mochigoma) {
        this.mochigoma = Collections.unmodifiableMap(mochigoma);
    }

    public Mochigoma push(final BasePiece koma,
                          final Player player) throws IllegalMoveException {
        if (!MOCHI_GOMA.contains(koma.getClass())) {
            //noinspection HardCodedStringLiteral
            throw new IllegalMoveException(
                    String.format("Can't push %s-%s to mochigoma.", koma,
                            player));
        }
        final Map<KomaPlayerPair, Integer> komaMap = new HashMap<>(mochigoma);
        komaMap.computeIfPresent(new KomaPlayerPair(koma.getClass(), player),
                (p, n) -> n + 1);
        return new Mochigoma(komaMap);
    }

    public Mochigoma remove(final BasePiece koma,
                            final Player player) throws IllegalMoveException {
        if (count(koma, player) <= 0) {
            //noinspection HardCodedStringLiteral
            throw new IllegalMoveException(String.format("%s is empty.",
                    new KomaPlayerPair(koma.getClass(), player)));
        }
        final Map<KomaPlayerPair, Integer> mochigoma = new HashMap<>(
                this.mochigoma);
        mochigoma.computeIfPresent(new KomaPlayerPair(koma.getClass(), player),
                (p, n) -> n - 1);
        return new Mochigoma(mochigoma);
    }

    public int count(final BasePiece koma, final Player player) {
        return mochigoma
                .getOrDefault(new KomaPlayerPair(koma.getClass(), player), 0);
    }

}
