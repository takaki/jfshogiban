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

import org.media_as.takaki.jfshogiban.IllegalMoveException;
import org.media_as.takaki.jfshogiban.Kyokumen;
import org.media_as.takaki.jfshogiban.action.IMovement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PlayMove {
    private final Kyokumen initial;
    private final List<IMovement> moves;

    public static PlayMove startPosition() throws IllegalMoveException {
        return new PlayMove(Kyokumen.startPosition(), Arrays.asList());
    }

    public PlayMove(final Kyokumen initial, final List<IMovement> moves) {
        this.initial = initial;
        this.moves = Collections.unmodifiableList(moves);
    }

    public PlayMove getNextKyokumen(final IMovement move) {
        final List<IMovement> moves = this.moves;
        moves.add(move);
        return new PlayMove(initial, moves);

    }

    public Kyokumen getCurrentKyokumen() throws IllegalMoveException {
        //noinspection LocalVariableOfConcreteClass
        Kyokumen kyokumen = initial;
        for (final IMovement move : moves) {
            kyokumen = move.action(kyokumen);
        }
        return kyokumen;
    }
}
