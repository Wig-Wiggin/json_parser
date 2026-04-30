import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectParserTest {

    @Test
    public void shouldParseEmptyObject() {
        // parses empty object
        Assertions.assertEquals(new HashMap<String, Object>(), JsonParser.parse("{}"));
    }

    @Test
    public void shouldParseSinglePairObject() {
        // parses object with one key-value pair
        Map<String, Object> map = new HashMap<>();
        map.put("name", "John");
        Assertions.assertEquals(map, JsonParser.parse("{\"name\":\"John\"}"));
    }

    @Test
    public void shouldParseMultiPairObject() {
        // parses object with multiple key-value pairs
        Map<String, Object> map = new HashMap<>();
        map.put("name", "John");
        map.put("age", 24);
        Assertions.assertEquals(map, JsonParser.parse("{\"name\":\"John\",\"age\":24}"));
    }

    @Test
    public void shouldParseMixedValueObject() {
        // parses object with mixed value types
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", true);
        map.put("c", null);
        Assertions.assertEquals(map, JsonParser.parse("{\"a\":1,\"b\":true,\"c\":null}"));
    }

    @Test
    public void shouldParseObjectWithWhitespace() {
        // ignores whitespace inside object
        Map<String, Object> map = new HashMap<>();
        map.put("name", "John");
        Assertions.assertEquals(map, JsonParser.parse("{ \"name\" : \"John\" }"));
    }

    @Test
    public void shouldParseObjectWithEmptyKey() {
        // parses object with empty string key
        Map<String, Object> map = new HashMap<>();
        map.put("", 123);
        Assertions.assertEquals(map, JsonParser.parse("{\"\":123}"));
    }

    @Test
    public void shouldParseNestedObject() {
        // parses nested object
        Map<String, Object> inner = new HashMap<>();
        inner.put("name", "Alice");

        Map<String, Object> outer = new HashMap<>();
        outer.put("user", inner);

        Assertions.assertEquals(outer, JsonParser.parse("{\"user\":{\"name\":\"Alice\"}}"));
    }

    @Test
    public void shouldParseObjectContainingArray() {
        // parses object containing array
        Map<String, Object> map = new HashMap<>();
        map.put("skills", Arrays.asList("C", "Java"));
        Assertions.assertEquals(map, JsonParser.parse("{\"skills\":[\"C\",\"Java\"]}"));
    }

    @Test
    public void shouldParseDeeplyNestedMixedStructures() {
        // parses deeply nested mixed structures
        Map<String, Object> innerObject = new HashMap<>();
        innerObject.put("b", 2);

        List<Object> nestedArray = Arrays.asList(1, innerObject, Arrays.asList(3));

        Map<String, Object> outer = new HashMap<>();
        outer.put("a", nestedArray);

        Assertions.assertEquals(outer, JsonParser.parse("{\"a\":[1,{\"b\":2},[3]]}"));
    }

    @Test
    public void shouldParseObjectContainingEmptyStructures() {
        // parses object containing empty array and empty object
        Map<String, Object> map = new HashMap<>();
        map.put("emptyArray", List.of());
        map.put("emptyObject", new HashMap<String, Object>());
        Assertions.assertEquals(map, JsonParser.parse("{\"emptyArray\":[],\"emptyObject\":{}}"));
    }

    @Test
    public void shouldRejectUnclosedObject() {
        // rejects unclosed object
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{"));
    }

    @Test
    public void shouldRejectObjectMissingClosingBrace() {
        // rejects missing closing brace
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{\"a\":1"));
    }

    @Test
    public void shouldRejectObjectMissingColon() {
        // rejects missing colon after key
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{\"a\" 1}"));
    }

    @Test
    public void shouldRejectObjectMissingValue() {
        // rejects missing value after colon
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{\"a\":}"));
    }

    @Test
    public void shouldRejectUnquotedObjectKey() {
        // rejects unquoted object key
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{a:1}"));
    }

    @Test
    public void shouldRejectTrailingCommaInObject() {
        // rejects trailing comma in object
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{\"a\":1,}"));
    }

    @Test
    public void shouldRejectLeadingCommaInObject() {
        // rejects leading comma in object
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{,\"a\":1}"));
    }

    @Test
    public void shouldRejectMissingCommaBetweenPairs() {
        // rejects missing comma between pairs
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{\"a\":1 \"b\":2}"));
    }

    @Test
    public void shouldRejectCommaInsteadOfColon() {
        // rejects comma used instead of colon
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("{\"a\",1}"));
    }
}