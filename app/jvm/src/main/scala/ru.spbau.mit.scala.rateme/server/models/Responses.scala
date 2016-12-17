package ru.spbau.mit.scala.rateme.server.models

final case class ResponseLogin(success: Boolean, login: String = "", sessionKey: Int = -1)
final case class ResponseRegister(success: Boolean)
final case class ResponsePhotos(firstName: String, secondName: String, firstPhoto: String, secondPhoto: String)
final case class ResponseListLikes(users: Array[String])

