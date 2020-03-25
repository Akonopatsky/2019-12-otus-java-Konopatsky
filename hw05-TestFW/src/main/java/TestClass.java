import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.testFW.runing.After;
import ru.otus.testFW.runing.Before;
import ru.otus.testFW.runing.Test;

public class TestClass {
    static private Logger logger = LoggerFactory.getLogger(TestClass.class);

    @Before
    public void methodBefore() {
        logger.info("before ");
    }

    @Test
    public void test1() {
        logger.info("test1 ");
    }

    @Test
    public void test2() {
        logger.info("test2 ");
        throw new IndexOutOfBoundsException();
    }

    @Test
    public void test4() {
        logger.info("test4 ");
        throw new UnsupportedOperationException();
    }

    @Test
    public void test3() {
        logger.info("test3 ");
    }

    @After
    public void methodAfter1() {
        logger.info("methodAfter1 ");
    }

    @After
    public void methodAfter2() {
        logger.info("methodAfter2 ");
    }

}
