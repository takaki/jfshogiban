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

package org.media_as.takaki.jfshogiban.protocol;

import org.media_as.takaki.jfshogiban.IllegalMoveException;
import org.media_as.takaki.jfshogiban.PlayMove;
import org.media_as.takaki.jfshogiban.action.IMovement;
import org.media_as.takaki.jfshogiban.action.NormalMove;
import org.media_as.takaki.jfshogiban.action.PromoteMove;

import java.util.Scanner;

public class Terminal {
    public static IMovement toMovement(String input) {
        int fx = Integer.valueOf(input.substring(0, 1));
        int fy = Integer.valueOf(input.substring(1, 2));
        int tx = Integer.valueOf(input.substring(2, 3));
        int ty = Integer.valueOf(input.substring(3, 4));
        return input.length() > 4 ? new PromoteMove(fx, fy, tx,
                ty) : new NormalMove(fx, fy, tx, ty);

    }

    public static void main(String[] args) throws IllegalMoveException {
        PlayMove playMove = PlayMove.startPosition();
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(playMove.toCSA());
            System.out.println("> ");
            String input = scanner.next();
            final IMovement movement = toMovement(input);
            playMove = playMove.getNextKyokumen(movement);
        }
    }
}