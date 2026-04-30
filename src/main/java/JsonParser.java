import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {

    public static Object parse(String json) {
        try {
            if (json == null) {
                throw new StringParseException();
            }

            Parser parser = new Parser(json);
            Object value = parser.parseValue();
            parser.skipWhitespace();

            if (!parser.isAtEnd()) {
                throw new StringParseException();
            }

            return value;
        } catch (RuntimeException exception) {
            return "Invalid JSON";
        }
    }

    private static final class Parser {
        private final String json;
        private int index;

        private Parser(String json) {
            this.json = json;
            this.index = 0;
        }

        private Object parseValue() {
            skipWhitespace();

            if (isAtEnd()) {
                throw new StringParseException();
            }

            char current = currentChar();

            if (current == '"') {
                return parseString();
            }
            if (current == '{') {
                return parseObject();
            }
            if (current == '[') {
                return parseArray();
            }
            if (current == 't' || current == 'f') {
                return parseBoolean();
            }
            if (current == 'n') {
                return parseNull();
            }
            if (current == '-' || Character.isDigit(current)) {
                return parseNumber();
            }

            throw new StringParseException();
        }

        private String parseString() {
            if (currentChar() != '"') {
                throw new StringParseException();
            }

            index++; // skip opening quote
            StringBuilder builder = new StringBuilder();

            while (!isAtEnd()) {
                char current = currentChar();

                if (current == '\\') {
                    index++;

                    if (isAtEnd()) {
                        throw new StringParseException();
                    }

                    char escaped = currentChar();

                    if (escaped == '"' || escaped == '\\') {
                        builder.append(escaped);
                        index++;
                        continue;
                    }

                    throw new StringParseException();
                }

                if (current == '"') {
                    index++; // skip closing quote
                    return builder.toString();
                }

                builder.append(current);
                index++;
            }

            throw new StringParseException();
        }

        private Object parseNumber() {
            int start = index;

            if (currentChar() == '-') {
                index++;
                if (isAtEnd()) {
                    throw new StringParseException();
                }
            }

            if (currentChar() == '0') {
                index++;
                if (!isAtEnd() && Character.isDigit(currentChar())) {
                    throw new StringParseException();
                }
            } else if (Character.isDigit(currentChar())) {
                while (!isAtEnd() && Character.isDigit(currentChar())) {
                    index++;
                }
            } else {
                throw new StringParseException();
            }

            boolean isDecimal = false;

            if (!isAtEnd() && currentChar() == '.') {
                isDecimal = true;
                index++;

                if (isAtEnd() || !Character.isDigit(currentChar())) {
                    throw new StringParseException();
                }

                while (!isAtEnd() && Character.isDigit(currentChar())) {
                    index++;
                }
            }

            String token = json.substring(start, index);

            try {
                if (isDecimal) {
                    return Double.parseDouble(token);
                }
                return Integer.parseInt(token);
            } catch (NumberFormatException exception) {
                throw new StringParseException();
            }
        }

        private Boolean parseBoolean() {
            if (json.startsWith("true", index)) {
                index += 4;
                return true;
            }

            if (json.startsWith("false", index)) {
                index += 5;
                return false;
            }

            throw new StringParseException();
        }

        private Object parseNull() {
            if (json.startsWith("null", index)) {
                index += 4;
                return null;
            }

            throw new StringParseException();
        }

        private List<Object> parseArray() {
            if (currentChar() != '[') {
                throw new StringParseException();
            }

            index++; // skip [
            skipWhitespace();

            List<Object> result = new ArrayList<>();

            if (!isAtEnd() && currentChar() == ']') {
                index++; // skip ]
                return result;
            }

            while (true) {
                result.add(parseValue());
                skipWhitespace();

                if (isAtEnd()) {
                    throw new StringParseException();
                }

                char current = currentChar();

                if (current == ',') {
                    index++; // skip comma
                    skipWhitespace();

                    if (isAtEnd() || currentChar() == ']') {
                        throw new StringParseException();
                    }

                    continue;
                }

                if (current == ']') {
                    index++; // skip ]
                    return result;
                }

                throw new StringParseException();
            }
        }

        private Map<String, Object> parseObject() {
            if (currentChar() != '{') {
                throw new StringParseException();
            }

            index++; // skip {
            skipWhitespace();

            Map<String, Object> result = new HashMap<>();

            if (!isAtEnd() && currentChar() == '}') {
                index++; // skip }
                return result;
            }

            while (true) {
                skipWhitespace();

                if (isAtEnd() || currentChar() != '"') {
                    throw new StringParseException();
                }

                String key = parseString();
                skipWhitespace();

                if (isAtEnd() || currentChar() != ':') {
                    throw new StringParseException();
                }

                index++; // skip :
                Object value = parseValue();
                result.put(key, value);

                skipWhitespace();

                if (isAtEnd()) {
                    throw new StringParseException();
                }

                char current = currentChar();

                if (current == ',') {
                    index++; // skip comma
                    skipWhitespace();

                    if (isAtEnd() || currentChar() == '}') {
                        throw new StringParseException();
                    }

                    continue;
                }

                if (current == '}') {
                    index++; // skip }
                    return result;
                }

                throw new StringParseException();
            }
        }

        private void skipWhitespace() {
            while (!isAtEnd() && Character.isWhitespace(currentChar())) {
                index++;
            }
        }

        private boolean isAtEnd() {
            return index >= json.length();
        }

        private char currentChar() {
            return json.charAt(index);
        }
    }

    private static final class StringParseException extends RuntimeException {
        private StringParseException() {
            super("Invalid JSON");
        }
    }
}