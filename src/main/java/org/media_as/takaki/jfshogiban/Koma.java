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

import java.util.EnumMap;
import java.util.Map;

@SuppressWarnings("HardCodedStringLiteral")
public enum Koma {
    EMPTY,

    SENTE_GYOKU,
    SENTE_HISYA,
    SENTE_KAKU,
    SENTE_KIN,
    SENTE_GIN,
    SENTE_KEIMA,
    SENTE_KYOSHA,
    SENTE_FU,
    SENTE_RYU,
    SENTE_UMA,
    SENTE_NARIGIN,
    SENTE_NARIKEI,
    SENTE_NARIKYO,
    SENTE_TOKIN,

    GOTE_GYOKU,
    GOTE_HISYA,
    GOTE_KAKU,
    GOTE_KIN,
    GOTE_GIN,
    GOTE_KEIMA,
    GOTE_KYOSHA,
    GOTE_FU,
    GOTE_RYU,
    GOTE_UMA,
    GOTE_NARIGIN,
    GOTE_NARIKEI,
    GOTE_NARIKYO,
    GOTE_TOKIN;

    private static final Map<Koma, Koma> CAPTURE_SENTE = new EnumMap<>(
            Koma.class);
    private static final Map<Koma, Koma> CAPTURE_GOTE = new EnumMap<>(
            Koma.class);

    private static final Map<Koma, String> STRING_MAP = new EnumMap<>(
            Koma.class);

    static {
        CAPTURE_SENTE.put(SENTE_GYOKU, SENTE_GYOKU);
        CAPTURE_SENTE.put(SENTE_HISYA, SENTE_HISYA);
        CAPTURE_SENTE.put(SENTE_KAKU, SENTE_KAKU);
        CAPTURE_SENTE.put(SENTE_KIN, SENTE_KIN);
        CAPTURE_SENTE.put(SENTE_GIN, SENTE_GIN);
        CAPTURE_SENTE.put(SENTE_KEIMA, SENTE_KEIMA);
        CAPTURE_SENTE.put(SENTE_KYOSHA, SENTE_KYOSHA);
        CAPTURE_SENTE.put(SENTE_FU, SENTE_FU);
        CAPTURE_SENTE.put(SENTE_RYU, SENTE_HISYA);
        CAPTURE_SENTE.put(SENTE_UMA, SENTE_KAKU);
        CAPTURE_SENTE.put(SENTE_NARIGIN, SENTE_GIN);
        CAPTURE_SENTE.put(SENTE_NARIKEI, SENTE_KEIMA);
        CAPTURE_SENTE.put(SENTE_NARIKYO, SENTE_KYOSHA);
        CAPTURE_SENTE.put(SENTE_TOKIN, SENTE_FU);

        CAPTURE_SENTE.put(GOTE_GYOKU, SENTE_GYOKU);
        CAPTURE_SENTE.put(GOTE_HISYA, SENTE_HISYA);
        CAPTURE_SENTE.put(GOTE_KAKU, SENTE_KAKU);
        CAPTURE_SENTE.put(GOTE_KIN, SENTE_KIN);
        CAPTURE_SENTE.put(GOTE_GIN, SENTE_GIN);
        CAPTURE_SENTE.put(GOTE_KEIMA, SENTE_KEIMA);
        CAPTURE_SENTE.put(GOTE_KYOSHA, SENTE_KYOSHA);
        CAPTURE_SENTE.put(GOTE_FU, SENTE_FU);
        CAPTURE_SENTE.put(GOTE_RYU, SENTE_HISYA);
        CAPTURE_SENTE.put(GOTE_UMA, SENTE_KAKU);
        CAPTURE_SENTE.put(GOTE_NARIGIN, SENTE_KIN);
        CAPTURE_SENTE.put(GOTE_NARIKEI, SENTE_KEIMA);
        CAPTURE_SENTE.put(GOTE_NARIKYO, SENTE_KYOSHA);
        CAPTURE_SENTE.put(GOTE_TOKIN, SENTE_FU);

        CAPTURE_GOTE.put(SENTE_GYOKU, GOTE_GYOKU);
        CAPTURE_GOTE.put(SENTE_HISYA, GOTE_HISYA);
        CAPTURE_GOTE.put(SENTE_KAKU, GOTE_KAKU);
        CAPTURE_GOTE.put(SENTE_KIN, GOTE_KIN);
        CAPTURE_GOTE.put(SENTE_GIN, GOTE_GIN);
        CAPTURE_GOTE.put(SENTE_KEIMA, GOTE_KEIMA);
        CAPTURE_GOTE.put(SENTE_KYOSHA, GOTE_KYOSHA);
        CAPTURE_GOTE.put(SENTE_FU, GOTE_FU);
        CAPTURE_GOTE.put(SENTE_RYU, GOTE_HISYA);
        CAPTURE_GOTE.put(SENTE_UMA, GOTE_KAKU);
        CAPTURE_GOTE.put(SENTE_NARIGIN, GOTE_GIN);
        CAPTURE_GOTE.put(SENTE_NARIKEI, GOTE_KEIMA);
        CAPTURE_GOTE.put(SENTE_NARIKYO, GOTE_KYOSHA);
        CAPTURE_GOTE.put(SENTE_TOKIN, GOTE_FU);

        CAPTURE_GOTE.put(GOTE_GYOKU, GOTE_GYOKU);
        CAPTURE_GOTE.put(GOTE_HISYA, GOTE_HISYA);
        CAPTURE_GOTE.put(GOTE_KAKU, GOTE_KAKU);
        CAPTURE_GOTE.put(GOTE_KIN, GOTE_KIN);
        CAPTURE_GOTE.put(GOTE_GIN, GOTE_GIN);
        CAPTURE_GOTE.put(GOTE_KEIMA, GOTE_KEIMA);
        CAPTURE_GOTE.put(GOTE_KYOSHA, GOTE_KYOSHA);
        CAPTURE_GOTE.put(GOTE_FU, GOTE_FU);
        CAPTURE_GOTE.put(GOTE_RYU, GOTE_HISYA);
        CAPTURE_GOTE.put(GOTE_UMA, GOTE_KAKU);
        CAPTURE_GOTE.put(GOTE_NARIGIN, GOTE_KIN);
        CAPTURE_GOTE.put(GOTE_NARIKEI, GOTE_KEIMA);
        CAPTURE_GOTE.put(GOTE_NARIKYO, GOTE_KYOSHA);
        CAPTURE_GOTE.put(GOTE_TOKIN, GOTE_FU);

        STRING_MAP.put(SENTE_HISYA, " 飛");
        STRING_MAP.put(SENTE_RYU, " 龍");
        STRING_MAP.put(GOTE_HISYA, "v飛");
        STRING_MAP.put(GOTE_RYU, "v龍");
        STRING_MAP.put(SENTE_KAKU, " 角");
        STRING_MAP.put(SENTE_UMA, " 馬");
        STRING_MAP.put(GOTE_KAKU, "v角");
        STRING_MAP.put(GOTE_UMA, "v馬");
        STRING_MAP.put(SENTE_KIN, " 金");
        STRING_MAP.put(GOTE_KIN, "v金");
        STRING_MAP.put(SENTE_GIN, " 銀");
        STRING_MAP.put(SENTE_NARIGIN, " 全");
        STRING_MAP.put(GOTE_GIN, "v銀");
        STRING_MAP.put(GOTE_NARIGIN, "v全");
        STRING_MAP.put(SENTE_KEIMA, " 桂");
        STRING_MAP.put(SENTE_NARIKEI, " 圭");
        STRING_MAP.put(GOTE_KEIMA, "v桂");
        STRING_MAP.put(GOTE_NARIKEI, "v圭");
        STRING_MAP.put(SENTE_KYOSHA, " 香");
        STRING_MAP.put(SENTE_NARIKYO, " 杏");
        STRING_MAP.put(GOTE_KYOSHA, "v香");
        STRING_MAP.put(GOTE_NARIKYO, "v杏");
        STRING_MAP.put(SENTE_FU, " 歩");
        STRING_MAP.put(SENTE_TOKIN, " と");
        STRING_MAP.put(GOTE_FU, "v歩");
        STRING_MAP.put(GOTE_TOKIN, "vと");
        STRING_MAP.put(SENTE_GYOKU, " 王");
        STRING_MAP.put(GOTE_GYOKU, "v玉");
        STRING_MAP.put(EMPTY, "   ");

    }

    @Override
    public String toString() {
        return STRING_MAP.get(this);
    }

    public Koma changeCaptured(
            final Player player) throws IllegalMoveException {
        if (this == EMPTY) {
            throw new IllegalMoveException();
        }
        return player == Player.SENTEBAN ? CAPTURE_SENTE
                .get(this) : CAPTURE_GOTE.get(this);
    }

}
