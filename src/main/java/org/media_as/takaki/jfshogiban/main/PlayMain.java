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

package org.media_as.takaki.jfshogiban.main;

import org.media_as.takaki.jfshogiban.channel.IMoveChannel;
import org.media_as.takaki.jfshogiban.core.Kyokumen;
import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.move.EndMove;
import org.media_as.takaki.jfshogiban.move.IMovement;
import org.media_as.takaki.jfshogiban.tostr.CsaConverter;
import org.media_as.takaki.jfshogiban.tostr.IStringConverter;
import org.media_as.takaki.jfshogiban.tostr.SfenConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

public final class PlayMain implements IMain {
    private static final Logger LOG = LoggerFactory.getLogger(PlayMain.class);

    private final Kyokumen kyokumen;

    private final IMoveChannel channelSente;
    private final IMoveChannel channelGote;
    private final int moves;  // TODO: move list

    public PlayMain(final Kyokumen kyokumen, final int moves,
                    final IMoveChannel channelSente,
                    final IMoveChannel channelGote) {
        this.kyokumen = kyokumen;
        this.moves = moves;

        final PrintWriter writer = new PrintWriter(System.out);
        writer.format("SFEN: %s\n", convertString(new SfenConverter()));
        writer.format("N+%s\nN-%s\n%s%d\n", channelSente, channelGote,
                kyokumen.convertString(new CsaConverter()), moves);
        writer.flush();

        this.channelSente = channelSente;
        this.channelGote = channelGote;
    }

    @Override
    public IMain next() {
        final IMovement movement = (kyokumen
                .getTurn() == Player.SENTEBAN ? channelSente : channelGote)
                .getMovement(kyokumen);
        LOG.debug("{} {}", kyokumen.getTurn(), movement);
        return movement instanceof EndMove ? new PlayEnd(
                movement.action(kyokumen), moves + 1, channelSente,
                channelGote) : new PlayMain(movement.action(kyokumen),
                moves + 1, channelSente, channelGote);
    }

    public String convertString(final IStringConverter converter) {
        return kyokumen.convertString(converter);
    }

    @Override
    public int getMoves() {
        return moves;
    }
}
