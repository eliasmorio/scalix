package fr.scalix.cache

import fr.scalix.Settings
import org.json4s.*
import org.json4s.native.JsonMethods.*

import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.{Failure, Success, Try, Using}


class FileCache extends Cache[String, JValue] {

  private def getFileName(key: String): String = {
    s"${Settings.Cache.cacheDirectory}/$key.json"
  }

  override def get(key: String): Option[JValue] = {
    val fileName = getFileName(key)
    val file = new File(fileName)

    if (file.exists()) {
      try {
        Using(Source.fromFile(file)) { source =>
          val jsonString = source.mkString
          parse(jsonString)
        }.toOption
      } catch {
        case e: Exception => None
      }
    } else {
      None
    }
  }

  override def set(key: String, value: JValue): Try[Unit] = {
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

  override def keys: Set[String] = {
    val directory = new File(Settings.Cache.cacheDirectory)
    if (directory.exists()) {
      directory.listFiles().map(_.getName).toSet
    } else {
      Set.empty
    }
  }

}
