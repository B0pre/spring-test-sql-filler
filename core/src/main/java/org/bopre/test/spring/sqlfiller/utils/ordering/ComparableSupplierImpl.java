package org.bopre.test.spring.sqlfiller.utils.ordering;

import java.util.function.Supplier;

public class ComparableSupplierImpl<T> implements ComparableSupplier<T> {
    private final Supplier<T> supplier;
    private final int order;

    public ComparableSupplierImpl(int order, Supplier<T> supplier) {
        this.supplier = supplier;
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public T get() {
        return supplier.get();
    }

    public static <T> ComparableSupplier<T> comparableOf(int order, Supplier<T> supplier) {
        return new ComparableSupplierImpl<>(order, supplier);
    }

}
