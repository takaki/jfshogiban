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
public enum Koma {
    ;

    public static final IPiece SENTE_GYOKU = new KomaGyoku(Player.SENTEBAN);
    public static final IPiece SENTE_HISYA = new KomaHisha(Player.SENTEBAN);
    public static final IPiece SENTE_KAKU = new KomaKaku(Player.SENTEBAN);
    public static final IPiece SENTE_KIN = new KomaKin(Player.SENTEBAN);
    public static final IPiece SENTE_GIN = new KomaGin(Player.SENTEBAN);
    public static final IPiece SENTE_KEIMA = new KomaKeima(Player.SENTEBAN);
    public static final IPiece SENTE_KYOSHA = new KomaKyosha(Player.SENTEBAN);
    public static final IPiece SENTE_FU = new KomaFu(Player.SENTEBAN);
    public static final IPiece SENTE_RYU = new KomaRyu(Player.SENTEBAN);
    public static final IPiece SENTE_UMA = new KomaUma(Player.SENTEBAN);
    public static final IPiece SENTE_NARIGIN = new KomaNarigin(Player.SENTEBAN);
    public static final IPiece SENTE_NARIKEI = new KomaNarikei(Player.SENTEBAN);
    public static final IPiece SENTE_NARIKYO = new KomaNarikyo(Player.SENTEBAN);
    public static final IPiece SENTE_TOKIN = new KomaTokin(Player.SENTEBAN);

    public static final IPiece GOTE_GYOKU = new KomaGyoku(Player.GOTEBAN);
    public static final IPiece GOTE_HISYA = new KomaHisha(Player.GOTEBAN);
    public static final IPiece GOTE_KAKU = new KomaKaku(Player.GOTEBAN);
    public static final IPiece GOTE_KIN = new KomaKin(Player.GOTEBAN);
    public static final IPiece GOTE_GIN = new KomaGin(Player.GOTEBAN);
    public static final IPiece GOTE_KEIMA = new KomaKeima(Player.GOTEBAN);
    public static final IPiece GOTE_KYOSHA = new KomaKyosha(Player.GOTEBAN);
    public static final IPiece GOTE_FU = new KomaFu(Player.GOTEBAN);
    public static final IPiece GOTE_RYU = new KomaRyu(Player.GOTEBAN);
    public static final IPiece GOTE_UMA = new KomaUma(Player.GOTEBAN);
    public static final IPiece GOTE_NARIGIN = new KomaNarigin(Player.GOTEBAN);
    public static final IPiece GOTE_NARIKEI = new KomaNarikei(Player.GOTEBAN);
    public static final IPiece GOTE_NARIKYO = new KomaNarikyo(Player.GOTEBAN);
    public static final IPiece GOTE_TOKIN = new KomaTokin(Player.SENTEBAN);

}
