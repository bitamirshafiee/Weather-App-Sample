package com.weather.ext

import android.nfc.FormatException
import androidx.annotation.StringRes
import com.google.gson.JsonSyntaxException
import com.weather.R
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException

fun errorHandlerHelper(
    throwable: Throwable
): NetworkError {
    val networkError = when (throwable) {
        is HttpException -> {
            when (throwable.code()) {
                401 -> {
                    NetworkError(
                        Exception.UNAUTHORIZED, R.string.str_error_unauthorized
                    )
                }
                404 -> {
                    //I just add this string as an example for  404 error from what I get with a
                    // wrong city name, for sure this error handler function
                    // needs to define more accurately with codes related to a specific error
                    NetworkError(
                        Exception.UNAUTHORIZED, R.string.str_error_city_not_found
                    )
                }
                else -> NetworkError(
                    Exception.UNDEFINE, R.string.str_error_undefined
                )
            }
        }
        is FormatException -> {
            NetworkError(Exception.FORMAT, R.string.str_error_format)
        }
        is EOFException -> {
            NetworkError(Exception.EOF, R.string.str_error_EOF)
        }
        is IOException -> {
            NetworkError(Exception.IO, R.string.str_error_io)
        }
        is JsonSyntaxException -> {
            NetworkError(Exception.JSON_SYNTAX, R.string.str_error_json_syntax)
        }
        else -> NetworkError(Exception.UNDEFINE, R.string.str_error_undefined)
    }
    return networkError
}

data class NetworkError(val type: Int, @StringRes val errorMessage: Int)

object Exception {
    const val UNAUTHORIZED = 0
    const val FORMAT = 1
    const val EOF = 2
    const val IO = 3
    const val JSON_SYNTAX = 4
    const val UNDEFINE = 5
}