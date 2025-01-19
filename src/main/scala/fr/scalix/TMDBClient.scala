package fr.scalix

import fr.scalix.cache.{Cache, CacheManager, FileCache, MemoryCache}
import org.json4s.*
import org.json4s.native.JsonMethods.*
import sttp.client4.{DefaultSyncBackend, UriContext, basicRequest}
import sttp.model.StatusCode

object TMDBClient {
  private val BASE_URL = Settings.TMDB.baseUrl
  private val API_KEY = Settings.TMDB.apiKey
  private val backend = DefaultSyncBackend()

  private val creditsFileCache = new CacheManager[String, JValue](new FileCache())
  private val actorIdCache = new MemoryCache[(String, String), Int]

  private def fetchApi(endpoint: String): JValue = {
    val url = s"$BASE_URL$endpoint"
    val uri = uri"$url"
    val response = basicRequest
      .get(uri)
      .header("Authorization", s"Bearer $API_KEY")
      .send(backend)

    response.code match {
      case StatusCode.Ok => response.body match {
        case Right(value) => parse(value)
        case Left(error) => throw new Exception(error)
      }
      case _ => throw new Exception(s"Error: ${response.code}")
    }
  }

  def searchPerson(query: String): JValue = {
    fetchApi(s"3/search/person?query=$query")
  }

  def getPersonMovieCredits(actorId: Int): JValue = {
    creditsFileCache.getOrFetch(s"actor$actorId") {
      fetchApi(s"3/person/$actorId/movie_credits")
    }
  }

  def getMovieCredits(movieId: Int): JValue = {
    creditsFileCache.getOrFetch(s"movie$movieId") {
      fetchApi(s"3/movie/$movieId/credits")
    }
  }

}
