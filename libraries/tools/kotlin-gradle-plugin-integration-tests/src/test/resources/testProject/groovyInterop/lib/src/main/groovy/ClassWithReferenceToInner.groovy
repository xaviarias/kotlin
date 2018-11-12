public class ClassWithReferenceToInner {
    public void f1(Thread.State state) {}

    public void f2(Outer.Nested nested) {}
}

@interface Foo {}

class Outer {
    class Nested {
        Nested(@Foo String name) {

        }
    }
}

enum GEnum {
    FOO("123");

    GEnum(@Foo String value) {}
}