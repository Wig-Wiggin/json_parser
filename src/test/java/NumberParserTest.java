import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberParserTest {

    @Test
    public void shouldParseZero() {
        // parses zero
        Assertions.assertEquals(0, JsonParser.parse("0"));
    }

    @Test
    public void shouldParsePositiveInteger() {
        // parses positive integer
        Assertions.assertEquals(1, JsonParser.parse("1"));
    }

    @Test
    public void shouldParseNegativeInteger() {
        // parses negative integer
        Assertions.assertEquals(-1, JsonParser.parse("-1"));
    }

    @Test
    public void shouldParseMultiDigitInteger() {
        // parses multi-digit integer
        Assertions.assertEquals(12345, JsonParser.parse("12345"));
    }

    @Test
    public void shouldParseDecimalNumber() {
        // parses decimal number
        Assertions.assertEquals(1.5, JsonParser.parse("1.5"));
    }

    @Test
    public void shouldParseNegativeDecimalNumber() {
        // parses negative decimal number
        Assertions.assertEquals(-45.6, JsonParser.parse("-45.6"));
    }

    @Test
    public void shouldParseNumberWithWhitespace() {
        // ignores surrounding whitespace around number
        Assertions.assertEquals(12, JsonParser.parse(" 12 "));
    }

    @Test
    public void shouldRejectLeadingZeroNumber() {
        // rejects leading zero in strict JSON
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("01"));
    }

    @Test
    public void shouldRejectIncompleteDecimalNumber() {
        // rejects incomplete decimal number
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("1."));
    }

    @Test
    public void shouldRejectDecimalWithoutLeadingDigit() {
        // rejects decimal without leading digit
        Assertions.assertEquals("Invalid JSON", JsonParser.parse(".5"));
    }

    @Test
    public void shouldRejectExtraCharactersAfterNumber() {
        // rejects extra characters after number
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("1abc"));
    }

    @Test
    public void shouldRejectDoubleMinusNumber() {
        // rejects malformed negative number
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("--1"));
    }

    @Test
    public void shouldRejectStandaloneMinusSign() {
        // rejects standalone minus sign
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("-"));
    }
}