package org.example.concertbackend.common.model

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T,
) {
    companion object {
        fun success(type: SuccessType): ApiResponse<Unit> = ApiResponse(type.status, type.message, Unit)

        fun <T> success(
            type: SuccessType,
            data: T,
        ): ApiResponse<T> = ApiResponse(type.status, type.message, data)

        fun error(errorType: ErrorType): ApiResponse<Unit> = ApiResponse(errorType.status, errorType.message, Unit)
    }
}
