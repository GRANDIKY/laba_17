import java.util.ArrayList;

public class Interpreter {
    private ArrayList<Token> stack = new ArrayList<>();
    private ArrayList<Token> tokens;

    Interpreter(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    private void plus(int i) {
        Double token_next;
        if (tokens.get(i + 1).getType().equals("in_braces")) {
            Tokenizer tokenizer = new Tokenizer((String) tokens.get(i + 1).getValue());
            tokenizer.tokenize();
            Interpreter interpreter = new Interpreter(tokenizer.tokens);
            token_next = interpreter.interpret();
        } else {
            token_next = (Double) tokens.get(i + 1).getValue();
        }
        Double sum = (Double) stack.get(0).getValue() + token_next;
        stack.clear();
        stack.add(new Token<Double>(sum, "digit"));
    }

    private void minus(int i) {
        Double token_next;
        if (tokens.get(i + 1).getType().equals("in_braces")) {
            Tokenizer tokenizer = new Tokenizer((String) tokens.get(i + 1).getValue());
            tokenizer.tokenize();
            Interpreter interpreter = new Interpreter(tokenizer.tokens);
            token_next = interpreter.interpret();
        } else {
            token_next = (Double) tokens.get(i + 1).getValue();
        }
        Double sum = (Double) stack.get(0).getValue() - token_next;
        stack.clear();
        stack.add(new Token<Double>(sum, "digit"));
    }

    private void multi(int i_left, int i_right, int pos) {
        Token left_t = tokens.get(i_left);
        Token right_t = tokens.get(i_right);
        Double left, right;
        if (left_t.getType().equals("in_braces")) {
            Tokenizer tokenizer = new Tokenizer((String) left_t.getValue());
            tokenizer.tokenize();
            Interpreter interpreter = new Interpreter(tokenizer.tokens);
            left = interpreter.interpret();
        } else {
            left = (Double) left_t.getValue();
        }
        if (right_t.getType().equals("in_braces")) {
            Tokenizer tokenizer = new Tokenizer((String) right_t.getValue());
            tokenizer.tokenize();
            Interpreter interpreter = new Interpreter(tokenizer.tokens);
            right = interpreter.interpret();
        } else {
            right = (Double) right_t.getValue();
        }
        tokens.add(pos, new Token(left * right, "digit"));
        tokens.remove(i_left);
        tokens.remove(i_right);
    }

    private void div(int i_left, int i_right, int pos) {
        Token left_t = tokens.get(i_left);
        Token right_t = tokens.get(i_right);
        Double left, right;
        if (left_t.getType().equals("in_braces")) {
            Tokenizer tokenizer = new Tokenizer((String) left_t.getValue());
            tokenizer.tokenize();
            Interpreter interpreter = new Interpreter(tokenizer.tokens);
            left = interpreter.interpret();
        } else {
            left = (Double) left_t.getValue();
        }
        if (right_t.getType().equals("in_braces")) {
            Tokenizer tokenizer = new Tokenizer((String) right_t.getValue());
            tokenizer.tokenize();
            Interpreter interpreter = new Interpreter(tokenizer.tokens);
            right = interpreter.interpret();
        } else {
            right = (Double) right_t.getValue();
        }
        tokens.add(pos, new Token(left / right, "digit"));
        tokens.remove(i_left);
        tokens.remove(i_right);
    }

    private void braces(int i) {
        Tokenizer tokenizer = new Tokenizer((String) tokens.get(i).getValue());
        tokenizer.tokenize();
        Interpreter interpreter = new Interpreter(tokenizer.tokens);
        stack.add(new Token<Double>(interpreter.interpret(), "digit"));
    }

    public double interpret() {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getType().equals("multi")) {
                multi(i - 1, i + 1, i);
            } else if (tokens.get(i).getType().equals("div")) {
                div(i - 1, i + 1, i);
            }
        }
        for (int i = 0; i < tokens.size(); i++) {
            switch (tokens.get(i).getType()) {
                case "digit" -> stack.add(tokens.get(i));
                case "plus" -> {
                    plus(i);
                    i++;
                }
                case "minus" -> {
                    minus(i);
                    i++;
                }

                case "in_braces" -> braces(i);
            }
        }
        return (double) stack.get(0).getValue();
    }
}

