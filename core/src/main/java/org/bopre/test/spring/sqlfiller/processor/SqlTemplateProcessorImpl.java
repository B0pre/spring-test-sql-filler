package org.bopre.test.spring.sqlfiller.processor;

import org.bopre.test.spring.sqlfiller.processor.exception.IllegalArgumentPlaceholderException;
import org.bopre.test.spring.sqlfiller.processor.obj.ParameterDefinition;
import org.bopre.test.spring.sqlfiller.processor.obj.SupportedType;
import org.bopre.test.spring.sqlfiller.processor.obj.TemplateProcessingResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlTemplateProcessorImpl implements SqlTemplateProcessor {

    @Override
    public TemplateProcessingResult processSql(final String sqlTemplate) {
        final TemplateProcessingResult.Builder resultBuilder = TemplateProcessingResult.builder()
                .originTemplate(sqlTemplate);

        final String patternStr = "#\\{.*?}";
        final Pattern pattern = Pattern.compile(patternStr);
        final StringBuffer out = new StringBuffer();
        final Matcher matcher = pattern.matcher(sqlTemplate);

        int index = 1;
        while (matcher.find()) {
            final int currentIndex = index++;

            final String replacement = matcher.group(0);
            final ParameterDefinition parameterDefinition = analyzeReplacement(currentIndex, replacement);
            resultBuilder.parameter(parameterDefinition);

            final String replaceWith = "?";
            matcher.appendReplacement(out, replaceWith);
        }
        matcher.appendTail(out);

        resultBuilder.sql(out.toString());
        return resultBuilder.build();
    }

    private ParameterDefinition analyzeReplacement(int index, String replacement) {
        final Pattern pattern = Pattern.compile("#\\{((?<type>[A-Z]+)/)?(?<name>[^:]*)(:(?<default>[^}]*))?\\}");
        final Matcher matcher = pattern.matcher(replacement);

        if (!matcher.find())
            throw new IllegalArgumentPlaceholderException("invalid placeholder: " + replacement);

        final String type = orDefault(matcher.group("type"), SupportedType.STRING.name());
        final String name = matcher.group("name");
        final String defaultValue = matcher.group("default");

        return ParameterDefinition.builder()
                .type(SupportedType.typeOf(type))
                .index(index)
                .name(name)
                .defaultValue(defaultValue)
                .build();
    }

    private String orDefault(String value, String defaultV) {
        if (value == null)
            return defaultV;
        return value;
    }

}
