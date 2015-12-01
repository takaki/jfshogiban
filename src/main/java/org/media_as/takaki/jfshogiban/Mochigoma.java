package org.media_as.takaki.jfshogiban;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.media_as.takaki.jfshogiban.KomaType.*;

public class Mochigoma {
    private static final EnumSet<KomaType> NAMA_GOMA = EnumSet
            .of(SENTE_HISYA, SENTE_KAKU, SENTE_KIN, SENTE_GIN, SENTE_KEIMA,
                    SENTE_KYOSHA, SENTE_FU, GOTE_HISYA, GOTE_KAKU, GOTE_KIN,
                    GOTE_GIN, GOTE_KEIMA, GOTE_KYOSHA, GOTE_FU);
    private final Map<KomaType, Integer> komaMap;

    public static Mochigoma initialize() {
        return new Mochigoma();
    }

    private Mochigoma() {
        komaMap = NAMA_GOMA.stream().collect(Collectors.toMap(s -> s, s -> 0));
    }

    private Mochigoma(Map<KomaType, Integer> komaMap) {
        this.komaMap = komaMap;
    }

    public Mochigoma put(KomaType koma) throws IllegalMoveException {
        if (!NAMA_GOMA.contains(koma)) {
            throw new IllegalMoveException();
        }
        final Map<KomaType, Integer> komaMap = new HashMap<>(this.komaMap);
        komaMap.computeIfPresent(koma, (p, n) -> n + 1);
        return new Mochigoma(komaMap);
    }

    public Mochigoma get(KomaType koma) throws IllegalMoveException {
        if (!NAMA_GOMA.contains(koma)) {
            throw new IllegalMoveException();
        }
        if (komaMap.get(koma) <= 0) {
            throw new IllegalMoveException();
        }
        final Map<KomaType, Integer> komaMap = new HashMap<>(this.komaMap);
        komaMap.computeIfPresent(koma, (p, n) -> n - 1);
        return new Mochigoma(komaMap);
    }

    public int count(KomaType koma) {
        return komaMap.getOrDefault(koma, 0);
    }
}
