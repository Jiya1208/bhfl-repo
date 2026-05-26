package com.bfhl.controller;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CONTROLLER
 * Only POST /bfhl — NO GET endpoint
 */
@RestController
@RequestMapping("/bfhl")
@CrossOrigin(origins = "*")
public class BfhlController {

    private final BfhlService bfhlService;

    // Constructor injection (best practice)
    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    /**
     * POST /bfhl
     * Request:  { "data": ["a", "1", "334", "4", "R", "$"] }
     * Response: 200 OK with full classified response
     */
    @PostMapping
    public ResponseEntity<BfhlResponse> processData(
            @Valid @RequestBody BfhlRequest request) {

        BfhlResponse response = bfhlService.processData(request);
        return ResponseEntity.ok(response);
    }
}
