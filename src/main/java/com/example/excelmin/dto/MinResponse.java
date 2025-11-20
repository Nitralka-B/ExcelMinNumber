package com.example.excelmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Ответ с N-ым минимальным числом")
public class MinResponse {
    @Schema(description = "N-ое минимальное число", example = "15")
    private int MinNumber;

    @Schema(description = "Статус операции", example = "success")
    private String status;

    @Schema(description = "Сообщение", example = "Number found successfully")
    private String message;

    public MinResponse() {}

    public MinResponse(int MinNumber, String status, String message) {
        this.MinNumber = MinNumber;
        this.status = status;
        this.message = message;
    }
}
