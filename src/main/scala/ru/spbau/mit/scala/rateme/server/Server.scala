package ru.spbau.mit.scala.rateme.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object Config {
  val PORT = 8080
}

object Server extends App {
  implicit val system = ActorSystem("rateme-actor-system")
  implicit val materalizer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  println(s"Starting server on ${Config.PORT}")
}
