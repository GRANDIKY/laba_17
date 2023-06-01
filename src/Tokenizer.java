import java.util.ArrayList;

public class Tokenizer {
    ArrayList<Token> tokens = new ArrayList<>();
    public String expression;

    public Tokenizer(String expression) {
        this.expression = expression;
    }

    private int get_full_num(int i) {
        Double result = 0.0;
        for (; i < expression.length(); i++) {
            char t = expression.charAt(i);
            String sym = Character.toString(t);
            if (sym.matches("\\d")) {
                result = result * 10 + Integer.parseInt(sym);
            } else {
                break;
            }
        }
        tokens.add(new Token<Double>((Double) result, "digit"));
        return i - 1;
    }

    private int get_full_braces(int i) {
        int braces_counter = 0;
        StringBuffer result = new StringBuffer();
        for (; i < expression.length(); i++) {
            char t = expression.charAt(i);
            String sym = Character.toString(t);
            if (sym.equals("(")) {
                braces_counter++;
                if (braces_counter > 1) {
                    result.append(sym);
                }
            } else if (sym.equals(")")) {
                braces_counter--;
                if (braces_counter > 0) {
                    result.append(sym);
                }

            } else {
                result.append(sym);
            }
            if (braces_counter == 0) {
                break;
            }
        }
        tokens.add(new Token<String>(result.toString(), "in_braces"));
        return i - 1;
    }

    public void tokenize() {
        for (int i = 0; i < expression.length(); i++) {
            char t = expression.charAt(i);
            String sym = Character.toString(t);
            if (sym.matches("\\d")) {
                i = get_full_num(i);
            } else if (sym.equals("+")) {
                tokens.add(new Token<String>("+", "plus"));
            } else if (sym.equals("-")) {
                tokens.add(new Token<String>("-", "minus"));
            } else if (sym.equals("*")) {
                tokens.add(new Token<String>("*", "multi"));
            } else if (sym.equals("/")) {
                tokens.add(new Token<String>("/", "div"));
            } else if (sym.equals("(")) {
                i = get_full_braces(i);
            }
        }
    }
}