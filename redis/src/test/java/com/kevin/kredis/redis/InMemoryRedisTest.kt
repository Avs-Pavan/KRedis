package com.kevin.kredis.redis

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test


class InMemoryRedisTest {

    private lateinit var redis: Redis<String, String>

    @Before
    fun setUp() {
        redis = InMemoryRedis(
            maxSize = 4,
            evictionPolicy = LruEvictionPolicy()
        )
    }


    @Test
    fun `test cache put and get`() {
        redis.put("1", "one")
        redis.put("2", "two")
        redis.put("3", "three")
        redis.put("4", "four")

        assertEquals("one", redis.get("1"))
        assertEquals("two", redis.get("2"))
        assertEquals("three", redis.get("3"))
        assertEquals("four", redis.get("4"))
    }

    @Test
    fun `test cache eviction`() {
        redis.put("1", "one")
        redis.put("2", "two")
        redis.put("3", "three")
        redis.put("4", "four")
        redis.put("5", "five") // This should trigger eviction

        assertNull(redis.get("1")) // "1" should be evicted
        assertEquals("two", redis.get("2"))
        assertEquals("three", redis.get("3"))
        assertEquals("four", redis.get("4"))
        assertEquals("five", redis.get("5"))
    }

    @Test
    fun `test cache access order`() {
        redis.put("1", "one")
        redis.put("2", "two")
        redis.put("3", "three")
        redis.put("4", "four")

        // Access "1" to make it recently used
        redis.get("1")

        // Add another item to trigger eviction
        redis.put("5", "five")

        // "2" should be evicted because "1" was accessed recently
        assertNull(redis.get("2"))
        assertEquals("one", redis.get("1"))
        assertEquals("three", redis.get("3"))
        assertEquals("four", redis.get("4"))
        assertEquals("five", redis.get("5"))
    }

    @Test
    fun `test getAll`() {
        redis.put("1", "one")
        redis.put("2", "two")
        redis.put("3", "three")

        val allEntries = redis.getAll()
        assertEquals(3, allEntries.size)
        assertEquals("one", allEntries["1"])
        assertEquals("two", allEntries["2"])
        assertEquals("three", allEntries["3"])
    }

    @Test
    fun `test remove`() {
        redis.put("1", "one")
        redis.put("2", "two")

        redis.remove("1")
        assertNull(redis.get("1"))
        assertEquals("two", redis.get("2"))
    }

    @Test
    fun `test exists`() {
        redis.put("1", "one")
        redis.put("2", "two")

        assertTrue(redis.exists("1"))
        assertFalse(redis.exists("3"))
    }

    @Test
    fun `test keys`() {
        redis.put("1", "one")
        redis.put("2", "two")
        redis.put("3", "three")

        val keys = redis.keys()
        assertEquals(3, keys.size)
        assertTrue(keys.contains("1"))
        assertTrue(keys.contains("2"))
        assertTrue(keys.contains("3"))
    }

    @Test
    fun `test size`() {
        redis.put("1", "one")
        redis.put("2", "two")

        assertEquals(2, redis.size())
    }

    @Test
    fun `test clear`() {
        redis.put("1", "one")
        redis.put("2", "two")

        redis.clear()
        assertEquals(0, redis.size())
        assertNull(redis.get("1"))
        assertNull(redis.get("2"))
    }
}