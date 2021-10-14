package de.birgitkratz.conwaycubes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ConwayCubes {

    private final Path path =
            Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("input.txt")).getFile());

    public int processPart1() throws IOException {
        final List<String> lines = Files.readAllLines(path);

        int[][] input = new int[lines.size()][lines.get(0).length()];

        for (int i=0; i<lines.size(); i++) {
            final char[] chars = lines.get(i).toCharArray();
            for (int j=0; j<chars.length; j++) {
                if (chars[j] == '#') {
                    input[i][j] = 1;
                }
            }
        }
        return processInputPart1(input);
    }

    public int processInputPart1(final int[][] input) {
        final List<int[][]> ints = cycle6Times(input);

        return ints.stream().mapToInt(i -> Arrays.stream(i).flatMapToInt(Arrays::stream).sum()).sum();
    }

    List<int[][]> cycle6Times(int[][] input) {
        int[][] cycleLayer0 = input;
        int[][] cycleLayer1 = new int[cycleLayer0.length][cycleLayer0[0].length];

        int[][] nextCycleLayer0 = cycle(cycleLayer0, cycleLayer1, cycleLayer1);
        int[][] nextCycleLayer1 = cycle(cycleLayer1, new int[cycleLayer0.length][cycleLayer0[0].length], cycleLayer0);

        cycleLayer0 = nextCycleLayer0;
        cycleLayer1 = nextCycleLayer1;
        int[][] cycleLayer2 = new int[cycleLayer0.length][cycleLayer0[0].length];

        nextCycleLayer0 = cycle(cycleLayer0, cycleLayer1, cycleLayer1);
        nextCycleLayer1 = cycle(cycleLayer1, cycleLayer2, cycleLayer0);
        int[][] nextCycleLayer2 = cycle(cycleLayer2, new int[cycleLayer0.length][cycleLayer0[0].length], cycleLayer1);

        cycleLayer0 = nextCycleLayer0;
        cycleLayer1 = nextCycleLayer1;
        cycleLayer2 = nextCycleLayer2;
        int[][] cycleLayer3 = new int[cycleLayer0.length][cycleLayer0[0].length];

        nextCycleLayer0 = cycle(cycleLayer0, cycleLayer1, cycleLayer1);
        nextCycleLayer1 = cycle(cycleLayer1, cycleLayer2, cycleLayer0);
        nextCycleLayer2 = cycle(cycleLayer2, cycleLayer3, cycleLayer1);
        int[][] nextCycleLayer3 = cycle(cycleLayer3, new int[cycleLayer0.length][cycleLayer0[0].length], cycleLayer2);

        cycleLayer0 = nextCycleLayer0;
        cycleLayer1 = nextCycleLayer1;
        cycleLayer2 = nextCycleLayer2;
        cycleLayer3 = nextCycleLayer3;
        int[][] cycleLayer4 = new int[cycleLayer0.length][cycleLayer0[0].length];

        nextCycleLayer0 = cycle(cycleLayer0, cycleLayer1, cycleLayer1);
        nextCycleLayer1 = cycle(cycleLayer1, cycleLayer2, cycleLayer0);
        nextCycleLayer2 = cycle(cycleLayer2, cycleLayer3, cycleLayer1);
        nextCycleLayer3 = cycle(cycleLayer3, cycleLayer4, cycleLayer2);
        int[][] nextCycleLayer4 = cycle(cycleLayer4, new int[cycleLayer0.length][cycleLayer0[0].length], cycleLayer3);

        cycleLayer0 = nextCycleLayer0;
        cycleLayer1 = nextCycleLayer1;
        cycleLayer2 = nextCycleLayer2;
        cycleLayer3 = nextCycleLayer3;
        cycleLayer4 = nextCycleLayer4;
        int[][] cycleLayer5 = new int[cycleLayer0.length][cycleLayer0[0].length];

        nextCycleLayer0 = cycle(cycleLayer0, cycleLayer1, cycleLayer1);
        nextCycleLayer1 = cycle(cycleLayer1, cycleLayer2, cycleLayer0);
        nextCycleLayer2 = cycle(cycleLayer2, cycleLayer3, cycleLayer1);
        nextCycleLayer3 = cycle(cycleLayer3, cycleLayer4, cycleLayer2);
        nextCycleLayer4 = cycle(cycleLayer4, cycleLayer5, cycleLayer3);
        int[][] nextCycleLayer5 = cycle(cycleLayer5, new int[cycleLayer0.length][cycleLayer0[0].length], cycleLayer4);

        cycleLayer0 = nextCycleLayer0;
        cycleLayer1 = nextCycleLayer1;
        cycleLayer2 = nextCycleLayer2;
        cycleLayer3 = nextCycleLayer3;
        cycleLayer4 = nextCycleLayer4;
        cycleLayer5 = nextCycleLayer5;
        int[][] cycleLayer6 = new int[cycleLayer0.length][cycleLayer0[0].length];

        nextCycleLayer0 = cycle(cycleLayer0, cycleLayer1, cycleLayer1);
        nextCycleLayer1 = cycle(cycleLayer1, cycleLayer2, cycleLayer0);
        nextCycleLayer2 = cycle(cycleLayer2, cycleLayer3, cycleLayer1);
        nextCycleLayer3 = cycle(cycleLayer3, cycleLayer4, cycleLayer2);
        nextCycleLayer4 = cycle(cycleLayer4, cycleLayer5, cycleLayer3);
        nextCycleLayer5 = cycle(cycleLayer5, cycleLayer6, cycleLayer4);
        int[][] nextCycleLayer6 = cycle(cycleLayer6, new int[cycleLayer0.length][cycleLayer0[0].length], cycleLayer5);

        return List.of(nextCycleLayer0, nextCycleLayer1, nextCycleLayer2, nextCycleLayer3, nextCycleLayer4, nextCycleLayer5,
                nextCycleLayer6, nextCycleLayer6, nextCycleLayer5, nextCycleLayer4, nextCycleLayer3, nextCycleLayer2,
                nextCycleLayer1);
    }

    int[][] surroundWithZeroRowsAndColumns(final int[][] input) {
        int[][] result = new int[input.length + 2][input[0].length + 2];
        IntStream.range(0, result[0].length).forEach(i -> result[0][i] = 0);
        for (int row = 0; row < input.length; row++) {
            result[row + 1][0] = 0;
            System.arraycopy(input[row], 0, result[row + 1], 1, input[0].length);
            result[row + 1][result[0].length - 1] = 0;
        }

        return result;
    }

    int[][] cycle(final int[][] layerZero, final int[][] layerPlus1, final int[][] layerMinus1) {
        final int[][] activeNeighbourCounts = countNeighboursMatrix(layerZero, layerPlus1, layerMinus1);
        final int[][] surroundLayerZero = surroundWithZeroRowsAndColumns(layerZero);

        int[][] result = new int[activeNeighbourCounts.length][activeNeighbourCounts[0].length];
        for (int row = 0; row < activeNeighbourCounts.length; row++) {
            for (int column = 0; column < activeNeighbourCounts[0].length; column++) {
                switch (activeNeighbourCounts[row][column]) {
                case 2:
                    if (surroundLayerZero[row][column] == 1) {
                        result[row][column] = 1;
                    }
                    break;
                case 3:
                    result[row][column] = 1;
                    break;
                default:
                    result[row][column] = 0;
                }
            }
        }
        return result;
    }

    public int[][] countNeighboursMatrix(final int[][] layerZero, final int[][] layerPlus1, final int[][] layerMinus1) {
        final int[][] surroundLayerZero = surroundWithZeroRowsAndColumns(layerZero);
        final int[][] surroundLayerPlus1 = surroundWithZeroRowsAndColumns(layerPlus1);
        final int[][] surroundLayerMinus1 = surroundWithZeroRowsAndColumns(layerMinus1);

        final int[][] activeNeighboursLayerZero = calculateActiveNeighbours(surroundLayerZero);
        final int[][] activeNeighboursLayerPlus1 = calculateActiveNeighbours(surroundLayerPlus1);
        final int[][] activeNeighboursLayerMinus1 = calculateActiveNeighbours(surroundLayerMinus1);

        int[][] result = new int[surroundLayerZero.length][surroundLayerZero[0].length];

        for (int row = 0; row < surroundLayerZero.length; row++) {
            for (int column = 0; column < surroundLayerZero[0].length; column++) {
                result[row][column] = activeNeighboursLayerZero[row][column] + activeNeighboursLayerPlus1[row][column] + activeNeighboursLayerMinus1[row][column];
                if (surroundLayerPlus1[row][column] == 1) {
                    result[row][column] += 1;
                }
                if (surroundLayerMinus1[row][column] == 1) {
                    result[row][column] += 1;
                }
            }
        }
        return result;
    }

    private int[][] calculateActiveNeighbours(final int[][] input) {
        int[][] activeNeighbours = new int[input.length][input[0].length];

        final int[] flatInput = Arrays.stream(input).flatMapToInt(Arrays::stream).toArray();

        IntStream.range(0, flatInput.length).forEach(index -> {
            final List<Integer> neighbourIndices = findNeighbourIndices(index, input.length, input[0].length);
            final int count = (int) neighbourIndices.stream().filter(i -> flatInput[i] == 1).count();

            int row = index / input[0].length;
            int col = index - (row * input[0].length);
            activeNeighbours[row][col] = count;
        });

        return activeNeighbours;
    }

    private List<Integer> findNeighbourIndices(final int index, final int rows, final int cols) {
        List<Integer> neighbourIndices = new ArrayList<>();

        int row = index / cols;
        int col = index - (row * cols);

        if ((row - 1) >= 0) {
            if ((col - 1) >= 0) {
                neighbourIndices.add(index - (cols + 1));
            }

            neighbourIndices.add(index - cols);

            if ((col + 1) < cols) {
                neighbourIndices.add(index - (cols - 1));
            }
        }

        if ((col - 1) >= 0) {
            neighbourIndices.add(index - 1);
        }
        if ((col + 1) < cols) {
            neighbourIndices.add(index + 1);
        }

        if ((row + 1) < rows) {
            if ((col - 1) >= 0) {
                neighbourIndices.add(index + (cols - 1));
            }

            neighbourIndices.add(index + cols);

            if ((col + 1) < cols) {
                neighbourIndices.add(index + (cols + 1));
            }
        }

        return neighbourIndices;
    }
}
