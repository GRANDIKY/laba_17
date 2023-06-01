public class Token<T> {
    private T value;
    private String type;

    public Token(T value, String type) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}