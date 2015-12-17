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
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.media_as.takaki.jfshogiban.core.Kyokumen;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class WinMain extends Application {

    private static final String HOME_TAKAKI_TMP_XBOARD_TEST_SVG = "/home/takaki/tmp/xboard/WhiteRook.svg";

    public static void main(final String[] args) throws Exception {
//        jpg();
        launch(args);
    }

    @SuppressWarnings({"ProhibitedExceptionDeclared", "UseOfSystemOutOrSystemErr", "DesignForExtension"})
    @Override
    public void start(final Stage stage) throws Exception {
        final StackPane root = new StackPane();
        //        root.getChildren().add(btn);
        final GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        final Kyokumen kyokumen = Kyokumen.startPosition();
        drawPiece(gridPane, kyokumen);
        root.getChildren().add(gridPane);
        //noinspection ImplicitNumericConversion
        final Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    private static void drawPiece(final GridPane gridPane,
                                  final Kyokumen kyokumen) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
//                final ImageView view = createImage();
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

    static ImageView createImage() throws IOException, TranscoderException {
        final BufferedImageTranscoder imageTranscoder = new BufferedImageTranscoder();

        final Path path = Paths.get(HOME_TAKAKI_TMP_XBOARD_TEST_SVG);
        final TranscoderInput input = new TranscoderInput(
                Files.newInputStream(path));
        imageTranscoder.transcode(input, null);
        final BufferedImage bufferedImage = imageTranscoder.getBufferedImage();
        final WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
        final ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }

}
