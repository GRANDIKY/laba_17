import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            String expr = in.nextLine();
            if (expr.equals("exit")) {
                return;
            }
            Tokenizer tokenizer = new Tokenizer(expr);
            tokenizer.tokenize();

            Interpreter interpreter = new Interpreter(tokenizer.tokens);
            System.out.println(interpreter.interpret());
        }
    }
}