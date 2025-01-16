

object ScalixApp extends App {

  def main(args: Array[String]): Unit = {
    println("Hello, Scalix!")
  }

  def findActorId(firstname: String, lastname: String): Option[Int] = {
    return None
  }

  def findActorMovies(actorId: Int): Set[(Int, String)] = {
    return Set()
  }

  def findMovieDirector(movieId: Int): Option[(Int, String)] = {
    return None
  }

  def collaboration(actor1: Fullname, actor2: Fullname): Set[(String, String)] = {
    return Set()
  }



}
