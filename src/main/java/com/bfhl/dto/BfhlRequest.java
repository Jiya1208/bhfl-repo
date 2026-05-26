package com.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * REQUEST DTO
 * Input: { "data": ["a", "1", "334", "4", "R", "$"] }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BfhlRequest {

    @NotNull(message = "data field is required and cannot be null")
    @JsonProperty("data")
    private List<String> data;
}
