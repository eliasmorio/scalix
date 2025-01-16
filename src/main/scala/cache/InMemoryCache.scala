package cache

import scala.util.Try

class InMemoryCache[K, V] extends Cache[K, V] {
  private var cache = Map.empty[K, V]

  override def get(key: K): Option[V] = cache.get(key)

  override def set(key: K, value: V): Try[Unit] = {
    cache += (key -> value)
    Try(())
  }
}
