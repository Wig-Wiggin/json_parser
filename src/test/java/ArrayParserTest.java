import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayParserTest {

    @Test
    public void shouldParseEmptyArray() {
        // parses empty array
        Assertions.assertEquals(List.of(), JsonParser.parse("[]"));
    }

    @Test
    public void shouldParseSingleNumberArray() {
        // parses array with one number
        Assertions.assertEquals(List.of(1), JsonParser.parse("[1]"));
    }

    @Test
    public void shouldParseSingleStringArray() {
        // parses array with one string
        Assertions.assertEquals(List.of("a"), JsonParser.parse("[\"a\"]"));
    }

    @Test
    public void shouldParsePrimitiveArray() {
        // parses array with primitive values
        Assertions.assertEquals(Arrays.asList(true, false, null), JsonParser.parse("[true,false,null]"));
    }

    @Test
    public void shouldParseMixedArray() {
        // parses array with mixed value types
        Assertions.assertEquals(Arrays.asList(1, "a", true, null), JsonParser.parse("[1,\"a\",true,null]"));
    }

    @Test
    public void shouldParseArrayWithWhitespace() {
        // ignores whitespace inside array
        Assertions.assertEquals(Arrays.asList(1, 2, 3), JsonParser.parse("[ 1 , 2 , 3 ]"));
    }

    @Test
    public void shouldParseNestedArrays() {
        // parses nested arrays
        Assertions.assertEquals(Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4)), JsonParser.parse("[[1,2],[3,4]]"));
    }

    @Test
    public void shouldParseArrayContainingArray() {
        // parses array containing another array
        Assertions.assertEquals(Arrays.asList(1, Arrays.asList(2, 3), 4), JsonParser.parse("[1,[2,3],4]"));
    }

    @Test
    public void shouldParseArrayContainingObject() {
        // parses array containing object
        Map<String, Object> object = new HashMap<>();
        object.put("a", 1);
        Assertions.assertEquals(Arrays.asList(object, 2), JsonParser.parse("[{\"a\":1},2]"));
    }

    @Test
    public void shouldParseArrayContainingEmptyStructures() {
        // parses array containing empty array and empty object
        Map<String, Object> emptyObject = new HashMap<>();
        Assertions.assertEquals(Arrays.asList(List.of(), emptyObject), JsonParser.parse("[[],{}]"));
    }

    @Test
    public void shouldRejectUnclosedArray() {
        // rejects unclosed array
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("["));
    }

    @Test
    public void shouldRejectArrayMissingClosingBracket() {
        // rejects missing closing bracket
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("[1,2,3"));
    }

    @Test
    public void shouldRejectArrayMissingCommaBetweenElements() {
        // rejects missing comma between elements
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("[1 2]"));
    }

    @Test
    public void shouldRejectArrayLeadingComma() {
        // rejects leading comma
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("[,1]"));
    }

    @Test
    public void shouldRejectArrayTrailingComma() {
        // rejects trailing comma
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("[1,]"));
    }

    @Test
    public void shouldRejectArrayDoubleComma() {
        // rejects double comma
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("[1,,2]"));
    }

    @Test
    public void shouldRejectInvalidTokenInsideArray() {
        // rejects invalid token inside array
        Assertions.assertEquals("Invalid JSON", JsonParser.parse("[1,abc]"));
    }
}