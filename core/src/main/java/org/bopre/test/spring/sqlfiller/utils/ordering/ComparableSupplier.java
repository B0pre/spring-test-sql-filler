package org.bopre.test.spring.sqlfiller.utils.ordering;

import java.util.function.Supplier;

public interface ComparableSupplier<T> extends Supplier<T>, Comparable<ComparableSupplier<T>> {

    int getOrder();

    @Override
    default int compareTo(ComparableSupplier<T> o) {
        if (o == null)
            return -1;
        return Integer.compare(getOrder(), o.getOrder());
    }

}
