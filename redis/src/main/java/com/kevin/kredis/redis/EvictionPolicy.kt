package com.kevin.kredis.redis


interface EvictionPolicy<K> {
    fun onItemAccessed(key: K)
    fun onItemAdded(key: K)
    fun evict(): K?
}

class LruEvictionPolicy<K> : EvictionPolicy<K> {
    private val keyList = mutableListOf<K>()

    override fun onItemAccessed(key: K) {
        keyList.remove(key)
        keyList.add(0, key)
    }

    override fun onItemAdded(key: K) {
        keyList.add(0, key)
    }

    override fun evict(): K? {
        return keyList.removeAt(keyList.size - 1)
    }
}

class FifoEvictionPolicy<K> : EvictionPolicy<K> {
    private val keyList = mutableListOf<K>()

    override fun onItemAccessed(key: K) {
    }

    override fun onItemAdded(key: K) {
        keyList.add(key)
    }

    override fun evict(): K? {
        return keyList.removeAt(0)
    }
}