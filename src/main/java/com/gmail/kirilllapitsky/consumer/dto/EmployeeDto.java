package com.gmail.kirilllapitsky.consumer.dto;

import com.gmail.kirilllapitsky.consumer.enums.States;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employee transfer object.")
public class EmployeeDto {
    @Schema(description = "Employee firstname.")
    private String firstName;
    @Schema(description = "Employee lastname.")
    private String lastName;
    @Schema(description = "Employee age.")
    private int age;
    @Schema(description = "Employee state.")
    private States state;
    @Schema(description = "Employee contract info.")
    private ContractDto contract;
}
