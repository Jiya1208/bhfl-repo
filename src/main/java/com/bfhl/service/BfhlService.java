package com.bfhl.service;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;

/**
 * SERVICE INTERFACE
 * Mandatory as per spec — keeps controller decoupled from logic
 */
public interface BfhlService {
    BfhlResponse processData(BfhlRequest request);
}
