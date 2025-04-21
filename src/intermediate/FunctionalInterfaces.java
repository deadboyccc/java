package intermediate;

public class FunctionalInterfaces {
    public static void main(String[] args) {
        int result = calculator((v1, v2) -> v1 + v2, 2, 2);
        System.out.println(result);
        System.out.println(calculator((Double v1, Double v2) -> v1 / v2, 10.0, 3.0));
        var result2 = calculator((String a, String b) -> a.toUpperCase() + " " + b.toUpperCase(), "Ahmed", "Ali");
        System.out.println(result2);

    }

    public static <T> T calculator(Operations<T> fun, T v1, T v2) {
        return fun.operate(v1, v2);

    }

}
