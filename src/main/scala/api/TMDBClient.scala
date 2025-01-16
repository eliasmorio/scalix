package api

import sttp.client4.{DefaultSyncBackend, UriContext, basicRequest, quickRequest}
import sttp.model.StatusCode

object TMDBClient {
  private val BASE_URL = "https://api.themoviedb.org/"
  private val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMWFkZDJmYzZlMTYxNGQ5MGExNGFkY2YxMzgzMDJiYyIsIm5iZiI6MTczNjkyMzIyMy40MTYsInN1YiI6IjY3ODc1ODU3YjkwOTRjN2RmZWJiODRlNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8h_E9Dr_jvYewOLYtHfttqb8uayekgAj_Lvc49Riqds"
  private val backend = DefaultSyncBackend()

  private def fetchApi(endpoint: String): String = {
    val url = s"$BASE_URL$endpoint"
    println(url)
    val uri = uri"$url"
    val response = basicRequest
      .get(uri)
      .header("Authorization", s"Bearer $API_KEY")
      .send(backend)

    response.code match {
      case StatusCode.Ok => response.body match {
        case Right(value) => value
        case Left(error) => throw new Exception(s"Error: $error")
      }
      case _ => throw new Exception(s"Error: ${response.code}")
    }
  }

  def searchPerson(query: String): String = {
    fetchApi(s"3/search/person?query=$query")
  }

  def getActorMovies(actorId: Int): String = {
    fetchApi(s"3/person/$actorId/movie_credits")
  }

  def findMovieDirector(movieId: Int): String = {
    fetchApi(s"3/movie/$movieId/credits")
  }

}
