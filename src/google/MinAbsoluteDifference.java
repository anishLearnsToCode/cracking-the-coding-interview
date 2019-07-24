package google;

import java.util.*;
import java.util.stream.Stream;

public class MinAbsoluteDifference {
    private static int solution(Integer[] loads) {
        List<Integer> possibleSums = getAllPossibleSums(loads);
        Collections.sort(possibleSums);
        System.out.println(possibleSums);

        if (possibleSums.size() % 2 == 0) {
            return Math.abs(
                    possibleSums.get(possibleSums.size() / 2) - possibleSums.get((possibleSums.size() + 1) / 2)
            );
        }

        return 0;
    }

    private static List<Integer> getAllPossibleSums(Integer[] array) {
        Set<Integer> possibleSums = new HashSet<>();
        possibleSums.add(array[0]);

        for (int index = 1 ; index < array.length ; index++) {
            Set<Integer> tempSums = new HashSet<>();
            for (int sum : possibleSums) {
                tempSums.add(array[index] + sum);
            }
            possibleSums.addAll(tempSums);
            possibleSums.add(array[index]);
        }

        return new ArrayList<>(possibleSums);
    }

    private static int solution(Integer[] array, int endIndex, Set<Integer> possibleSums) {
        if (endIndex == 0) {
            possibleSums.add(array[0]);
            return Math.abs(array[0]);
        }

        int previousDifference = solution(array, endIndex - 1, possibleSums);
        int requiredDifference = (array[endIndex] - previousDifference + 1) / 2;
        int closestSum = getClosestSum(requiredDifference, possibleSums);
        System.out.println("closest sum: " + closestSum);
        updateAllPossibleSums(possibleSums, array[endIndex]);
        return Math.abs(previousDifference - closestSum);
    }

    private static void updateAllPossibleSums(Set<Integer> possibleSums, int element) {
        Set<Integer> newSums = new HashSet<>();
        for (int sum : possibleSums) {
            newSums.add(element + sum);
        }
        possibleSums.add(element);
        possibleSums.addAll(newSums);
    }

    private static int getClosestSum(int requiredDifference, Set<Integer> possibleSums) {
        if (possibleSums.contains(requiredDifference)) {
            return requiredDifference;
        }

        int leftSideCheck = -1;
        for (int index = requiredDifference - 1 ; index >= 0 ; index--) {
            if (possibleSums.contains(index)) {
                leftSideCheck = index;
                break;
            }
        }

        int rightSideCheck = -1, maximumSum = getMaxSum(possibleSums);
//        System.out.println("max sum: " + maximumSum);
        for (int index = requiredDifference + 1 ; index <= maximumSum ; index++) {
            if (possibleSums.contains(index)) {
                rightSideCheck = index;
                break;
            }
        }

        if (leftSideCheck == -1) {
            return rightSideCheck;
        } else if (rightSideCheck == -1) {
            return leftSideCheck;
        }

        return requiredDifference - leftSideCheck < rightSideCheck - requiredDifference ? leftSideCheck : rightSideCheck;
    }

    private static int getMaxSum(Set<Integer> possibleSums) {
        int result = Integer.MIN_VALUE;
        for (int sum : possibleSums) {
            if (sum > result) {
                result = sum;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer[] loads = getIntegerArray(in.next());

        System.out.print(solution(loads));
    }

    private static Integer[] getIntegerArray(String str) {
        return Stream.of(str.split("\\,"))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
    }
}
