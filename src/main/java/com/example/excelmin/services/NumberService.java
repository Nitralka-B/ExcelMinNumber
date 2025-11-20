package com.example.excelmin.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

@Service
public class NumberService {

    /**
     * Находит N-ное минимальное число в Excel файле
     * Использует алгоритм с max-heap для эффективного поиска
     */
    public int findMinNumber(String filePath, int n) throws IOException {
        if (n <= 0) {
            throw new IllegalArgumentException("N должно быть положительным числом");
        }

        int[] numbers = readNumbersFromExcel(filePath);

        if (n > numbers.length) {
            throw new IllegalArgumentException("N не может быть больше количества чисел в файле");
        }

        return findMin(numbers, n);
    }

    /**
     * Эффективный алгоритм поиска N-ного минимального числа
     * Использует max-heap (PriorityQueue) размера N
     * Временная сложность: O(M log N), где M - количество чисел
     */
    private int findMin(int[] numbers, int n) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

        for (int num : numbers) {
            if (maxHeap.size() < n) {
                maxHeap.offer(num);
            } else if (num < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(num);
            }
        }

        return maxHeap.peek();
    }

    /**
     * Чтение чисел из Excel файла (первый столбец)
     */
    private int[] readNumbersFromExcel(String filePath) throws IOException {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int[] numbers = new int[rowCount];
            int validNumberCount = 0;

            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                        numbers[validNumberCount++] = (int) cell.getNumericCellValue();
                    }
                }
            }

            if (validNumberCount < rowCount) {
                int[] result = new int[validNumberCount];
                System.arraycopy(numbers, 0, result, 0, validNumberCount);
                return result;
            }

            return numbers;
        }
    }
}
