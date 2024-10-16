package com.cj.smartworker.domain.common

abstract class DomainException(
    override val message: String?
): RuntimeException()
