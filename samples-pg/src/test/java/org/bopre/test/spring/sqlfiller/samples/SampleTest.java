package org.bopre.test.spring.sqlfiller.samples;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.bopre.test.spring.sqlfiller.annotation.EnableSqlFiller;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlArg;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlTemplate;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlTemplatePack;
import org.bopre.test.spring.sqlfiller.samples.model.RegisteredUser;
import org.bopre.test.spring.sqlfiller.samples.model.UserRating;
import org.bopre.test.spring.sqlfiller.samples.repository.UserRatingRepository;
import org.bopre.test.spring.sqlfiller.samples.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@AutoConfigureEmbeddedDatabase
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class
)
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableSqlFiller
public class SampleTest {

    private static final Logger logger = LoggerFactory.getLogger(SampleTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @SqlTemplatePack(value = InitSchemaDb.class)
    @SqlTemplatePack(value = InsertUserWithRating.class, args = {
            @SqlArg(name = "user_id", value = "100"),
            @SqlArg(name = "rating_id", value = "1000"),
            @SqlArg(name = "user_name", value = "First User Name"),
            @SqlArg(name = "user_rating_value", value = "1.0")
    })
    @SqlTemplatePack(value = InsertUserWithRating.class, args = {
            @SqlArg(name = "user_id", value = "101"),
            @SqlArg(name = "rating_id", value = "1001"),
            @SqlArg(name = "user_rating_value", value = "2.0")
    })
    @Test
    public void test() {
        logger.info("test perform");
        List<RegisteredUser> allUsers = userRepository.getAll();

        assertEquals("users count", 2, allUsers.size());

        RegisteredUser user0 = allUsers.get(0);
        RegisteredUser user1 = allUsers.get(1);

        assertEquals("user0 id", 100, user0.getId().intValue());
        assertEquals("user0 name", "First User Name", user0.getName());
        assertUserRating("user0 rating", user0, 1.0);
        assertEquals("user1 id", 101, user1.getId().intValue());
        assertEquals("user1 name", "", user1.getName());
        assertUserRating("user1 rating", user1, 2.0);
    }

    private void assertUserRating(String message, RegisteredUser user, double expectedRating) {
        UserRating userRating = userRatingRepository.forUser(user);

        assertNotNull(message, userRating);
        assertEquals(message, expectedRating, userRating.getRating(), 0.001);
    }

    @SqlTemplate(template = "/sql/templates/init_users_table.sqtmpl", cleanupTemplate = "/sql/templates/init_users_table.cleanup.sqtmpl", order = 0)
    @SqlTemplate(template = "/sql/templates/init_rating_table.sqtml", cleanupTemplate = "/sql/templates/init_rating_table.cleanup.sqtml", order = 1)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private @interface InitSchemaDb {
    }

    @SqlTemplate(
            inlineSql = "INSERT INTO r_user(id, name, description) VALUES (#{INT/user_id:0}, #{STRING/user_name:}, #{STRING/user_description:})",
            inlineCleanupSql = "DELETE FROM r_user WHERE id = #{INT/user_id:0}"
    )
    @SqlTemplate(
            inlineSql = "INSERT INTO user_rating(id, user_id, rating) VALUES (#{INT/rating_id:0}, #{INT/user_id:}, #{DOUBLE/user_rating_value:0.0})",
            inlineCleanupSql = "DELETE FROM user_rating WHERE id = #{INT/rating_id:0}"
    )
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private @interface InsertUserWithRating {
    }

}
