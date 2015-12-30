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

package org.media_as.takaki.jfshogiban.gui;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.media_as.takaki.jfshogiban.channel.IMoveChannel;
import org.media_as.takaki.jfshogiban.channel.usi.UsiChannel;
import org.media_as.takaki.jfshogiban.core.Koma;
import org.media_as.takaki.jfshogiban.core.Kyokumen;
import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.main.IMain;
import org.media_as.takaki.jfshogiban.main.PlayEnd;
import org.media_as.takaki.jfshogiban.main.PlayWinMain;
import org.media_as.takaki.jfshogiban.move.IMovement;
import org.media_as.takaki.jfshogiban.piece.IPiece;
import org.media_as.takaki.jfshogiban.tostr.CsaConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WinMain extends Application {
    private static final Logger LOG = LoggerFactory.getLogger(WinMain.class);

    private final ImageView[][] views = new ImageView[9][9];
    private final Label gote = new Label();
    private final Label sente = new Label();
    private final Label info = new Label();

    public static void main(final String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final VBox root = new VBox();
        final Button button = new Button();
        button.setText("start");
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    update();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        button.setOnMouseClicked(mouseEvent -> {
            button.setDisable(true);
            button.setText("Running");
            final ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(task);
        });

        gote.setText("Gote");
        sente.setText("Sente");
        final HBox goteBox = new HBox();
        final HBox senteBox = new HBox();
        final HBox infoBox = new HBox();
        goteBox.getChildren().addAll(gote);
        senteBox.getChildren().addAll(sente);
        infoBox.getChildren().add(info);
        root.getChildren()
                .addAll(goteBox, initGridPane(), senteBox, infoBox, button);
        final Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
        drawPiece(Kyokumen.startPosition());
    }

    public void update() throws IOException {
        final Stream<IMain> iterate = Stream.iterate(
                new PlayWinMain(Kyokumen.startPosition(), new ArrayList<>(),
                        new UsiChannel(
                                Paths.get("/home/takaki/tmp/gpsfish/src"),
                                "gpsfish"),
                        new UsiChannel(Paths.get("/home/takaki/tmp/apery/bin"),
                                "apery"), this), IMain::next);
        final IMain playEnd = iterate
                .filter(playMain -> playMain instanceof PlayEnd).findFirst()
                .get();
        final int moves = playEnd.getMoves() - 1;

        LOG.debug("Game End: {} WON {} Moves",
                (moves & 1) == 1 ? Player.SENTEBAN : Player.GOTEBAN, moves);
    }

    private GridPane initGridPane() {
        final GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                final ImageView view = new ImageView();
                view.setFitHeight(50);
                view.setFitWidth(50);
                views[x][y] = view;
                gridPane.add(view, x, y + 1);
            }
        }
        for (int x = 0; x < 9; x++) {
            final Label label = new Label();
            label.setPrefWidth(50);
            label.setText(Integer.toString(9 - x));
            label.setAlignment(Pos.CENTER);
            gridPane.add(label, x, 0);
        }
        for (int y = 0; y < 9; y++) {
            final Label label = new Label();
            label.setOnMouseClicked(
                    mouseEvent -> System.out.println("clicked"));
            label.setPrefHeight(50);
            label.setPrefWidth(30);
            label.setText(Integer.toString(y + 1));
            label.setAlignment(Pos.CENTER);
            gridPane.add(label, 9, y + 1);
        }
        return gridPane;
    }

    public void drawPiece(final Kyokumen kyokumen) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                final int finalX = x;
                final int finalY = y;
                views[finalX][finalY].setImage(null);
                final Optional<Image> view = kyokumen
                        .get(9 - finalX, finalY + 1)
                        .map(PieceImage::getSvgImage);
                view.ifPresent(v -> views[finalX][finalY].setImage(v));
            }
        }
    }

    public void drawInfo(IMoveChannel channelSente, IMoveChannel channelGote,
                         Kyokumen current, List<IMovement> movements) {
        final List<IPiece> SENTE_LIST = Arrays
                .asList(Koma.SENTE_FU, Koma.SENTE_KYOSHA, Koma.SENTE_KEIMA,
                        Koma.SENTE_GIN, Koma.SENTE_KIN, Koma.SENTE_KAKU,
                        Koma.SENTE_HISYA);
        final List<IPiece> GOTE_LIST = Arrays
                .asList(Koma.GOTE_FU, Koma.GOTE_KYOSHA, Koma.GOTE_KEIMA,
                        Koma.GOTE_GIN, Koma.GOTE_KIN, Koma.GOTE_KAKU,
                        Koma.GOTE_HISYA);


        sente.setText(channelSente + SENTE_LIST.stream()
                .map(p -> p.convertString(new CsaConverter()) + current
                        .countMochigoma(p)).collect(Collectors.joining(",")));
        gote.setText(channelGote + GOTE_LIST.stream()
                .map(p -> p.convertString(new CsaConverter()) + current
                        .countMochigoma(p)).collect(Collectors.joining(",")));
        info.setText(String.format("Moves = %d", movements.size()));
    }

}
