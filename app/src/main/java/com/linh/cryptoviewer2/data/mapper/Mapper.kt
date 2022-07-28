package com.linh.cryptoviewer2.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}