package com.example.excelmin.controller;

import com.example.excelmin.dto.MinResponse;
import com.example.excelmin.services.NumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/numbers")
@Tag(name = "Number Controller", description = "Контроллер для поиска N-ого минимального числа в Excel файлах")
public class ExcelNumberController {
    @Autowired
    private NumberService numberService;

    @GetMapping("/exc-min")
    @Operation(
            summary = "Найти N-ое минимальное число",
            description = "Возвращает N-ое минимальное число из указанного Excel файла"
    )
    public MinResponse findMinNumber(
            @Parameter(description = "Путь к Excel файлу (.xlsx)", example = "C:/data/numbers.xlsx")
            @RequestParam String filePath,

            @Parameter(description = "Позиция минимального числа (N)", example = "3")
            @RequestParam int n) {

        try {
            int result = numberService.findMinNumber(filePath, n);
            return new MinResponse(result, "success",
                    String.format("Найдено %d-ое минимальное число: %d", n, result));

        } catch (Exception e) {
            return new MinResponse(0, "error",
                    String.format("Ошибка: %s", e.getMessage()));
        }
    }
}
