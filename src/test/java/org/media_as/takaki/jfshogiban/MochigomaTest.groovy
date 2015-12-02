package org.media_as.takaki.jfshogiban

import spock.lang.Specification

class MochigomaTest extends Specification {
    def mochigoma = Mochigoma.initialize()

    def "put and get"() {
        when:
        def mochigoma2 = mochigoma.put(Koma.SENTE_FU)
        then:
        mochigoma2.get(Koma.SENTE_FU)
    }

    def "get throw Exception"() {
        when:
        mochigoma.get(Koma.SENTE_FU)

        then:
        thrown(IllegalMoveException)

        when:
        mochigoma.get(Koma.SENTE_KYOSHA)

        then:
        thrown(IllegalMoveException)
    }

    def "count"() {
        when:
        def mochigoma2 = mochigoma.put(Koma.SENTE_FU)

        then:
        mochigoma2.count(Koma.SENTE_FU) == 1

        when:
        def mochigoma3 = mochigoma.put(Koma.SENTE_FU)
                .put(Koma.SENTE_FU)
                .put(Koma.SENTE_FU)
                .put(Koma.SENTE_KYOSHA)
                .put(Koma.SENTE_KEIMA)
                .get(Koma.SENTE_KYOSHA)

        then:
        mochigoma3.count(Koma.SENTE_FU) == 3
        mochigoma3.count(Koma.SENTE_KYOSHA) == 0
        mochigoma3.count(Koma.SENTE_KEIMA) == 1
    }

}
