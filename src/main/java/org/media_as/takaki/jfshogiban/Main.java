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

import com.codepoetics.protonpack.StreamUtils;
import org.media_as.takaki.jfshogiban.protocol.usi.UsiChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"HardCodedStringLiteral", "DuplicateStringLiteralInspection", "UtilityClassCanBeEnum"})
// public class Main extends Application {
public final class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private Main() {
    }

    public static void main(final String[] args) throws IOException {
        LOG.debug(Arrays.toString(args));
        LOG.debug("run Main");

        final Stream<PlayMain> iterate = Stream.iterate(
                new PlayMain(Kyokumen.startPosition(), false, new UsiChannel(
                        Paths.get("/home/takaki/tmp/gpsfish/src"), "gpsfish"),
                        new UsiChannel(Paths.get("/home/takaki/tmp/apery/bin"),
                                "apery")), playMain -> {
                    try {
                        return playMain.getNextPlayMain();
                    } catch (final IllegalMoveException e) {
                        LOG.debug("*** Raise Exception ***");
                        throw new RuntimeException(e);
                    }
                });
        final List<PlayMain> collect = StreamUtils
                .takeUntil(iterate, PlayMain::isFinished)
                .collect(Collectors.toList());

        final int moves = collect.size() - 1;
        LOG.debug("Game End: {} WON {} Moves",
                (moves & 1) == 1 ? Player.SENTEBAN : Player.GOTEBAN, moves);
        System.exit(0); // FIXME

    }


//    public static void main(final String[] args) {
//        launch(args);
//    }
//
//    @SuppressWarnings({"ProhibitedExceptionDeclared", "UseOfSystemOutOrSystemErr", "DesignForExtension"})
//    @Override
//    public void start(final Stage stage) throws Exception {
//        final Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(event -> System.out.println("Hello World!"));
//
//        final StackPane root = new StackPane();
//        root.getChildren().add(btn);
//
//        //noinspection ImplicitNumericConversion
//        final Scene scene = new Scene(root, 300, 250);
//
//        stage.setTitle("Hello World!");
//        stage.setScene(scene);
//        stage.show();
//    }
}
