package com.cartrawler.assessment.car;

import java.util.Objects;

public final class CorpSizeKey {
    final boolean corporate;
    final int size;

    public CorpSizeKey(boolean corporate, int size) {
        this.corporate = corporate;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CorpSizeKey that)) return false;
        return corporate == that.corporate && size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corporate, size);
    }
}
