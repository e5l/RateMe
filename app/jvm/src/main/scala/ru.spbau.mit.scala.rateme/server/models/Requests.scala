package ru.spbau.mit.scala.rateme.server.models

final case class RequestSign(login: String, password: String)

final case class RequestUploadPhoto(key: Int, photoUrl: String)

final case class SessionRequest(key: Int)

final case class RequestLike(key: Int, login: String, photo: String)