package com.gmail.kirilllapitsky.consumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "New employee transfer object.")
public class NewEmployeeDto {
    @Schema(description = "Employee firstname.")
    private String firstName;
    @Schema(description = "Employee lastname.")
    private String lastName;
    @Schema(description = "Employee age.")
    private int age;
    @Schema(description = "Employee contract info.")
    private NewContractDto contract;
}