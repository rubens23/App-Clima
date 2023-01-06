package com.example.climaapp.tutorialteste

sealed class Result<out R>(error: kotlin.Error) {

    data class Success<out T>(val data: T) : Result<T>(kotlin.Error(Exception("Not found")))
    data class Error(val exception: Exception) : Result<Nothing>(kotlin.Error(Exception("Not found")))
    object Loading : Result<Nothing>(kotlin.Error(Exception("Not found")))

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null
