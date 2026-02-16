package com.fd.app.model;

import java.util.EnumSet;
import java.util.Set;

public enum OrderStatus {
    CREATED,
    PAID,
    CANCELLED,
    DELIVERED;

    private Set<OrderStatus> allowedNext;

    static {
        CREATED.allowedNext = EnumSet.of(PAID, CANCELLED);
        PAID.allowedNext = EnumSet.of(DELIVERED);
        CANCELLED.allowedNext = EnumSet.noneOf(OrderStatus.class);
        DELIVERED.allowedNext = EnumSet.noneOf(OrderStatus.class);
    }

    public boolean canTransitionTo(OrderStatus target) {
        if (target == null) return false;
        return allowedNext.contains(target);
    }

    public Set<OrderStatus> allowedNextStatuses() {
        return EnumSet.copyOf(allowedNext);
    }
}