package com.kevin.kredis.redis

/**
 * A generic interface for a Redis-like key-value store.
 *
 * @param K the type of keys maintained by this store
 * @param V the type of mapped values
 */
interface Redis<K : Any, V : Any> {

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or `null` if no value is found
     */
    fun get(key: K): V?

    /**
     * Retrieves all key-value pairs in the store.
     *
     * @return a map containing all key-value pairs
     */
    fun getAll(): Map<K, V>

    /**
     * Associates the specified value with the specified key in the store.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    fun put(key: K, value: V)

    /**
     * Removes the mapping for a key from the store if it is present.
     *
     * @param key the key whose mapping is to be removed from the store
     */
    fun remove(key: K)

    /**
     * Checks if the store contains a mapping for the specified key.
     *
     * @param key the key whose presence in the store is to be tested
     * @return `true` if the store contains a mapping for the specified key, `false` otherwise
     */
    fun exists(key: K): Boolean

    /**
     * Retrieves a set of all keys in the store.
     *
     * @return a set of all keys
     */
    fun keys(): Set<K>

    /**
     * Returns the number of key-value mappings in the store.
     *
     * @return the number of key-value mappings
     */
    fun size(): Int

    /**
     * Removes all key-value mappings from the store.
     */
    fun clear()
}