package org.media_as.takaki.jfshogiban

import spock.lang.Specification

class ShogiBanTest extends Specification {
    def board = ShogiBan.initialize()

    def "get"() {
        expect:
        //noinspection GroovyAssignabilityCheck
        board.get(x, y) == KomaType.SPACE
        where:
        [x, y] << [0..8, 0..8].combinations()
    }

    def "get throw error"() {
        when:
        board.get(-1, 0)
        then:
        thrown(IllegalMoveException)
        when:
        board.get(2, 9)
        then:
        thrown(IllegalMoveException)
    }

    def "put and check"() {
        when:
        def board2 = board.put(1, 1, KomaType.GOTE_KAKU)
        then:
        board2.get(1, 1) == KomaType.GOTE_KAKU
    }

}
