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

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.media_as.takaki.jfshogiban.channel.IMoveChannel;
import org.media_as.takaki.jfshogiban.channel.usi.UsiChannel;
import org.media_as.takaki.jfshogiban.core.Kyokumen;
import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.gui.WinMain;
import org.media_as.takaki.jfshogiban.move.EndMove;
import org.media_as.takaki.jfshogiban.move.IMovement;
import org.media_as.takaki.jfshogiban.tostr.CsaConverter;
import org.media_as.takaki.jfshogiban.tostr.SfenConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PlayWinMain implements IMain {
    private static final Logger LOG = LoggerFactory.getLogger(PlayWinMain.class);

    private final Kyokumen startpos;

    private final IMoveChannel channelSente;
    private final IMoveChannel channelGote;
    private final List<IMovement> movements;
    private final StackPane rootPane;
    private final Stage stage;

    public PlayWinMain(final Kyokumen startpos, final List<IMovement> movements,
                       final IMoveChannel channelSente,
                       final IMoveChannel channelGote, final StackPane rootPane, final Stage stage) {
        this.rootPane = rootPane;
        this.stage = stage;
        LOG.debug("{}", movements);
        this.startpos = startpos;
        this.movements = Collections.unmodifiableList(movements);
        this.channelSente = channelSente;
        this.channelGote = channelGote;
    }

    @Override
    public IMain next() {
        final IMovement movement = (startpos
                .getTurn() == Player.SENTEBAN ? channelSente : channelGote)
                .getMovement(startpos, movements);
        LOG.debug("{} {}", startpos.getTurn(), movement);
        final List<IMovement> movements = new ArrayList<>(this.movements);
        movements.add(movement);

        final Kyokumen current = movements.stream()
                .reduce(startpos, (kyokumen, move) -> move.action(kyokumen), (x, y) -> y);
        final PrintWriter writer = new PrintWriter(System.out);
        writer.format("SFEN: %s\n", current.convertString(new SfenConverter()));
        writer.format("N+%s\nN-%s\n%s%d\n", channelSente, channelGote,
                current.convertString(new CsaConverter()), movements.size());
        writer.flush();
        LOG.debug("before draw");

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        WinMain.drawPiece(gridPane, current);
        LOG.debug("a");
        rootPane.getChildren().removeAll();
        LOG.debug("b");
        rootPane.getChildren().add(gridPane);
        stage.show();
        LOG.debug("after draw");
        return movement instanceof EndMove ? new PlayEnd(startpos, movements,
                channelSente, channelGote) : new PlayWinMain(startpos, movements,
                channelSente, channelGote, rootPane, stage);
    }


    @Override
    public int getMoves() {
        return movements.size();
    }
}
