public class test {
    private static test ourInstance = new test();

    public static test getInstance() {
        return ourInstance;
    }

    private test() {
    }
}
