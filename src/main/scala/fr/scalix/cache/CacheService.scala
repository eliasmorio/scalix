package fr.scalix.cache

class CacheService[K, V] (cache: Cache[K, V]) {

  def getOrFetch(key: K)(fetchFunc: => V): V =
    cache.get(key).getOrElse {
      val result = fetchFunc
      cache.set(key, result)
      result
    }


  def getOrFetchOption(key: K)(fetch: => Option[V]): Option[V] =
    cache.get(key).orElse {
      fetch match {
        case Some(value) =>
          cache.set(key, value)
          Some(value)
        case None => None
      }
    }

}
