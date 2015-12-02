package org.media_as.takaki.jfshogiban

import spock.lang.Specification

class ShogiBanTest extends Specification {
    def board = ShogiBan.initialize()

    def "get"() {
        expect:
        //noinspection GroovyAssignabilityCheck
        board.get(x, y) == Koma.EMPTY
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

    def "put and error"() {
        when:
        board.put(0, 9, Koma.GOTE_KAKU)
        then:
        thrown(IllegalMoveException)
    }
    def "put and check"() {
        when:
        def board2 = board.put(1, 1, Koma.GOTE_KAKU)
        then:
        board2.get(1, 1) == Koma.GOTE_KAKU
    }

}
