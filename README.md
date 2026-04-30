# JSON Parser

A simple JSON parser built from scratch in Java without using any built-in JSON libraries.

## Features

This parser supports:

- Objects
- Arrays
- Strings
- Numbers
- Booleans
- Null
- Nested arrays and objects
- Whitespace handling
- Invalid JSON detection

## Supported JSON Examples

```json
{"name":"John","age":25,"isStudent":false}
```

```json
[1, "text", true, null]
```

```json
{"user":{"name":"Alice","skills":["C","Python"]}}
```

## Project Rules

This project does not use:

- `json` libraries
- `JSON.parse`
- Jackson
- Gson
- any external JSON parser

Everything is parsed manually by reading characters and building Java values.

## Return Types

Parsed JSON values are converted into Java types:

- Object -> `Map<String, Object>`
- Array -> `List<Object>`
- String -> `String`
- Number -> `Integer` or `Double`
- Boolean -> `Boolean`
- Null -> `null`

## How It Works

The parser reads the input string character by character and decides what type of JSON value it is parsing.

Main parsing parts:

- parse value
- parse string
- parse number
- parse array
- parse object

The parser also supports recursion, so nested arrays and nested objects work correctly.

## Error Handling

If the input is not valid JSON, the parser returns:

```text
Invalid JSON
```

Examples of invalid input:

- missing comma
- missing colon
- unclosed string
- unclosed array
- unclosed object
- invalid number
- extra characters after valid JSON

## How to Run

1. Clone or download the project
2. Open it in IntelliJ IDEA or any Java IDE
3. Run the test classes with JUnit
4. Run the parser by calling:

```java
Object result = JsonParser.parse("{\"name\":\"John\"}");
System.out.println(result);
```

## Testing

The project uses JUnit tests.

Tests are separated by type:

- null tests
- boolean tests
- string tests
- number tests
- array tests
- object tests
- general parser tests

## Learning Goals

By building this project, you can understand:

- how parsers work internally
- recursive parsing
- string processing
- nested data structures
- JSON syntax validation

## Notes

This parser is designed for learning purposes and project practice.

It is not a full production JSON parser, but it correctly handles the required project scope.
