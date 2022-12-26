package com.github.badaccuracyid.cuddlyoctogarbanzo.utils;

import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Patient;

import java.util.List;
import java.util.function.Consumer;

public class Sorter {

    public static final Consumer<List<Patient>> mergeSortPatients = (list) -> mergeSortPatients(list, 0, list.size() - 1);

    private static void mergeSortPatients(List<Patient> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortPatients(list, left, mid);
            mergeSortPatients(list, mid + 1, right);

            mergePatients(list, left, mid, right);
        }
    }

    private static void mergePatients(List<Patient> list, int left, int mid, int right) {
        int leftArrSize = mid - left + 1;
        int rightArrSize = right - mid;

        Patient[] leftArr = new Patient[leftArrSize];
        Patient[] rightArr = new Patient[rightArrSize];

        for (int i = 0; i < leftArrSize; i++) {
            leftArr[i] = list.get(left + i);
        }

        for (int i = 0; i < rightArrSize; i++) {
            rightArr[i] = list.get(mid + 1 + i);
        }

        int leftIndex = 0;
        int rightIndex = 0;
        int currIndex = left;

        while (leftIndex < leftArrSize && rightIndex < rightArrSize) {
            if (leftArr[leftIndex].getName().compareTo(rightArr[rightIndex].getName()) <= 0) {
                list.set(currIndex, leftArr[leftIndex]);
                leftIndex++;
            } else {
                list.set(currIndex, rightArr[rightIndex]);
                rightIndex++;
            }
            currIndex++;
        }

        while (leftIndex < leftArrSize) {
            list.set(currIndex, leftArr[leftIndex]);
            leftIndex++;
            currIndex++;
        }

        while (rightIndex < rightArrSize) {
            list.set(currIndex, rightArr[rightIndex]);
            rightIndex++;
            currIndex++;
        }
    }
}
