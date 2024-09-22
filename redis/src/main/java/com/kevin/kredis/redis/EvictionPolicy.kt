package com.kevin.kredis.redis

import java.util.LinkedList
import java.util.Queue


interface EvictionPolicy<K> {
    fun onItemAccessed(key: K)
    fun onItemAdded(key: K)
    fun evict(): K?
}


class LruEvictionPolicy<K> : EvictionPolicy<K> {
    private val accessOrderMap = LinkedHashMap<K, Unit>(16, 0.75f, true)

    override fun onItemAdded(key: K) {
        accessOrderMap[key] = Unit
    }

    override fun onItemAccessed(key: K) {
        accessOrderMap[key] = Unit
    }

    override fun evict(): K? {
        val iterator = accessOrderMap.keys.iterator()
        return if (iterator.hasNext()) {
            val key = iterator.next()
            iterator.remove()
            key
        } else {
            null
        }
    }
}


class FifoEvictionPolicy<K> : EvictionPolicy<K> {
    private val queue: Queue<K> = LinkedList()

    override fun onItemAdded(key: K) {
        queue.add(key)
    }

    override fun onItemAccessed(key: K) {
        // No action needed for FIFO on access
    }

    override fun evict(): K? {
        return queue.poll()
    }
}