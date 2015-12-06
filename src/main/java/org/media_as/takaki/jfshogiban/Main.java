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

import org.media_as.takaki.jfshogiban.action.IMovement;
import org.media_as.takaki.jfshogiban.protocol.Terminal;

import java.util.stream.Stream;

@SuppressWarnings({"HardCodedStringLiteral", "DuplicateStringLiteralInspection", "UtilityClassCanBeEnum"})
// public class Main extends Application {
public final class Main {

    private final Player currentPlayer;
    private final PlayMove playMove;
    final Terminal inputSente;
    final Terminal inputGote;

    private Main(final PlayMove playMove, final Player currentPlayer,
                 final Terminal inputSente, final Terminal inputGote,
                 final boolean finished) {
        this.currentPlayer = currentPlayer;
        this.playMove = playMove;
        this.inputSente = inputSente;
        this.inputGote = inputGote;
    }

    public Main getNextMain() throws IllegalMoveException {
        final IMovement movement = (currentPlayer == Player.SENTEBAN ? inputSente : inputGote)
                .toMovement(playMove, currentPlayer);
        return new Main(playMove.getNextPlayMove(movement),
                currentPlayer == Player.SENTEBAN ? Player.GOTEBAN : Player.SENTEBAN,
                inputSente, inputGote, false);
    }

    public static void main(final String[] args) throws IllegalMoveException {
        final Main main = new Main(
                new PlayMove(Kyokumen.startPosition(), false), Player.SENTEBAN,
                new Terminal(), new Terminal(), false);
        final Stream<Main> iterate = Stream.iterate(main, main0 -> {
            try {
                return main0.getNextMain();
            } catch (final IllegalMoveException e) {
                throw new RuntimeException(e);
            }
        });
        iterate.anyMatch(main0 -> main0.isFinished());

//        while (true) {
//            main = main.getNextMain();
//        }
    }


    public boolean isFinished() {
        return playMove.isFinished();
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
