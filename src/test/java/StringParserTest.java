import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringParserTest {

    @Test
    public void shouldParseSimpleString() {
        // parses normal string
        Assertions.assertEquals("hello", JsonParser.parse("\"hello\""));
    }

    @Test
    public void shouldParseEmptyString() {
        // parses empty string
        Assertions.assertEquals("", JsonParser.parse("\"\""));
    }

    @Test
    public void shouldParseSpaceString() {
        // parses string containing a space
        Assertions.assertEquals(" ", JsonParser.parse("\" \""));
    }

    @Test
    public void shouldParseNumericLookingString() {
        // parses numeric-looking text as string
        Assertions.assertEquals("123", JsonParser.parse("\"123\""));
    }

    @Test
    public void shouldParseNullLookingString() {
        // parses null-looking text as string
        Assertions.assertEquals("null", JsonParser.parse("\"null\""));
    }

    @Test
    public void shouldParseBooleanLookingString() {
        // parses boolean-looking text as string
        Assertions.assertEquals("true", JsonParser.parse("\"true\""));
    }

    @Test
    public void shouldParseStringWithSpaces() {
        // parses string with spaces
        Assertions.assertEquals("hello world", JsonParser.parse("\"hello world\""));
    }

    @Test
    public void shouldParseEscapedQuoteInString() {
        // parses escaped quote inside string
        Assertions.assertEquals("a\"b", JsonParser.parse("\"a\\\"b\""));
    }

    @Test
    public void shouldParseEscapedBackslashInString() {
        // parses escaped backslash inside string
        Assertions.assertEquals("a\\b", JsonParser.parse("\"a\\\\b\""));
    }

    @Test
    public void shouldRejectUnclosedString() {
        // rejects unclosed string
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("\"abc"));
    }

    @Test
    public void shouldRejectSingleQuotedString() {
        // rejects single-quoted string
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("'abc'"));
    }

    @Test
    public void shouldRejectExtraCharactersAfterClosingQuote() {
        // rejects extra characters after closing quote
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("\"a\"b\""));
    }

    @Test
    public void shouldParseStringWithSurroundingWhitespace() {
        // ignores surrounding whitespace around string
        Assertions.assertEquals("abc", JsonParser.parse("  \"abc\"  "));
    }
}