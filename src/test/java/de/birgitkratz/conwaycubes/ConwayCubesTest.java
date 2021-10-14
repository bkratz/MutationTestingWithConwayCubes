package de.birgitkratz.conwaycubes;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConwayCubesTest {

    ConwayCubes conwayCubes = new ConwayCubes();

    @Test
    void processPart1() throws IOException {
        assertThat(conwayCubes.processPart1()).isEqualTo(317);
    }

    @Test
    void acceptanceTestPart1() {
        int[][] input = new int[][] {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}};

        assertThat(conwayCubes.processInputPart1(input)).isEqualTo(112);
    }

    @Test
    void countNeighboursMatrix() {
        int[][] layer0 = new int[][] {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}};
        int[][] layer1 = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        int[][] layer_1 = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        int[][] expected = new int[][] {{0, 1, 1, 1, 0}, {0, 1, 1, 2, 1}, {1, 3, 5, 3, 2}, {1, 1, 3, 2, 2}, {1, 2, 3, 2, 1}};

        assertThat(conwayCubes.countNeighboursMatrix(layer0, layer1, layer_1)).isEqualTo(expected);

        layer1 = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        layer0 = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        layer_1 = new int[][] {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}};

        expected = new int[][] {{0, 1, 1, 1, 0}, {0, 1, 2, 2, 1}, {1, 3, 5, 4, 2}, {1, 2, 4, 3, 2}, {1, 2, 3, 2, 1}};

        assertThat(conwayCubes.countNeighboursMatrix(layer0, layer1, layer_1)).isEqualTo(expected);
    }

    @Test
    void surroundWithZeroRowsAndColums() {
        int[][] input = new int[][] {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}};

        int[][] expected = new int[][] {{0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {0, 1, 1, 1, 0}, {0, 0, 0, 0, 0}};

        assertThat(conwayCubes.surroundWithZeroRowsAndColumns(input)).isEqualTo(expected);
    }

    @Test
    void cycle() {
        int[][] layer0 = new int[][] {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}};
        int[][] layer1 = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        int[][] layer_1 = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        int[][] expected = new int[][] {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 1, 0}, {0, 0, 1, 1, 0}, {0, 0, 1, 0, 0}};

        assertThat(conwayCubes.cycle(layer0, layer1, layer_1)).isEqualTo(expected);

        layer0 = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        layer1 = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        layer_1 = new int[][] {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}};

        expected = new int[][] {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 1, 0, 0}};

        assertThat(conwayCubes.cycle(layer0, layer1, layer_1)).isEqualTo(expected);
    }
}