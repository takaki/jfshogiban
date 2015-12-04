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

}
