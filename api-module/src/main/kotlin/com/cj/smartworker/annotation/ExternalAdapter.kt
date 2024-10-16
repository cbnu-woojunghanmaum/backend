package com.cj.smartworker.annotation

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class ExternalAdapter()
/**
 * add to external service adapter
 */
