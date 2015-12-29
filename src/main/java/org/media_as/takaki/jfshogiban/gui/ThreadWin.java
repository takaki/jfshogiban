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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadWin extends Application {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadWin.class);

    private final StopWatch stopWatch = new StopWatch();

    public static void main(final String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stopWatch.start();
        final StackPane root = new StackPane();
        final Scene scene = new Scene(root, 500, 500);
        Button button = new Button();
        button.setText("Hello Button");
        GridPane grid = new GridPane();
        root.getChildren().add(grid);
        grid.add(button, 0, 0);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
        LOG.debug("done");
        // updateButton(button);
        final Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //while (true) {
                            try {
                                Thread.sleep(400);
                                Platform.runLater(() -> updateButton(button));
                                //Platform.runLater(() -> replaceButton(grid));
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        //}
                        return null;
                    }
                };
            }
        };

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                service.start();
            }
        });

    }

    private void replaceButton(GridPane grid) {
        Button button = new Button();
        button.setText("hoge: " + stopWatch);
        LOG.debug("add");
        grid.add(button,0,0);
    }

    private void updateButton(Button button) {
        LOG.debug("run {}", stopWatch);
        button.setText(stopWatch.toString());
    }
}
