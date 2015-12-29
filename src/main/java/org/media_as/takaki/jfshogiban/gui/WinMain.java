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
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.media_as.takaki.jfshogiban.channel.usi.UsiChannel;
import org.media_as.takaki.jfshogiban.core.Kyokumen;
import org.media_as.takaki.jfshogiban.core.Player;
import org.media_as.takaki.jfshogiban.main.IMain;
import org.media_as.takaki.jfshogiban.main.PlayEnd;
import org.media_as.takaki.jfshogiban.main.PlayMain;
import org.media_as.takaki.jfshogiban.main.PlayWinMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class WinMain extends Application {
    private static final Logger LOG = LoggerFactory.getLogger(WinMain.class);

    private StackPane root;

    public static void main(final String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        root = new StackPane();
        //final Kyokumen kyokumen = Kyokumen.startPosition();
//        GridPane gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.runLater(() -> {
                    try {
                        update(stage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
        final Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    public void update(Stage stage) throws IOException {
        final Stream<IMain> iterate = Stream.iterate(
                new PlayWinMain(Kyokumen.startPosition(), new ArrayList<>(),
                        new UsiChannel(
                                Paths.get("/home/takaki/tmp/gpsfish/src"),
                                "gpsfish"),
                        new UsiChannel(Paths.get("/home/takaki/tmp/apery/bin"),
                                "apery"), root, stage), IMain::next);
        final IMain playEnd = iterate
                .filter(playMain -> playMain instanceof PlayEnd).findFirst()
                .get();
        final int moves = playEnd.getMoves() - 1;

        LOG.debug("Game End: {} WON {} Moves",
                (moves & 1) == 1 ? Player.SENTEBAN : Player.GOTEBAN, moves);
    }

    public static void drawPiece(final GridPane gridPane,
                                 final Kyokumen kyokumen) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                final int finalX = 9 - x;
                final int finalY = y + 1;
                final Optional<ImageView> view = kyokumen.get(finalX, finalY)
                        .map(PieceImage::getImageView);
                final int finalX1 = x;
                final int finalY1 = y + 1;
                view.ifPresent(v -> {
                    v.setOnMouseClicked(ev -> System.out.println(
                            String.format("press %d %d", finalX, finalY)));
                    gridPane.add(v, finalX1, finalY1);
                });
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
    }

}
