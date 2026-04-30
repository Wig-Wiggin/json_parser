
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GeneralParserTest {

    @Test
    public void shouldRejectExtraCharactersAfterTopLevelValue() {
        // rejects extra characters after valid JSON value
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("true extra"));
    }

    @Test
    public void shouldRejectMultipleTopLevelValues() {
        // rejects multiple top-level values
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{} []"));
    }

    @Test
    public void shouldRejectEmptyInput() {
        // rejects empty input
        Assertions.assertEquals("Invalid JSON", JsonParser.parse(""));
    }

    @Test
    public void shouldRejectWhitespaceOnlyInput() {
        // rejects whitespace-only input
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("   \n\t  "));
    }

    @Test
    public void shouldParseNestedJsonWithTabsAndNewlines() {
        // ignores spaces tabs and newlines around nested JSON
        Map<String, Object> map = new HashMap<>();
        map.put("a", Arrays.asList(1, 2));
        Assertions.assertEquals(map, JsonParser.parse(" \n\t { \"a\" : [1, 2] } \t "));
    }
}

