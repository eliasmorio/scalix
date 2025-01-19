package fr.scalix

import com.typesafe.config.{Config, ConfigFactory}

object Settings {
  private val config = ConfigFactory.load()

  object TMDB {
    val baseUrl: String = config.getString("tmdb.api.base-url")
    val apiKey: String = config.getString("tmdb.api.key")
  }

  object Cache {
    val cacheDirectory: String = config.getString("cache.base-dir")
  }
}
