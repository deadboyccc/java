package intermediate;

@FunctionalInterface
public interface Operations<T> {
    T operate(T val1, T val2);
}
