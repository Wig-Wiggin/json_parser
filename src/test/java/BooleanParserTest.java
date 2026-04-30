import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanParserTest {

    @Test
    public void shouldParseBooleanTrue() {
        // parses boolean true
        Assertions.assertEquals(true, JsonParser.parse("true"));
    }

    @Test
    public void shouldParseBooleanFalse() {
        // parses boolean false
        Assertions.assertEquals(false, JsonParser.parse("false"));
    }

    @Test
    public void shouldParseBooleanWithWhitespace() {
        // ignores surrounding whitespace around boolean
        Assertions.assertEquals(true, JsonParser.parse(" true "));
    }

    @Test
    public void shouldRejectIncompleteTrue() {
        // rejects incomplete true token
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("tru"));
    }

    @Test
    public void shouldRejectExtraCharactersAfterBoolean() {
        // rejects extra characters after boolean
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("falseabc"));
    }
}