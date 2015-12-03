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

    @SuppressWarnings({"FeatureEnvy", "MethodWithMultipleReturnPoints", "OverlyComplexMethod", "OverlyLongMethod"})
    private void checkRule(final int fx, final int fy, final int tx,
                           final int ty) throws IllegalMoveException {
        final Koma koma = get(fx, fy);
        //noinspection SwitchStatementWithTooManyBranches,SwitchStatement
        switch (koma) {
            case EMPTY:
                return;
            case SENTE_GYOKU:
            case GOTE_GYOKU:
                if (Math.abs(fx - ty) <= 1 || Math.abs(fy - ty) <= 1) {
                    return;
                }
                // 王取りのチェックをどこかに
                break;
            case SENTE_RYU:
            case GOTE_RYU:
                if (Math.abs(fx - tx) == 1 && Math.abs(fy - ty) == 1) {
                    return;
                }
                //noinspection fallthrough
            case SENTE_HISYA:
            case GOTE_HISYA:
                if (fx == tx && autoIntRange(fy, ty).allMatch(y -> {
                    try {
                        return isEmpty(fx, y);
                    } catch (final IllegalMoveException ignored) {
                        return false;
                    }
                })) {
                    return;

                }
                if (fy == ty && autoIntRange(fx, tx).allMatch(x -> {
                    try {
                        return isEmpty(x, fy);
                    } catch (final IllegalMoveException ignored) {
                        return false;
                    }
                })) {
                    return;

                }
                break;
            case SENTE_UMA:
            case GOTE_UMA:
                if (fx == tx && Math.abs(fy - ty) == 1) {
                    return;
                }
                if (Math.abs(fx - tx) == 1 && fy == ty) {
                    return;
                }
                //noinspection fallthrough
            case SENTE_KAKU:
            case GOTE_KAKU:
                if (Math.abs(fx - tx) == Math.abs(fy - ty)) {
                    final int diffX = fx > tx ? 1 : -1;
                    final int diffY = fy > ty ? 1 : -1;
                    if (fx > tx && fy > ty &&
                            IntStream.rangeClosed(1, Math.abs(fx - tx))
                                    .allMatch(diff -> {
                                        try {
                                            return isEmpty(fx + diffX,
                                                    fx + diffY);
                                        } catch (final IllegalMoveException ignored) {
                                            return false;
                                        }
                                    })) {
                        return;
                    }
                }
                break;
            case SENTE_KIN:
            case SENTE_NARIGIN:
            case SENTE_NARIKEI:
            case SENTE_NARIKYO:
            case SENTE_TOKIN:
            case GOTE_KIN:
            case GOTE_NARIGIN:
            case GOTE_NARIKEI:
            case GOTE_NARIKYO:
            case GOTE_TOKIN:
                if (Math.abs(fx - ty) <= 1 && fy - ty == turn.sign()) {
                    return;
                }
                if (Math.abs(fx - ty) == 1 && fy == ty) {
                    return;
                }
                if (fx - ty == 0 && fy - ty == -turn.sign()) {
                    return;
                }
                break;
            case SENTE_GIN:
            case GOTE_GIN:
                if (Math.abs(fx - ty) <= 1 && fy - ty == turn.sign()) {
                    return;
                }
                if (Math.abs(fx - ty) == 1 && fy - ty == -turn.sign()) {
                    return;
                }
                break;
            case SENTE_KEIMA:
            case GOTE_KEIMA:
                if (Math.abs(fx - tx) == 1 && (fy - ty) * turn.sign() == 2) {
                    return;
                }
                break;
            case SENTE_KYOSHA:
            case GOTE_KYOSHA:
                if (fx == tx && (fy - ty) * turn.sign() > 0 && autoIntRange(fy,
                        ty).allMatch(y -> {
                    try {
                        return isEmpty(fx, y);
                    } catch (final IllegalMoveException ignored) {
                        return false;
                    }
                })) {
                    return;
                }
                break;
            case SENTE_FU:
            case GOTE_FU:
                if (fx == tx && fy - ty == turn.sign()) {
                    return;
                }
                break;
        }
        throw new IllegalMoveException(koma + " move is illegal.");
    }

    private boolean isEmpty(final int x,
                            final int y) throws IllegalMoveException {
        return banmen.isEmpty(x, y);
    }

    private static IntStream autoIntRange(final int start, final int end) {
        return start < end ? IntStream
                .rangeClosed(start + 1, end - 1) : IntStream
                .rangeClosed(end + 1, start - 1);
    }

    public int countMochigoma(final Koma koma) {
        return banmen.countMochigoma(koma);
    }
}
