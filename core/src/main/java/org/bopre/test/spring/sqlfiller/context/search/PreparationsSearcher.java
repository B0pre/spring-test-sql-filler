package org.bopre.test.spring.sqlfiller.context.search;

import org.bopre.test.spring.sqlfiller.context.processing.SqlPreparation;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface PreparationsSearcher {

    Collection<SqlPreparation> findPreparations(Annotation[] init);

    static PreparationsSearcher init() {
        return new PreparationsSearcherImpl();
    }

}
