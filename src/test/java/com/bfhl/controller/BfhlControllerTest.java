package com.bfhl.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Example A — 200 OK, sum=339, concat=Ra")
    void testExampleA() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"data": ["a","1","334","4","R","$"]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.sum").value("339"))
                .andExpect(jsonPath("$.concat_string").value("Ra"))
                .andExpect(jsonPath("$.odd_numbers[0]").value("1"))
                .andExpect(jsonPath("$.special_characters[0]").value("$"));
    }

    @Test
    @DisplayName("Example B — sum=103, concat=ByA")
    void testExampleB() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"data": ["2","a","y","4","&","-","*","5","92","b"]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.sum").value("103"))
                .andExpect(jsonPath("$.concat_string").value("ByA"));
    }

    @Test
    @DisplayName("Example C — sum=0, concat=EoDdCbAa")
    void testExampleC() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"data": ["A","ABCD","DOE"]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.sum").value("0"))
                .andExpect(jsonPath("$.concat_string").value("EoDdCbAa"));
    }

    @Test
    @DisplayName("Missing data field — 400 Bad Request")
    void testMissingData() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    @Test
    @DisplayName("Malformed JSON — 400 Bad Request")
    void testMalformedJson() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("not valid json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    @Test
    @DisplayName("All 10 required fields present in response")
    void testAllFieldsPresent() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"data": ["z"]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").exists())
                .andExpect(jsonPath("$.user_id").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.roll_number").exists())
                .andExpect(jsonPath("$.odd_numbers").exists())
                .andExpect(jsonPath("$.even_numbers").exists())
                .andExpect(jsonPath("$.alphabets").exists())
                .andExpect(jsonPath("$.special_characters").exists())
                .andExpect(jsonPath("$.sum").exists())
                .andExpect(jsonPath("$.concat_string").exists());
    }
}
