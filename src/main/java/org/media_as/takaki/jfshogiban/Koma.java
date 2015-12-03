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

    }

    @SuppressWarnings({"HardCodedStringLiteral", "MethodWithMultipleReturnPoints", "OverlyComplexMethod", "OverlyLongMethod"})
    @Override
    public String toString() {
        //noinspection SwitchStatementWithTooManyBranches,SwitchStatement
        switch (this) {
            case SENTE_HISYA:
                return " 飛";
            case SENTE_RYU:
                return " 龍";
            case GOTE_HISYA:
                return "v飛";
            case GOTE_RYU:
                return "v龍";
            case SENTE_KAKU:
                return " 角";
            case SENTE_UMA:
                return " 馬";
            case GOTE_KAKU:
                return "v角";
            case GOTE_UMA:
                return "v馬";
            case SENTE_KIN:
                return " 金";
            case GOTE_KIN:
                return "v金";
            case SENTE_GIN:
                return " 銀";
            case SENTE_NARIGIN:
                return " 全";
            case GOTE_GIN:
                return "v銀";
            case GOTE_NARIGIN:
                return "v全";
            case SENTE_KEIMA:
                return " 桂";
            case SENTE_NARIKEI:
                return " 圭";
            case GOTE_KEIMA:
                return "v桂";
            case GOTE_NARIKEI:
                return "v圭";
            case SENTE_KYOSHA:
                return " 香";
            case SENTE_NARIKYO:
                return " 杏";
            case GOTE_KYOSHA:
                return "v香";
            case GOTE_NARIKYO:
                return "v杏";
            case SENTE_FU:
                return " 歩";
            case SENTE_TOKIN:
                return " と";
            case GOTE_FU:
                return "v歩";
            case GOTE_TOKIN:
                return "vと";
            case SENTE_GYOKU:
                return " 王";
            case GOTE_GYOKU:
                return "v玉";
            case EMPTY:
                return "   ";
        }
        return "   ";
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
