package com.bfhl.service.impl;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SERVICE IMPLEMENTATION
 * All business logic lives here
 */
@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${app.user.full-name}")
    private String fullName;

    @Value("${app.user.dob}")
    private String dob;

    @Value("${app.user.email}")
    private String email;

    @Value("${app.user.roll-number}")
    private String rollNumber;

    @Override
    public BfhlResponse processData(BfhlRequest request) {

        List<String> data = request.getData();

        List<String> oddNumbers       = new ArrayList<>();
        List<String> evenNumbers      = new ArrayList<>();
        List<String> alphabets        = new ArrayList<>();
        List<String> specialChars     = new ArrayList<>();
        List<Character> allAlphaChars = new ArrayList<>();
        long numberSum                = 0;

        for (String element : data) {

            if (isNumber(element)) {
                // NUMBER — split into odd/even
                long num = Long.parseLong(element);
                numberSum += num;
                if (num % 2 == 0) {
                    evenNumbers.add(element);  // return as string
                } else {
                    oddNumbers.add(element);   // return as string
                }

            } else if (isAlphabet(element)) {
                // ALPHABET — convert to uppercase
                alphabets.add(element.toUpperCase());
                // collect every character for concat_string
                for (char c : element.toCharArray()) {
                    allAlphaChars.add(c);
                }

            } else {
                // SPECIAL CHARACTER
                specialChars.add(element);
            }
        }

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(buildUserId())
                .email(email)
                .rollNumber(rollNumber)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialChars)
                .sum(String.valueOf(numberSum))
                .concatString(buildConcatString(allAlphaChars))
                .build();
    }

    /**
     * Check if entire string is a valid integer
     */
    private boolean isNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check if ALL characters in string are letters
     */
    private boolean isAlphabet(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

    /**
     * Build user_id → full_name_ddmmyyyy (always lowercase)
     */
    private String buildUserId() {
        return fullName.toLowerCase() + "_" + dob;
    }

    /**
     * Build concat_string:
     * Step 1: Collect all letter characters in order
     * Step 2: Reverse the list
     * Step 3: Alternating caps — index 0=UPPER, 1=lower, 2=UPPER...
     *
     * Example C: ["A","ABCD","DOE"]
     * chars   = [A,A,B,C,D,D,O,E]
     * reverse = [E,O,D,D,C,B,A,A]
     * result  = EoDdCbAa ✓
     */
    private String buildConcatString(List<Character> chars) {
        if (chars.isEmpty()) return "";
        Collections.reverse(chars);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.size(); i++) {
            char c = chars.get(i);
            sb.append(i % 2 == 0
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c));
        }
        return sb.toString();
    }
}
