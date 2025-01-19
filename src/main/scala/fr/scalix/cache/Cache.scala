package fr.scalix.cache

import scala.util.Try

trait Cache[K, V] {
  def get(key: K): Option[V]
  def set(key: K, value: V): Try[Unit]
}
