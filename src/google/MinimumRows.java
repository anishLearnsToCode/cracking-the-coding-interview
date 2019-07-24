package google;

import java.util.*;
import java.util.stream.Stream;

public class MinimumRows {
    static int solution(Integer[] array) {
        Set<Integer> allPositions = new HashSet<Integer>();
        int maximumMinimumHeight = array[0], numberOfMaxlements = 1;
        allPositions.add(array[0]);

        for(int index = 1 ; index < array.length ; index++) {
            if (array[index] > maximumMinimumHeight) {
                allPositions.add(array[index]);
            } else if (array[index] == maximumMinimumHeight) {
                numberOfMaxlements++;
            }
            else {
                int nextSmallest = getNextSmallest(array[index], allPositions, maximumMinimumHeight);
                if (nextSmallest == maximumMinimumHeight) {
                    numberOfMaxlements--;
                }
                allPositions.remove(nextSmallest);
                allPositions.add(array[index]);
            }

            if (numberOfMaxlements == 0) {

            }
        }

        return allPositions.size();
    }

    private static int anish(Integer[] array) {
        List<Integer> rows = new ArrayList<>();
        rows.add(array[0]);

        for (int index = 1 ; index < array.length ; index++) {
            int insertPosition = -1, currentMin = Integer.MAX_VALUE;
            for (int rowIndex = 0 ; rowIndex < rows.size() ; rowIndex++) {
                int top = rows.get(rowIndex);
                if (array[index] < top && top < currentMin) {
                    currentMin = top;
                    insertPosition = rowIndex;
                }
            }

            if (insertPosition == -1) {
                rows.add(array[index]);
            } else {
                rows.set(insertPosition, array[index]);
            }
        }

        return rows.size();
    }

    private static int getNextSmallest(int element, Set<Integer> allPositions, int maximumMinimumHeight) {
        for (int index = element + 1 ; index < maximumMinimumHeight ; index++) {
            if (allPositions.contains(index)) {
                return index;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        // Read from stdin, solve the problem, write answer to stdout.
        Scanner in = new Scanner(System.in);
        Integer[] A = getIntegerArray(in.next());
//        Integer[] A = [1, 2, 3];

        System.out.print(anish(A));
    }

    private static Integer[] getIntegerArray(String str) {
        return Stream.of(str.split("\\,"))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
    }
}