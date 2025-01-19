package fr.scalix

import fr.scalix.cache.{CacheManager, MemoryCache}
import org.json4s.native.JsonMethods.*
import org.json4s.*


object ScalixService {
  implicit val formats: DefaultFormats.type = DefaultFormats
  private val actorIdCache = new CacheManager[(String, String), Int](new MemoryCache)
  private val actorMoviesCache = new CacheManager[Int, Set[(Int, String)]](new MemoryCache)
  private val movieDirectorCache = new CacheManager[Int, Option[(Int, String)]](new MemoryCache)

  def findActorId(firstname: String, lastname: String): Option[ActorId] =
    actorIdCache.getOrFetchOption((firstname, lastname)) {
      extractActorId(TMDBClient.searchPerson(s"$firstname $lastname"))
    }

  def findActorMovies(actorId: Int): Set[(Int, String)] =
    actorMoviesCache.getOrFetch(actorId) {
      extractActorMovies(TMDBClient.getPersonMovieCredits(actorId))
    }

  def findMovieDirector(movieId: Int): Option[(Int, String)] = {
    movieDirectorCache.getOrFetch(movieId) {
      extractMovieDirector(TMDBClient.getMovieCredits(movieId))
    }
  }

  def collaboration(actor1: FullName, actor2: FullName): Set[(String, String)] = {
    val (firstname1, lastname1) = actor1
    val (firstname2, lastname2) = actor2

    val actorId1 = findActorId(firstname1, lastname1)
    val actorId2 = findActorId(firstname2, lastname2)

    (actorId1, actorId2) match {
      case (Some(id1), Some(id2)) =>
        val movies1 = findActorMovies(id1)
        val movies2 = findActorMovies(id2)
        movies1.intersect(movies2).map { case (id, title) =>
          val director = findMovieDirector(id)
          director match {
            case Some((_, name)) => (name, title)
            case None => ("Unknown", title)
          }
        }
      case _ => Set.empty
    }
  }

  private def extractActorId(bodyResult: JValue): Option[ActorId] = {
    ((bodyResult \ "results")(0) \ "id").extractOpt[Int]
  }


  private def extractActorMovies(bodyResult: JValue): Set[(Int, String)] = {
    (bodyResult \ "cast").children.map { movie =>
      val id = (movie \ "id").extract[Int]
      val title = (movie \ "title").extract[String]
      (id, title)
    }.toSet
  }

  private def extractMovieDirector(bodyResult: JValue): Option[(Int, String)] =
    (bodyResult \ "crew").children.find { crew =>
      (crew \ "job").extract[String] == "Director"
    } match {
      case Some(director) =>
        val id = (director \ "id").extract[Int]
        val name = (director \ "name").extract[String]
        Some((id, name))
      case None => None
    }

}
