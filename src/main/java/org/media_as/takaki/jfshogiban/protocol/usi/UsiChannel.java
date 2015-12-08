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

package org.media_as.takaki.jfshogiban.protocol.usi;

import com.codepoetics.protonpack.StreamUtils;
import org.media_as.takaki.jfshogiban.IllegalMoveException;
import org.media_as.takaki.jfshogiban.PlayMove;
import org.media_as.takaki.jfshogiban.Player;
import org.media_as.takaki.jfshogiban.action.IMovement;
import org.media_as.takaki.jfshogiban.protocol.IMoveChannel;

import java.util.List;
import java.util.stream.Stream;

public class UsiChannel implements IMoveChannel {
    private final UsiState state;

    public static UsiChannel initialize() {
        return new UsiChannel(new WaitUsiOK());
    }

    public UsiChannel(final UsiState state) {
        final Stream<UsiState> iterate = Stream.iterate(state, state0 -> {
            state.getCommand().stream().forEach(s -> {
                // write IO
                // read IO
                return;
            });
            String response = "";
            return state0.getNextState(response);
        });
        this.state = StreamUtils.takeUntil(iterate,
                state0 -> state0.getClass().equals(UsiNewgame.class))
                .reduce((a, b) -> b).get(); // TODO: Optional?
    }

    @Override
    public IMovement getMovement(PlayMove playMove,
                                 Player player) throws IllegalMoveException {
        final Stream<UsiState> iterate = Stream.iterate(state, state0 -> {
            state.getCommand().stream().forEach(s -> {
                // write IO
                // read IO
                return;
            });
            String response = "";
            return state0.getNextState(response);
        });
        final UsiState sendMovement = StreamUtils.takeUntil(iterate,
                state0 -> state0.getClass().equals(SendMovement.class))
                .reduce((a, b) -> b).get();// TODO: Optional?
        return toMovement(sendMovement.getCommand());
    }

    private IMovement toMovement(List<String> command) {
        return null;
    }

    @Override
    public UsiChannel getNextChannel() {
        return new UsiChannel(state.getNextState(""));
    }
}
