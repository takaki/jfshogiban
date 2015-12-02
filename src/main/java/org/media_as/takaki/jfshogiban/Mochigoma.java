package org.media_as.takaki.jfshogiban;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.media_as.takaki.jfshogiban.Koma.*;

public class Mochigoma {
    private static final EnumSet<Koma> NAMA_GOMA = EnumSet
            .of(SENTE_HISYA, SENTE_KAKU, SENTE_KIN, SENTE_GIN, SENTE_KEIMA,
                    SENTE_KYOSHA, SENTE_FU, GOTE_HISYA, GOTE_KAKU, GOTE_KIN,
                    GOTE_GIN, GOTE_KEIMA, GOTE_KYOSHA, GOTE_FU);
    private final Map<Koma, Integer> komaMap;

    public static Mochigoma initialize() {
        return new Mochigoma();
    }

    private Mochigoma() {
        komaMap = NAMA_GOMA.stream().collect(Collectors.toMap(s -> s, s -> 0));
    }

    private Mochigoma(Map<Koma, Integer> komaMap) {
        this.komaMap = komaMap;
    }

    public Mochigoma put(Koma koma) throws IllegalMoveException {
        if (!NAMA_GOMA.contains(koma)) {
            throw new IllegalMoveException();
        }
        final Map<Koma, Integer> komaMap = new HashMap<>(this.komaMap);
        komaMap.computeIfPresent(koma, (p, n) -> n + 1);
        return new Mochigoma(komaMap);
    }

    public Mochigoma get(Koma koma) throws IllegalMoveException {
        if (!NAMA_GOMA.contains(koma)) {
            throw new IllegalMoveException();
        }
        if (komaMap.get(koma) <= 0) {
            throw new IllegalMoveException();
        }
        final Map<Koma, Integer> komaMap = new HashMap<>(this.komaMap);
        komaMap.computeIfPresent(koma, (p, n) -> n - 1);
        return new Mochigoma(komaMap);
    }

    public int count(Koma koma) {
        return komaMap.getOrDefault(koma, 0);
    }
}
