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

import java.util.stream.IntStream;

// Kyokumen understands Shogi move rule.
public final class Kyokumen {
    private final Banmen banmen;
    private final Player turn;

    public static Kyokumen startPosition() throws IllegalMoveException {
        return new Kyokumen(Banmen.startPosition(), Player.SENTEBAN);
    }

    public Kyokumen(final Banmen banmen, final Player turn) {
        this.banmen = banmen;
        this.turn = turn;
    }

    public Player getTurn() {
        return turn;
    }

    public Koma get(final int x, final int y) throws IllegalMoveException {
        return banmen.get(x, y);
    }

    public Kyokumen move(final int fx, final int fy, final int tx,
                         final int ty) throws IllegalMoveException {
        if (!isOwn(fx, fy)) {
            throw new IllegalMoveException("Try to move not own piece.");
        }
        checkRule(fx, fy, tx, ty);
        return banmen.isEmpty(tx, ty) ? new Kyokumen(
                banmen.move(fx, fy, tx, ty), turn.next()) : capture(fx, fy, tx,
                ty);
    }

    private Kyokumen capture(final int fx, final int fy, final int tx,
                             final int ty) throws IllegalMoveException {
        if (isOwn(tx, ty)) {
            throw new IllegalMoveException("Try to put on own piece.");
        }
        return new Kyokumen(banmen.capture(tx, ty, turn).move(fx, fy, tx, ty),
                turn.next());
    }

    private boolean isOwn(final int x,
                          final int y) throws IllegalMoveException {
        return get(x, y).isOwn(turn);
    }

    private void checkRule(final int fx, final int fy, final int tx,
                           final int ty) throws IllegalMoveException {
        final Koma koma = get(fx, fy);
        switch (koma) {
            case EMPTY:
                break;
            case SENTE_GYOKU:
                break;
            case SENTE_HISYA:
                break;
            case SENTE_KAKU:
                break;
            case SENTE_KIN:
                break;
            case SENTE_GIN:
                break;
            case SENTE_RYU:
                break;
            case SENTE_UMA:
                break;
            case SENTE_NARIGIN:
                break;
            case SENTE_NARIKEI:
                break;
            case SENTE_NARIKYO:
                break;
            case SENTE_TOKIN:
                break;
            case GOTE_GYOKU:
                break;
            case GOTE_HISYA:
                break;
            case GOTE_KAKU:
                break;
            case GOTE_KIN:
                break;
            case GOTE_GIN:
                break;
            case GOTE_RYU:
                break;
            case GOTE_UMA:
                break;
            case GOTE_NARIGIN:
            case GOTE_NARIKEI:
            case GOTE_NARIKYO:
            case GOTE_TOKIN:
                break;
            case SENTE_KEIMA:
            case GOTE_KEIMA:
                if (!(Math.abs(fx - tx) == 1 && (fy - ty) * turn.sign() == 2)) {
                    throw new IllegalMoveException(koma + " move is illegal.");
                }
                break;
            case SENTE_KYOSHA:
            case GOTE_KYOSHA:
                if (!(fx == tx && (fy - ty) * turn.sign() > 0)) {
                    throw new IllegalMoveException(koma + " move is illegal.");
                }
                if (autoIntRange(fy, ty).anyMatch(y -> {
                    try {
                        return !isEmpty(fx, y);
                    } catch (final IllegalMoveException ignored) {
                        return true;
                    }
                })) {
                    throw new IllegalMoveException(koma + " move is illegal.");
                }
                break;
            case SENTE_FU:
            case GOTE_FU:
                if (!(fx == tx && (fy - ty) * turn.sign() == 1)) {
                    throw new IllegalMoveException(koma + " move is illegal.");
                }
                break;
        }
    }

    private boolean isEmpty(final int x,
                            final int y) throws IllegalMoveException {
        return banmen.isEmpty(x, y);
    }

    private static IntStream autoIntRange(final int s, final int e) {
        if (s < e) {
            return IntStream.rangeClosed(s + 1, e - 1);
        } else {
            return IntStream.rangeClosed(e + 1, s - 1);
        }
    }

    public int countMochigoma(final Koma koma) {
        return banmen.countMochigoma(koma);
    }
}
