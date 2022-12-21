package org.bopre.test.spring.sqlfiller.processor;

import org.bopre.test.spring.sqlfiller.processor.obj.TemplateProcessingResult;

public interface SqlTemplateProcessor {

    TemplateProcessingResult processSql(final String sqlTemplate);

}
