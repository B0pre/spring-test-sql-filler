package org.bopre.test.spring.sqlfiller.utils.ordering;

import java.util.Comparator;

public class ComparableWrap<T> implements Comparable<ComparableWrap<T>> {
    private final T inner;
    private final int index;

    public ComparableWrap(int index, T inner) {
        this.index = index;
        this.inner = inner;
    }

    public T getInner() {
        return inner;
    }

    public Integer getIndex() {
        return index;
    }

    @Override
    public int compareTo(ComparableWrap<T> o) {
        return Comparator.comparing((ComparableWrap<T> o1) -> o1.getIndex(), Integer::compare)
                .compare(this, o);
    }

}
