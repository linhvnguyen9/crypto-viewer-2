package com.linh.cryptoviewer2.util

interface Mapper<I, O> {
    fun map(input: I): O
}