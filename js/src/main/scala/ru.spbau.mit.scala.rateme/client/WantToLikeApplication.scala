package ru.spbau.mit.scala.rateme.client

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import org.scalajs.dom.html._
import ru.spbau.mit.scala.rateme.client.pages.models.{RequestLike, RequestPhotos, ResponsePhotos}

import scala.concurrent._
import ExecutionContext.Implicits.global
import upickle.default._

import scala.scalajs.js.JSApp


object WantToLikeApplication extends JSApp {

  var photos: ResponsePhotos = null

  def main(): Unit = {
    val leftButton = dom.document.getElementById("left-button")
      .asInstanceOf[html.Input]
    val rightButton = dom.document.getElementById("right-button")
      .asInstanceOf[html.Input]
    setupLikeBtn(leftButton, rightButton)
    val id = Integer.parseInt(dom.document.cookie)
    val data = write(RequestPhotos(id))
    Ajax.post("/IWantToLike", data, headers = Map("Content-Type" -> "application/json")).foreach { response =>
      photos = read[ResponsePhotos](response.responseText)
      dom.document.getElementById("left").asInstanceOf[Image].src = photos.firstPhoto
      dom.document.getElementById("right").asInstanceOf[Image].src = photos.secondPhoto
    }
  }

  def setupLikeBtn(leftButton: Input, rightButton: Input): Unit = {
    val id = Integer.parseInt(dom.document.cookie)

    leftButton.onclick = {
      (e: dom.MouseEvent) =>
        val likeData = write(RequestLike(id, photos.firstName))
        Ajax.post("/Like", likeData, headers = Map("Content-Type" -> "application/json"))
    }

    rightButton.onclick = {
      (e: dom.MouseEvent) =>
        val likeData = write(RequestLike(id, photos.secondName))
        Ajax.post("/Like", likeData, headers = Map("Content-Type" -> "application/json"))
    }
  }
}
