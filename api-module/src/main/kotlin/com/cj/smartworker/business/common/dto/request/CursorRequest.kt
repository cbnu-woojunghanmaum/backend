package com.cj.smartworker.business.common.dto.request

data class CursorRequest private constructor(
    val next: Int? = null,
    val size: Int = 10,
) {
    companion object {
        private const val DEFAULT_SIZE = 20
        private const val MAX_SIZE = 30
        operator fun invoke(next: Int, size: Int?): CursorRequest {
            return size?.let {
                validate(size)

                CursorRequest(
                    next = next,
                    size = size,
                )
            } ?: run {
                CursorRequest(
                    next = next,
                    size = DEFAULT_SIZE,
                )
            }
        }
        operator fun invoke(size: Int?): CursorRequest {
            return size?.let {
                validate(size)

                CursorRequest(
                    next = 1,
                    size = size,
                )
            } ?: run {
                CursorRequest(
                    next = 1,
                    size = DEFAULT_SIZE,
                )
            }
        }

        private fun validate(size: Int) {
            require(size > 0) { "size는 0보다 커야 합니다" }
            require(size <= MAX_SIZE) { "페이징 조회 size 최대 개수는 30개 입니다" }
        }

    }
}
