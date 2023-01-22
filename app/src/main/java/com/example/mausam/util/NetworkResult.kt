package com.example.mausam.util

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T): NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null): NetworkResult<T>(data, message)
    class Loading<T>: NetworkResult<T>()

}
/*
* This is a sealed class called NetworkResult that is used to handle the result of a network request in a more organized way. It has three sub-classes: Success, Error, and Loading.

Success is used to indicate that the network request was successful and it contains the data received from the server.

Error is used to indicate that an error occurred during the network request and it contains an error message and the data received from the server (if any).

Loading is used to indicate that the network request is in progress.

Here's an example of how this class can be used in a network request:*/