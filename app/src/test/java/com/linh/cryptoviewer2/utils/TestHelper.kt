package com.linh.cryptoviewer2.utils

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object TestHelper {
    fun randomString() = UUID.randomUUID().toString()
    fun randomFloat() = ThreadLocalRandom.current().nextFloat()
    fun randomDouble() = ThreadLocalRandom.current().nextDouble()
    fun randomInt() = ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    fun randomLong() = ThreadLocalRandom.current().nextLong(1000 + 1)
    fun randomBoolean() = ThreadLocalRandom.current().nextBoolean()
}