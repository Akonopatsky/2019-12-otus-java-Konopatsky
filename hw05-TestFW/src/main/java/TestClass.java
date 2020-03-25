import ru.otus.testFW.runing.After;
import ru.otus.testFW.runing.Before;
import ru.otus.testFW.runing.Test;

public class TestClass {

    @Before
    public void methodBefore() {
        System.out.println("before ");
    }

    @Test
    public void test1() {
        System.out.println("test1 ");
    }

    @Test
    public void test2() {
        System.out.println("test2 ");
        throw new IndexOutOfBoundsException();
    }

    @Test
    public void test4() {
        System.out.println("test4 ");
        throw new UnsupportedOperationException();
    }

    @Test
    public void test3() {
        System.out.println("test3 ");
    }

    @After
    public void methodAfter1() {
        System.out.println("methodAfter1 ");
    }

    @After
    public void methodAfter2() {
        System.out.println("methodAfter2 ");
    }

}
