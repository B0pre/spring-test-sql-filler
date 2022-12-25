package org.bopre.test.spring.sqlfiller.context.search;

import org.bopre.test.spring.sqlfiller.api.annotation.SqlArg;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlTemplate;
import org.bopre.test.spring.sqlfiller.api.annotation.SqlTemplatePack;
import org.bopre.test.spring.sqlfiller.api.annotation.repeat.SqlTemplates;
import org.bopre.test.spring.sqlfiller.api.annotation.repeat.TemplatePacks;
import org.bopre.test.spring.sqlfiller.context.processing.SqlPreparation;
import org.bopre.test.spring.sqlfiller.context.processing.SqlTemplateProperties;
import org.bopre.test.spring.sqlfiller.context.processing.impl.SqlPreparationImpl;
import org.bopre.test.spring.sqlfiller.exception.SqlFillerException;
import org.bopre.test.spring.sqlfiller.utils.io.ResourcesUtils;
import org.bopre.test.spring.sqlfiller.utils.ordering.ComparableSupplier;

import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.bopre.test.spring.sqlfiller.utils.ordering.ComparableSupplierImpl.comparableOf;
import static org.bopre.test.spring.sqlfiller.utils.text.TextUtils.isNullOrEmpty;

public class PreparationsSearcherImpl implements PreparationsSearcher {

    @Override
    public Collection<SqlPreparation> findPreparations(Annotation[] init) {
        return new ArrayList<>(findPreparationsRecursive(init, SqlTemplateProperties.empty()));
    }

    private List<SqlPreparation> findPreparationsRecursive(
            Annotation[] init,
            SqlTemplateProperties basic
    ) {
        final List<ComparableSupplier<List<SqlPreparation>>> lazySqlPreparationWithOrdering = new LinkedList<>();

        for (Annotation annotation : init) {
            if (annotation instanceof SqlTemplates) {
                final SqlTemplates sqlTemplates = (SqlTemplates) annotation;
                for (SqlTemplate sqlTemplate : sqlTemplates.value()) {
                    lazySqlPreparationWithOrdering.add(fromTemplateProvider(sqlTemplate, basic));
                }
            }
            if (annotation instanceof TemplatePacks) {
                final TemplatePacks templatePacks = (TemplatePacks) annotation;
                for (SqlTemplatePack sqlTemplatePack : templatePacks.value()) {
                    lazySqlPreparationWithOrdering.add(fromTemplatePackProvider(sqlTemplatePack, basic));
                }
            }
            if (annotation instanceof SqlTemplate) {
                final SqlTemplate sqlTemplate = (SqlTemplate) annotation;
                lazySqlPreparationWithOrdering.add(fromTemplateProvider(sqlTemplate, basic));
            }
            if (annotation instanceof SqlTemplatePack) {
                final SqlTemplatePack sqlTemplatePack = (SqlTemplatePack) annotation;
                lazySqlPreparationWithOrdering.add(fromTemplatePackProvider(sqlTemplatePack, basic));
            }
        }

        return lazySqlPreparationWithOrdering.stream()
                .sorted(Comparator.comparing(Function.identity(), ComparableSupplier::compareTo))
                .flatMap(p -> p.get().stream())
                .collect(Collectors.toList());
    }

    private ComparableSupplier<List<SqlPreparation>> fromTemplateProvider(SqlTemplate sqlTemplate, SqlTemplateProperties basic) {
        return comparableOf(sqlTemplate.order(),
                () -> Collections.singletonList(createPreparationFromTemplate(sqlTemplate, basic))
        );
    }

    private ComparableSupplier<List<SqlPreparation>> fromTemplatePackProvider(SqlTemplatePack sqlTemplatePack, SqlTemplateProperties basic) {
        return comparableOf(
                sqlTemplatePack.order(),
                () -> findPreparationsRecursive(
                        getAnnotations(sqlTemplatePack.value()),
                        SqlTemplateProperties.superposition(basic, SqlTemplateProperties.ofMap(transformArgsToMap(sqlTemplatePack.args())))
                )
        );
    }

    private Annotation[] getAnnotations(Class<? extends Annotation> clazz) {
        return clazz.getAnnotations();
    }

    private SqlPreparation createPreparationFromTemplate(SqlTemplate sqlTemplate, SqlTemplateProperties basic) {
        return SqlPreparationImpl.builder()
                .sqlTemplate(defineSqlTemplate(sqlTemplate.template(), sqlTemplate.inlineSql()))
                .sqlCleanup(defineSqlTemplate(sqlTemplate.cleanupTemplate(), sqlTemplate.inlineCleanupSql()))
                .properties(
                        SqlTemplateProperties.superposition(
                                basic,
                                SqlTemplateProperties.ofMap(
                                        transformArgsToMap(sqlTemplate.args())
                                )
                        )
                )
                .build();
    }

    private String defineSqlTemplate(String templateResource, String inline) {
        if (!isNullOrEmpty(templateResource) && !isNullOrEmpty(inline)) {
            throw new SqlFillerException("unsupported both inline with resources");
        }
        if (!isNullOrEmpty(templateResource)) {
            return ResourcesUtils.readResourceAsString(templateResource, Charset.defaultCharset());
        }
        if (!isNullOrEmpty(inline)) {
            return inline;
        }
        return null;
    }


    private Map<String, String> transformArgsToMap(SqlArg[] args) {
        final Map<String, String> result = new HashMap<>();
        for (SqlArg sqlArg : args) {
            result.put(sqlArg.name(), sqlArg.value());
        }
        return result;
    }

}
