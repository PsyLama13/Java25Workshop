package exercises.e07;

import java.util.*;

public class StreamGatherersExercise {

    public static <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }

    public static List<Double> movingAverage(List<Integer> numbers, int windowSize) {
        List<Double> averages = new ArrayList<>();
        for (int i = 0; i <= numbers.size() - windowSize; i++) {
            double sum = 0;
            for (int j = i; j < i + windowSize; j++) { sum += numbers.get(j); }
            averages.add(sum / windowSize);
        }
        return averages;
    }

    public static List<Integer> cumulativeSum(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        int sum = 0;
        for (int num : numbers) { sum += num; result.add(sum); }
        return result;
    }

    public static <T> List<T> everyNth(List<T> list, int n) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % n == 0) { result.add(list.get(i)); }
        }
        return result;
    }

    public static <T> List<T> distinctConsecutive(List<T> list) {
        List<T> result = new ArrayList<>();
        T last = null;
        for (T item : list) {
            if (!item.equals(last)) { result.add(item); last = item; }
        }
        return result;
    }

}
