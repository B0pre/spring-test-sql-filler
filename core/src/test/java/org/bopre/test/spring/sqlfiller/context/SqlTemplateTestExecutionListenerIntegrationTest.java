package org.bopre.test.spring.sqlfiller.context;

import org.bopre.test.spring.sqlfiller.annotation.EnableSqlFiller;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlArg;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlTemplate;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlTemplatePack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@EnableSqlFiller
class SqlTemplateTestExecutionListenerIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @SqlTemplate(
            inlineSql = "CREATE TABLE test(name TEXT);",
            inlineCleanupSql = "DROP TABLE test",
            order = 0
    )
    @SqlTemplatePack(value = InsertTest.class, args = {@SqlArg(name = "name_arg", value = "first")}, order = 1)
    @Test
    void testSingleInsert() {
        assertEquals(1, getLinesCount(), "wrong lines count");
    }

    @SqlTemplate(
            inlineSql = "CREATE TABLE test(name TEXT);",
            inlineCleanupSql = "DROP TABLE test",
            order = 0
    )
    @SqlTemplatePack(value = InsertTest.class, args = {@SqlArg(name = "name_arg", value = "first")}, order = 1)
    @SqlTemplatePack(value = InsertTest.class, args = {@SqlArg(name = "name_arg", value = "secone")}, order = 2)
    @Test
    void testTwoInserts() {
        assertEquals(2, getLinesCount(), "wrong lines count");
    }

    private Integer getLinesCount() {
        try {
            ResultSet resultSet = dataSource.getConnection().prepareStatement("SELECT COUNT(*) AS cnt FROM test")
                    .executeQuery();
            if (!resultSet.next())
                throw new IllegalStateException("no lines in select count(*)");
            return resultSet.getInt("cnt");
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
    }

    @SqlTemplate(
            inlineSql = "INSERT INTO test(name) VALUES (#{STRING/name_arg:})",
            inlineCleanupSql = "DELETE FROM test WHERE name = #{STRING/name_arg:}"
    )
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private @interface InsertTest {

    }

}