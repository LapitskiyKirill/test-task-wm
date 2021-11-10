package com.gmail.kirilllapitsky.producer.controller;

import com.gmail.kirilllapitsky.producer.service.KafkaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/state")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessStateController {
    private final KafkaService kafkaService;

    @Operation(summary = "Set next state to employee with specified id in parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee state successfully updated."),
            @ApiResponse(responseCode = "500", description = "Some problems while processing state.")})
    @PostMapping("/{employeeId}")
    public void processEmployeeState(
            @Parameter(required = true, description = "Id of employee that needs to set next state.")
            @PathVariable Long employeeId) {
        kafkaService.sendMessage(employeeId);
    }
}
