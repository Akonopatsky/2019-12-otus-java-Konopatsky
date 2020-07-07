import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.testFW.runing.TestFrameWork;

public class Demo {
    static private Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) throws ReflectiveOperationException {
        TestFrameWork testFrameWork = new TestFrameWork();
        testFrameWork.startTesting("TestClass");
        logger.info("total tests " + testFrameWork.getTestsCount());
        logger.info("successful tests " + testFrameWork.getSuccessCount());
        logger.info("exceptions " + testFrameWork.getExceptionCount());
    }
}
