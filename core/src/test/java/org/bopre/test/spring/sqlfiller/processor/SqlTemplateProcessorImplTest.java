package org.bopre.test.spring.sqlfiller.processor;

import org.bopre.test.spring.sqlfiller.processor.obj.ParameterDefinition;
import org.bopre.test.spring.sqlfiller.processor.obj.SupportedType;
import org.bopre.test.spring.sqlfiller.processor.obj.TemplateProcessingResult;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SqlTemplateProcessorImplTest {

    private SqlTemplateProcessor sqlTemplateProcessor;

    @Before
    public void beforeEach() {
        sqlTemplateProcessor = new SqlTemplateProcessorImpl();
    }

    @Test
    public void testProcessSql() {
        final String expected = "insert into test_table (\n" +
                "    id,\n" +
                "    field0,\n" +
                "    field1,\n" +
                "    field2\n" +
                ") values (\n" +
                "    ?1\n" +
                "    ?2,\n" +
                "    ?3,\n" +
                "    ?4\n" +
                ");\n";

        final String sqlTemplate = "insert into test_table (\n" +
                "    id,\n" +
                "    field0,\n" +
                "    field1,\n" +
                "    field2\n" +
                ") values (\n" +
                "    #{LONG/id:0}\n" +
                "    #{INT/field0:1},\n" +
                "    #{DOUBLE/field1:1.1},\n" +
                "    #{STRING/field2:qwerty qwerty}\n" +
                ");\n";

        final String actual = sqlTemplateProcessor.processSql(sqlTemplate).getSql();
        assertEquals("wrong sql processing", expected, actual);
    }

    @Test
    public void testProcessSqlParams() {
        final String sqlTemplate = "insert into test_table (\n" +
                "    id,\n" +
                "    field0,\n" +
                "    field1\n" +
                ") values (\n" +
                "    #{LONG/id:0}\n" +
                "    #{INT/field0:1},\n" +
                "    #{DOUBLE/field1:1.1},\n" +
                "    #{STRING/field2:qwerty qwerty}\n" +
                ");\n";

        final List<ParameterDefinition> expected = Arrays.asList(
                ParameterDefinition.builder()
                        .type(SupportedType.LONG)
                        .index(1)
                        .name("id")
                        .defaultValue("0")
                        .build(),
                ParameterDefinition.builder()
                        .type(SupportedType.INT)
                        .index(2)
                        .name("field0")
                        .defaultValue("1")
                        .build(),
                ParameterDefinition.builder()
                        .type(SupportedType.DOUBLE)
                        .index(3)
                        .name("field1")
                        .defaultValue("1.1")
                        .build(),
                ParameterDefinition.builder()
                        .type(SupportedType.STRING)
                        .index(4)
                        .name("field2")
                        .defaultValue("qwerty qwerty")
                        .build()
        );

        final TemplateProcessingResult actual = sqlTemplateProcessor.processSql(sqlTemplate);

        assertEquals("wrong sql processing", expected, actual.getParameters());
    }

}