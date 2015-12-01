package org.media_as.takaki.jfshogiban

import spock.lang.Specification

class MochigomaTest extends Specification {
    def mochigoma = Mochigoma.initialize()

    def "put and get"() {
        when:
        def mochigoma2 = mochigoma.put(KomaType.SENTE_FU)
        then:
        mochigoma2.get(KomaType.SENTE_FU)
    }

    def "get throw Exception"() {
        when:
        mochigoma.get(KomaType.SENTE_FU)

        then:
        thrown(IllegalMoveException)

        when:
        mochigoma.get(KomaType.SENTE_KYOSHA)

        then:
        thrown(IllegalMoveException)
    }

    def "count"() {
        when:
        def mochigoma2 = mochigoma.put(KomaType.SENTE_FU)

        then:
        mochigoma2.count(KomaType.SENTE_FU) == 1

        when:
        def mochigoma3 = mochigoma.put(KomaType.SENTE_FU)
                .put(KomaType.SENTE_FU)
                .put(KomaType.SENTE_FU)
                .put(KomaType.SENTE_KYOSHA)
                .put(KomaType.SENTE_KEIMA)
                .get(KomaType.SENTE_KYOSHA)

        then:
        mochigoma3.count(KomaType.SENTE_FU) == 3
        mochigoma3.count(KomaType.SENTE_KYOSHA) == 0
        mochigoma3.count(KomaType.SENTE_KEIMA) == 1
    }

}
