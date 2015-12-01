package org.media_as.takaki.jfshogiban;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ShogiBan {
    // 3四 is 26.

    private static final int HEIGHT = 9;
    private static final int WIDTH = 9;

    private final List<KomaType> board;

    public static ShogiBan initialize() {
        return new ShogiBan();
    }

    private ShogiBan() {
        board = new ArrayList<>(HEIGHT * WIDTH);
        IntStream.range(0, HEIGHT * WIDTH)
                .forEach(i -> board.add(KomaType.SPACE));
    }

    private ShogiBan(List<KomaType> board) {
        this.board = board;
    }

    public KomaType get(int x, int y) throws IllegalMoveException {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            throw new IllegalMoveException();
        }
        return board.get(x + HEIGHT * y);
    }

    public ShogiBan put(int x, int y,
                        KomaType koma) throws IllegalMoveException {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            throw new IllegalMoveException();
        }
        final List<KomaType> board = new ArrayList<>(this.board);
        board.set(x + HEIGHT * y, koma);
        return new ShogiBan(board);
    }

}
