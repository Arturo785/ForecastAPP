package com.example.forecastapp.internal

import kotlinx.coroutines.*

//deferred is a job that has a result
fun <T> lazyDeferred(block : suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this) // invokes and does the fun or the code that receives as an argument
        }
    }
}