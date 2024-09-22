# KRedis

KRedis is an in-memory key-value store implemented in Kotlin. It supports various eviction policies, including Least Recently Used (LRU).

## Features

- In-memory key-value store
- Configurable eviction policies
- Unlimited cache size by default
- Thread-safe operations

## Installation

To include KRedis in your project, add the following dependency to your `build.gradle` file:

```groovy
dependencies {
    implementation 'com.kevin.kredis:kredis:1.0.0'
}
```



## Usage

### Basic Usage

```kotlin
import com.kevin.kredis.redis.InMemoryRedis
import com.kevin.kredis.redis.LruEvictionPolicy

fun main() {
    val redis = InMemoryRedis<String, String>(
        evictionPolicy = LruEvictionPolicy(),
        maxSize = 100
    )

    redis.put("key1", "value1")
    println(redis.get("key1")) // Output: value1
}
```

### Unlimited Cache Size

```kotlin
val redis = InMemoryRedis<String, String>()
redis.put("key1", "value1")
println(redis.get("key1")) // Output: value1
```


