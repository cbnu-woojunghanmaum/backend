package com.cj.smartworker.domain.common

abstract class BaseEntity<ID>(
    open val id: ID,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseEntity<*>) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
