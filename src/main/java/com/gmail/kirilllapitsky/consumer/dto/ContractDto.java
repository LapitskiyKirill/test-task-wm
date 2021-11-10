package com.gmail.kirilllapitsky.consumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employee contract transfer object.")
public class ContractDto {
    @Schema(description = "Contract start date.")
    private LocalDate startDate;
    @Schema(description = "Contract start date.")
    private LocalDate endDate;
}
