public class B extends A {
    public static void main(String... args) {
        A a = new B();
        a.doit();
    }

    public void doit() {
        System.out.print("b");
    }
}