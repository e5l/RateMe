package ru.spbau.mit.scala.rateme.server

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import ru.spbau.mit.scala.rateme.client.pages.models._
import spray.json.DefaultJsonProtocol

trait JsonFormatter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val requestSignFormat = jsonFormat2(RequestSign)
  implicit val requestUploadPhotoFormat = jsonFormat2(RequestUploadPhoto)
  implicit val requestPhotosFormat = jsonFormat1(RequestPhotos)
  implicit val requestLikeFormat = jsonFormat2(RequestLike)
  implicit val requestListLikesFormat = jsonFormat1(RequestListLikes)


  implicit val responseRegisterFormat = jsonFormat1(ResponseRegister)
  implicit val responseLoginFormat = jsonFormat3(ResponseLogin)
  implicit val responsePhotosFormat = jsonFormat4(ResponsePhotos)
  implicit val responseListLikesFormat = jsonFormat1(ResponseListLikes)
}


