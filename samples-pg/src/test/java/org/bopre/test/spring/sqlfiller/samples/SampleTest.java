package org.bopre.test.spring.sqlfiller.samples;

import org.bopre.test.spring.sqlfiller.annotation.EnableSqlFiller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class
)
@EnableSqlFiller
public class SampleTest {

    private static final Logger logger = LoggerFactory.getLogger(SampleTest.class);

    @Test
    public void test() {
        logger.info("test perform");
    }

}
