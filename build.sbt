ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
      name := "scalix",
      libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.7",
      libraryDependencies += "org.scala-lang" %% "toolkit" % "0.6.0",
      libraryDependencies += "com.typesafe" % "config" % "1.4.3"

  )
