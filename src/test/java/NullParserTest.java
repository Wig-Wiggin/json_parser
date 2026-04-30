import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NullParserTest {

    @Test
    public void shouldParseNull() {
        // parses JSON null
        Assertions.assertNull(JsonParser.parse("null"));
    }

    @Test
    public void shouldParseNullWithWhitespace() {
        // ignores surrounding whitespace around null
        Assertions.assertNull(JsonParser.parse(" null "));
    }

    @Test
    public void shouldRejectIncompleteNull() {
        // rejects incomplete null token
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("nul"));
    }

    @Test
    public void shouldRejectExtraCharactersAfterNull() {
        // rejects extra characters after null
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("nullx"));
    }
}