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

  def main(): Unit = {
    val leftButton = dom.document.getElementById("left-button")
      .asInstanceOf[html.Input]
    val rightButton = dom.document.getElementById("right-button")
      .asInstanceOf[html.Input]
    setupLikeBtn(leftButton, rightButton)
    // TODO: add session id
    val data = write(RequestPhotos(1))
    Ajax.post("/IWantToLike", data, headers = Map("Content-Type" -> "application/json")).foreach { response =>
      val result = read[ResponsePhotos](response.responseText)
      print(result)
    }
  }

  def setupLikeBtn(leftButton: Input, rightButton: Input): Unit = {
    leftButton.onclick = {
      (e: dom.MouseEvent) =>
        // TODO: valid input
        val likeData = write(RequestLike(1, ""))
        Ajax.post("/Like", likeData, headers = Map("Content-Type" -> "application/json"))
    }

    rightButton.onclick = {
      (e: dom.MouseEvent) =>
        // TODO: valid input
        val likeData = write(RequestLike(1, ""))
        Ajax.post("/Like", likeData, headers = Map("Content-Type" -> "application/json"))
    }
  }
}
