package com.kevin.kredis

import com.kevin.kredis.redis.InMemoryRedis
import com.kevin.kredis.redis.LruEvictionPolicy
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun testCache() {

        val redis = InMemoryRedis<String, String>(
            maxSize = 4,
            evictionPolicy = LruEvictionPolicy()
        )

        redis.put("1", "one")
        redis.put("2", "two")
        redis.put("3", "three")
        redis.put("4", "four")

        redis.getAll().entries.forEach {
            println(it)
        }

        redis.put("5", "five")

        redis.getAll().entries.forEach {
            println(it)
        }
        redis.put("6", "six")

        redis.getAll().entries.forEach {
            println(it)
        }

    }
}