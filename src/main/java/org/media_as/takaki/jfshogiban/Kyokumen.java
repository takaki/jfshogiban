package org.media_as.takaki.jfshogiban;

public class Kyokumen {
    private final ShogiBan shogiBan;
    private final Mochigoma mochigoma;

    public static Kyokumen initialize() {
        return new Kyokumen(ShogiBan.initialize(), Mochigoma.initialize());
    }

    private Kyokumen(ShogiBan shogiBan, Mochigoma mochigoma) {
        this.shogiBan = shogiBan;
        this.mochigoma = mochigoma;
    }

    public Kyokumen move(int fx, int fy, int tx,
                         int ty) throws IllegalMoveException {
        Koma from = shogiBan.get(fx, fy);
        if (from == Koma.EMPTY || shogiBan.get(tx, ty) != Koma.EMPTY) {
            throw new IllegalMoveException();
        }
        return new Kyokumen(shogiBan.put(fx, fy, Koma.EMPTY).put(tx, ty, from),
                mochigoma);
    }

    public Kyokumen put(int x, int y, Koma koma) throws IllegalMoveException {
        if (shogiBan.get(x, y) != Koma.EMPTY) {
            throw new IllegalMoveException();
        }
        return new Kyokumen(shogiBan.put(x, y, koma), mochigoma);
    }

    public Koma get(int x, int y) throws IllegalMoveException {
        return shogiBan.get(x, y);
    }
}
