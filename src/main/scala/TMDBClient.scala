import cache.{Cache, FileCache, InMemoryCache}
import org.json4s.*
import org.json4s.native.JsonMethods.*
import sttp.client4.{DefaultSyncBackend, UriContext, basicRequest}
import sttp.model.StatusCode

object TMDBClient {
  private val BASE_URL = "https://api.themoviedb.org/"
  private val API_KEY = ""
  private val backend = DefaultSyncBackend()

  private val creditsFileCache = new FileCache()
  private val actorIdCache = new InMemoryCache[(String, String), Int]

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

  private def fetchWithCache[K, V](cache: Cache[K, V], key: K, fetchFunc: => V): V = {
    cache.get(key) match {
      case Some(value) => value
      case None =>
        val result = fetchFunc
        cache.set(key, result)
        result
    }
  }

  def searchPerson(query: String): JValue = {
    fetchApi(s"3/search/person?query=$query")
  }

  def getPersonMovieCredits(actorId: Int): JValue = {
    fetchWithCache(
      creditsFileCache,
      s"actor$actorId",
      fetchApi(s"3/person/$actorId/movie_credits")
    )
  }

  def getMovieCredits(movieId: Int): JValue = {
    fetchWithCache(
      creditsFileCache,
      s"movie$movieId",
      fetchApi(s"3/movie/$movieId/credits")
    )
  }

}
