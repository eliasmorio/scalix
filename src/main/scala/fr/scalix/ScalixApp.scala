package fr.scalix

import fr.scalix.cache.InMemoryCache
import org.json4s.native.JsonMethods.*
import org.json4s.*

import scala.util.Using


object ScalixApp {

  def main(args: Array[String]): Unit =
    println(ScalixService.collaboration(("Tom", "Hanks"), ("Tom", "Cruise")))

//    println(ScalixService.collaboration(("Robert", "De Niro"), ("Al", "Pacino")))
//
//    println(ScalixService.collaboration(("Tom", "Hanks"), ("Al", "Pacino")))
//
//    println(ScalixService.collaboration(("Tom", "Hanks"), ("Robert", "De Niro")))
//
//    println(ScalixService.collaboration(("Tom", "Hanks"), ("Tom", "Hanks")))
//    println(ScalixService.collaboration(("Marlon", "Brando"), ("Denzel", "Washington")))
//    println(ScalixService.collaboration(("Sydney", "Poitier"), ("Denzel", "Washington")))
//    println(ScalixService.collaboration(("Ingrid", "Bergman"), ("Humphrey", "Bogart")))
//    println(ScalixService.collaboration(("Kate", "Winslet"), ("Humphrey", "Bogart")))
//    println(ScalixService.collaboration(("Leonardo", "Dicaprio"), ("Kate", "Winslet")))
//    println(ScalixService.collaboration(("Meryl", "Streep"), ("Tom", "Hanks")))
//    println(ScalixService.collaboration(("Meryl", "Streep"), ("Robert", "De Niro")))
//    println(ScalixService.collaboration(("Meryl", "Streep"), ("Al", "Pacino")))
//    println(ScalixService.collaboration(("Meryl", "Streep"), ("Meryl", "Streep")))



}
