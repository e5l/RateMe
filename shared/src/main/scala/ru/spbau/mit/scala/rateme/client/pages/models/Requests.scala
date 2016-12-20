package ru.spbau.mit.scala.rateme.client.pages.models

/* User API */
final case class RequestSign(login: String, password: String)
final case class RequestUploadPhoto(key: Int, photoUrl: String)

/* Photos API */
final case class RequestPhotos(key: Int)
final case class RequestLike(key: Int, login: String)
final case class RequestListLikes(key: Int)
