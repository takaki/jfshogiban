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

    @SuppressWarnings({"MethodWithMultipleReturnPoints", "OverlyComplexMethod", "OverlyLongMethod"})
    public Koma changeCaptured(
            final Player player) throws IllegalMoveException {
        //noinspection SwitchStatementWithTooManyBranches,SwitchStatement
        switch (this) {
            case SENTE_HISYA:
            case SENTE_RYU:
            case GOTE_HISYA:
            case GOTE_RYU:
                return player == Player.SENTEBAN ? SENTE_HISYA : GOTE_HISYA;
            case SENTE_KAKU:
            case SENTE_UMA:
            case GOTE_KAKU:
            case GOTE_UMA:
                return player == Player.SENTEBAN ? SENTE_KAKU : GOTE_KAKU;
            case SENTE_KIN:
            case GOTE_KIN:
                return player == Player.SENTEBAN ? SENTE_KIN : GOTE_KIN;
            case SENTE_GIN:
            case SENTE_NARIGIN:
            case GOTE_GIN:
            case GOTE_NARIGIN:
                return player == Player.SENTEBAN ? SENTE_GIN : GOTE_GIN;
            case SENTE_KEIMA:
            case SENTE_NARIKEI:
            case GOTE_KEIMA:
            case GOTE_NARIKEI:
                return player == Player.SENTEBAN ? SENTE_KEIMA : GOTE_KEIMA;
            case SENTE_KYOSHA:
            case SENTE_NARIKYO:
            case GOTE_KYOSHA:
            case GOTE_NARIKYO:
                return player == Player.SENTEBAN ? SENTE_KYOSHA : GOTE_KYOSHA;
            case SENTE_FU:
            case SENTE_TOKIN:
            case GOTE_FU:
            case GOTE_TOKIN:
                return player == Player.SENTEBAN ? SENTE_FU : GOTE_FU;
            case SENTE_GYOKU:
            case GOTE_GYOKU:
            case EMPTY:
            default:
                throw new IllegalMoveException();
        }
    }

}
