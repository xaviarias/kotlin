fun main(args: Array<String>) {
    System.out.println(MyTraitAccessor().myField)

    System.out.println(ClassWithReferenceToInner().f1(null))
    System.out.println(ClassWithReferenceToInner().f2(null))

    //Tests for ASM 7 migration
    System.out.println(Outer().Nested("123"))
    System.out.println(GEnum.FOO)
}
