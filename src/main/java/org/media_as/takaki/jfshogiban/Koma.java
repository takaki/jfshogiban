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

@SuppressWarnings("ClassWithTooManyFields")
public final class Koma {

    public static final BasePiece SENTE_GYOKU = new KomaGyoku(Player.SENTEBAN);
    public static final BasePiece SENTE_HISYA = new KomaHisha(Player.SENTEBAN);
    public static final BasePiece SENTE_KAKU = new KomaKaku(Player.SENTEBAN);
    public static final BasePiece SENTE_KIN = new KomaKin(Player.SENTEBAN);
    public static final BasePiece SENTE_GIN = new KomaGin(Player.SENTEBAN);
    public static final BasePiece SENTE_KEIMA = new KomaKeima(Player.SENTEBAN);
    public static final BasePiece SENTE_KYOSHA = new KomaKyosha(
            Player.SENTEBAN);
    public static final BasePiece SENTE_FU = new KomaFu(Player.SENTEBAN);
    public static final BasePiece SENTE_RYU = new KomaRyu(Player.SENTEBAN);
    public static final BasePiece SENTE_UMA = new KomaUma(Player.SENTEBAN);
    public static final BasePiece SENTE_NARIGIN = new KomaNarigin(
            Player.SENTEBAN);
    public static final BasePiece SENTE_NARIKEI = new KomaNarikei(
            Player.SENTEBAN);
    public static final BasePiece SENTE_NARIKYO = new KomaNarikyo(
            Player.SENTEBAN);
    public static final BasePiece SENTE_TOKIN = new KomaTokin(Player.SENTEBAN);

    public static final BasePiece GOTE_GYOKU = new KomaGyoku(Player.GOTEBAN);
    public static final BasePiece GOTE_HISYA = new KomaHisha(Player.GOTEBAN);
    public static final BasePiece GOTE_KAKU = new KomaKaku(Player.GOTEBAN);
    public static final BasePiece GOTE_KIN = new KomaKin(Player.GOTEBAN);
    public static final BasePiece GOTE_GIN = new KomaGin(Player.GOTEBAN);
    public static final BasePiece GOTE_KEIMA = new KomaKeima(Player.GOTEBAN);
    public static final BasePiece GOTE_KYOSHA = new KomaKyosha(Player.GOTEBAN);
    public static final BasePiece GOTE_FU = new KomaFu(Player.GOTEBAN);
    public static final BasePiece GOTE_RYU = new KomaRyu(Player.GOTEBAN);
    public static final BasePiece GOTE_UMA = new KomaUma(Player.GOTEBAN);
    public static final BasePiece GOTE_NARIGIN = new KomaNarigin(
            Player.GOTEBAN);
    public static final BasePiece GOTE_NARIKEI = new KomaNarikei(
            Player.GOTEBAN);
    public static final BasePiece GOTE_NARIKYO = new KomaNarikyo(
            Player.GOTEBAN);
    public static final BasePiece GOTE_TOKIN = new KomaTokin(Player.SENTEBAN);

    private Koma() {
    }

//    private static final EnumSet<Koma> OWN_SENTE = EnumSet
//            .of(SENTE_GYOKU, SENTE_HISYA, SENTE_KAKU, SENTE_KIN, SENTE_GIN,
//                    SENTE_KEIMA, SENTE_KYOSHA, SENTE_FU, SENTE_RYU, SENTE_UMA,
//                    SENTE_NARIGIN, SENTE_NARIKEI, SENTE_NARIKYO, SENTE_TOKIN);
//
//    private static final EnumSet<Koma> OWN_GOTE = EnumSet
//            .of(GOTE_GYOKU, GOTE_HISYA, GOTE_KAKU, GOTE_KIN, GOTE_GIN,
//                    GOTE_KEIMA, GOTE_KYOSHA, GOTE_FU, GOTE_RYU, GOTE_UMA,
//                    GOTE_NARIGIN, GOTE_NARIKEI, GOTE_NARIKYO, GOTE_TOKIN);

//    private static final Map<Koma, Koma> CAPTURE_SENTE = new EnumMap<>(
//            Koma.class);
//    private static final Map<Koma, Koma> CAPTURE_GOTE = new EnumMap<>(
//            Koma.class);


//    public Koma changeCaptured(
//            final Player player) throws IllegalMoveException {
//        if (this == EMPTY) {
//            throw new IllegalMoveException();
//        }
//        return player == Player.SENTEBAN ? CAPTURE_SENTE
//                .get(this) : CAPTURE_GOTE.get(this);
//    }
//
//    public boolean isOwn(final Player player) {
//        return player == Player.SENTEBAN ? OWN_SENTE.contains(this) : OWN_GOTE
//                .contains(this);
//    }

}
