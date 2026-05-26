package com.bfhl.service;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.impl.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BfhlServiceTest {

    private BfhlServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
        ReflectionTestUtils.setField(service, "fullName",   "john_doe");
        ReflectionTestUtils.setField(service, "dob",        "17091999");
        ReflectionTestUtils.setField(service, "email",      "john@xyz.com");
        ReflectionTestUtils.setField(service, "rollNumber", "ABCD123");
    }

    @Test
    @DisplayName("Example A: mixed input")
    void testExampleA() {
        BfhlRequest req = new BfhlRequest(List.of("a", "1", "334", "4", "R", "$"));
        BfhlResponse res = service.processData(req);

        assertThat(res.isSuccess()).isTrue();
        assertThat(res.getUserId()).isEqualTo("john_doe_17091999");
        assertThat(res.getOddNumbers()).containsExactly("1");
        assertThat(res.getEvenNumbers()).containsExactlyInAnyOrder("334", "4");
        assertThat(res.getAlphabets()).containsExactlyInAnyOrder("A", "R");
        assertThat(res.getSpecialCharacters()).containsExactly("$");
        assertThat(res.getSum()).isEqualTo("339");
        assertThat(res.getConcatString()).isEqualTo("Ra");
    }

    @Test
    @DisplayName("Example B: multiple specials")
    void testExampleB() {
        BfhlRequest req = new BfhlRequest(
                List.of("2","a","y","4","&","-","*","5","92","b"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getOddNumbers()).containsExactly("5");
        assertThat(res.getEvenNumbers()).containsExactlyInAnyOrder("2","4","92");
        assertThat(res.getAlphabets()).containsExactlyInAnyOrder("A","Y","B");
        assertThat(res.getSpecialCharacters()).containsExactlyInAnyOrder("&","-","*");
        assertThat(res.getSum()).isEqualTo("103");
        assertThat(res.getConcatString()).isEqualTo("ByA");
    }

    @Test
    @DisplayName("Example C: only alphabets with multi-char strings")
    void testExampleC() {
        BfhlRequest req = new BfhlRequest(List.of("A","ABCD","DOE"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getOddNumbers()).isEmpty();
        assertThat(res.getEvenNumbers()).isEmpty();
        assertThat(res.getAlphabets()).containsExactlyInAnyOrder("A","ABCD","DOE");
        assertThat(res.getSpecialCharacters()).isEmpty();
        assertThat(res.getSum()).isEqualTo("0");
        assertThat(res.getConcatString()).isEqualTo("EoDdCbAa");
    }

    @Test
    @DisplayName("Edge case: empty array")
    void testEmptyArray() {
        BfhlRequest req = new BfhlRequest(List.of());
        BfhlResponse res = service.processData(req);

        assertThat(res.getOddNumbers()).isEmpty();
        assertThat(res.getEvenNumbers()).isEmpty();
        assertThat(res.getSum()).isEqualTo("0");
        assertThat(res.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Edge case: only numbers")
    void testOnlyNumbers() {
        BfhlRequest req = new BfhlRequest(List.of("3","6","11"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getOddNumbers()).containsExactlyInAnyOrder("3","11");
        assertThat(res.getEvenNumbers()).containsExactly("6");
        assertThat(res.getAlphabets()).isEmpty();
        assertThat(res.getSum()).isEqualTo("20");
    }

    @Test
    @DisplayName("user_id must always be lowercase")
    void testUserIdLowercase() {
        ReflectionTestUtils.setField(service, "fullName", "Jane_Doe");
        BfhlResponse res = service.processData(new BfhlRequest(List.of("1")));
        assertThat(res.getUserId()).isEqualTo("jane_doe_17091999");
    }
}
