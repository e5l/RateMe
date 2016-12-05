package ru.spbau.mit.scala.rateme.server.models

final case class User(login: String, password: String, photos: List[String])
