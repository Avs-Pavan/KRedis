package com.kevin.kredis.redis

import java.util.concurrent.ConcurrentHashMap

class InMemoryRedis<K : Any, V : Any>(
    private val evictionPolicy: EvictionPolicy<K> = LruEvictionPolicy(),
    private val maxSize: Int = Int.MAX_VALUE
) : Redis<K, V> {

    private val cacheMap = ConcurrentHashMap<K, V>()

    override fun get(key: K): V? {
        evictionPolicy.onItemAccessed(key)
        return cacheMap[key]
    }

    override fun getAll(): Map<K, V> {
        return cacheMap
    }

    override fun put(key: K, value: V) {
        if (cacheMap.size >= maxSize) {
            val evictKey = evictionPolicy.evict()
            if (evictKey != null) {
                cacheMap.remove(evictKey)
            }
        }
        cacheMap[key] = value
        evictionPolicy.onItemAdded(key)
    }

    override fun remove(key: K) {
        cacheMap.remove(key)
    }

    override fun exists(key: K): Boolean {
        return cacheMap.containsKey(key)
    }

    override fun keys(): Set<K> {
        return cacheMap.keys
    }

    override fun size(): Int {
        return cacheMap.size
    }

    override fun clear() {
        cacheMap.clear()
    }
}