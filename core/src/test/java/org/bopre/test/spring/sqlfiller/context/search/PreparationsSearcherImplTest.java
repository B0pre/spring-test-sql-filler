package org.bopre.test.spring.sqlfiller.context.search;

import org.bopre.test.spring.sqlfiller.api.annotation.SqlArg;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlTemplate;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlTemplatePack;
import org.bopre.test.spring.sqlfiller.context.processing.SqlPreparation;
import org.bopre.test.spring.sqlfiller.exception.SqlFillerException;
import org.bopre.test.spring.sqlfiller.utils.io.ResourcesUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class PreparationsSearcherImplTest {

    private PreparationsSearcher preparationsSearcher;

    @BeforeEach
    public void beforeEach() {
        preparationsSearcher = new PreparationsSearcherImpl();
    }

    @Test
    @SqlTemplate(
            template = "/templates/simple_template.sqtmpl",
            cleanupTemplate = "/templates/simple_template.cleanup.sqtmpl",
            args = {@SqlArg(name = "name", value = "qwerty")}
    )
    public void simpleTest() {
        final String expectedTemplate = getResourceContent("/templates/simple_template.sqtmpl");
        final String expectedCleanup = getResourceContent("/templates/simple_template.cleanup.sqtmpl");

        final Collection<SqlPreparation> actual = preparationsSearcher
                .findPreparations(getMethodAnnotations("simpleTest"));

        assertEquals(1, actual.size(), "wrong preparations count");

        final SqlPreparation preparation = actual.iterator().next();
        assertAll(
                "sqlPreparation",
                () -> assertEquals(expectedTemplate, preparation.getSqlTemplate().orElse(null), "wrong direct template"),
                () -> assertEquals(expectedCleanup, preparation.getSqlCleanup().orElse(null), "wrong cleanup template"),
                () -> assertEquals("qwerty", preparation.getProperties().getProperty("name").orElse(null), "wrong property(name)")
        );
    }

    @Test
    @SqlTemplate(
            inlineSql = "inline sample",
            inlineCleanupSql = "inline cleanup sample"
    )
    public void simpleInlineTest() {
        final String expectedTemplate = "inline sample";
        final String expectedCleanup = "inline cleanup sample";

        final Collection<SqlPreparation> actual = preparationsSearcher
                .findPreparations(getMethodAnnotations("simpleInlineTest"));

        assertEquals(1, actual.size(), "wrong preparations count");

        final SqlPreparation preparation = actual.iterator().next();
        assertAll(
                "sqlPreparation",
                () -> assertEquals(expectedTemplate, preparation.getSqlTemplate().orElse(null), "wrong direct template"),
                () -> assertEquals(expectedCleanup, preparation.getSqlCleanup().orElse(null), "wrong cleanup template")
        );
    }

    @Test
    @SqlTemplate(
            inlineSql = "inline sample",
            template = "/templates/simple_template.sqtmpl",
            inlineCleanupSql = "inline cleanup sample",
            cleanupTemplate = "/templates/simple_template.cleanup.sqtmpl"
    )
    public void simpleMixTest() {

        SqlFillerException exception = assertThrows(SqlFillerException.class, () ->
                preparationsSearcher.findPreparations(getMethodAnnotations("simpleMixTest"))
        );

        assertAll(
                "filler exception",
                () -> assertNotNull(exception, "exception"),
                () -> assertNotNull(exception.getMessage(), "exception.message"),
                () -> assertEquals("unsupported both inline with resources", exception.getMessage(), "wrong message")
        );
    }

    @Test
    public void simpleTemplatePackTest() {
        final String expectedTemplate = "inline sample";
        final String expectedCleanup = "inline cleanup sample";

        final Collection<SqlPreparation> actual = preparationsSearcher
                .findPreparations(getMethodAnnotations("simplePackage"));

        assertEquals(1, actual.size(), "wrong preparations count");

        final SqlPreparation preparation = actual.iterator().next();
        assertAll(
                "sqlPreparation",
                () -> assertEquals(expectedTemplate, preparation.getSqlTemplate().orElse(null), "wrong direct template"),
                () -> assertEquals(expectedCleanup, preparation.getSqlCleanup().orElse(null), "wrong cleanup template")
        );
    }

    @Test
    public void simpleTemplatePackRewriteArgsTest() {
        final Collection<SqlPreparation> actual = preparationsSearcher
                .findPreparations(getMethodAnnotations("simplePackage"));

        assertEquals(1, actual.size(), "wrong preparations count");

        final SqlPreparation preparation = actual.iterator().next();
        assertAll(
                "sqlPreparation",
                () -> assertEquals("123", preparation.getProperties().getProperty("id").orElse(null), "wrong property(name)"),
                () -> assertEquals("qwerty with addition", preparation.getProperties().getProperty("name").orElse(null), "wrong property(name)")
        );
    }

    @Test
    @SqlTemplate(inlineSql = "inline sample0")
    @SqlTemplate(inlineSql = "inline sample1")
    @SqlTemplate(inlineSql = "inline sample2")
    @SqlTemplate(inlineSql = "inline sample3")
    @SqlTemplate(inlineSql = "inline sample4")
    public void complexInlineTest() {
        final String[] expected = {
                "inline sample0",
                "inline sample1",
                "inline sample2",
                "inline sample3",
                "inline sample4",
        };

        final Collection<SqlPreparation> actual = preparationsSearcher.findPreparations(getMethodAnnotations("complexInlineTest"));
        final String[] actualArr = actual.stream()
                .map(p -> p.getSqlTemplate().orElse(null))
                .toArray(String[]::new);
        assertAll(
                "ordering",
                () -> assertEquals(expected.length, actual.size(), "wrong preparations count"),
                () -> assertArrayEquals(expected, actualArr, "direct sql list")
        );
    }


    @Test
    @SqlTemplatePack(value = SimplePack.class, order = 0)
    @SqlTemplate(inlineSql = "inline sample2", order = 1)
    @SqlTemplatePack(value = SimplePack.class, order = 2)
    @SqlTemplatePack(value = SimplePack.class, order = 3)
    @SqlTemplate(inlineSql = "inline sample2", order = 4)
    @SqlTemplatePack(value = SimplePack.class, order = 5)
    @SqlTemplate(inlineSql = "inline sample2", order = 6)
    public void simpleTemplateComplexMixTest() {
        final String[] expected = {
                "inline sample",
                "inline sample2",
                "inline sample",
                "inline sample",
                "inline sample2",
                "inline sample",
                "inline sample2"
        };

        final Collection<SqlPreparation> actual = preparationsSearcher.findPreparations(getMethodAnnotations("simpleTemplateComplexMixTest"));
        final String[] actualArr = actual.stream()
                .map(p -> p.getSqlTemplate().orElse(null))
                .toArray(String[]::new);
        assertAll(
                "ordering",
                () -> assertEquals(expected.length, actual.size(), "wrong preparations count"),
                () -> assertArrayEquals(expected, actualArr, "direct sql list")
        );
    }

    @Test
    @SqlTemplatePack(value = ComplexPack.class, order = 0)
    @SqlTemplate(inlineSql = "inline sample2", order = 1)
    @SqlTemplatePack(value = ComplexPack.class, order = 2)
    @SqlTemplatePack(value = ComplexPack.class, order = 3)
    @SqlTemplate(inlineSql = "inline sample2", order = 4)
    @SqlTemplatePack(value = ComplexPack.class, order = 5)
    @SqlTemplate(inlineSql = "inline sample2", order = 6)
    public void simpleTemplateComplexComplexMixTest() {
        Collection<String> expectedComplexPack = Arrays.asList(
                "inline sample 0",
                "inline sample",
                "inline sample",
                "inline sample 0"
        );
        final String[] expected = new ArrayList<String>() {{
            this.addAll(expectedComplexPack);
            this.add("inline sample2");
            this.addAll(expectedComplexPack);
            this.addAll(expectedComplexPack);
            this.add("inline sample2");
            this.addAll(expectedComplexPack);
            this.add("inline sample2");
        }}.toArray(new String[0]);

        final Collection<SqlPreparation> actual = preparationsSearcher.findPreparations(getMethodAnnotations("simpleTemplateComplexComplexMixTest"));
        final String[] actualArr = actual.stream()
                .map(p -> p.getSqlTemplate().orElse(null))
                .toArray(String[]::new);
        assertAll(
                "ordering",
                () -> assertEquals(expected.length, actual.size(), "wrong preparations count"),
                () -> assertArrayEquals(expected, actualArr, "direct sql list")
        );
    }

    private Annotation[] getMethodAnnotations(String name) {
        try {
            return this.getClass().getMethod(name).getAnnotations();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private String getResourceContent(String resource) {
        return ResourcesUtils.readResourceAsString(resource);
    }

    @SqlTemplatePack(value = SimplePack.class, args = {
            @SqlArg(name = "name", value = "qwerty with addition")
    })
    public void simplePackage() {
    }

    @SqlTemplate(
            inlineSql = "inline sample",
            inlineCleanupSql = "inline cleanup sample",
            args = {
                    @SqlArg(name = "id", value = "123"),
                    @SqlArg(name = "name", value = "qwerty")
            }
    )
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface SimplePack {
    }

    @SqlTemplate(inlineSql = "inline sample 0", order = 0)
    @SqlTemplatePack(value = SimplePack.class, order = 1)
    @SqlTemplatePack(value = SimplePack.class, order = 2)
    @SqlTemplate(inlineSql = "inline sample 0", order = 30000)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ComplexPack {
    }

}