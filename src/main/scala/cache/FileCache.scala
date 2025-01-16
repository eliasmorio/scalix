package cache

import org.json4s.*
import org.json4s.native.JsonMethods.*

import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.{Failure, Success, Try}

class FileCache[K](cacheDirectory: String) extends Cache[K, JValue] {

  private def getFileName(key: K): String = s"$cacheDirectory/${key.toString}.json"

  override def get(key: K): Option[JValue] = {
    val fileName = getFileName(key)
    val file = new File(fileName)

    if (file.exists()) {
      try {
        val contents = Source.fromFile(file).mkString
        Some(parse(contents))
      } catch {
        case e: Exception => None
      }
    } else {
      None
    }
  }

  override def set(key: K, value: JValue): Try[Unit] = {
    val fileName = getFileName(key)
    val file = new File(fileName)
    val writer = new PrintWriter(file)

    try {
      writer.write(compact(render(value)))
      Success(())
    } catch {
      case e: Exception => Failure(e)
    } finally {
      writer.close()
    }
  }
}
