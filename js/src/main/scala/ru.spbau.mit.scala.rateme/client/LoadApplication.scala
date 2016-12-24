package ru.spbau.mit.scala.rateme.client

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import org.scalajs.dom.html._
import ru.spbau.mit.scala.rateme.client.pages.models._
import upickle.default._

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.scalajs.js.JSApp
import upickle.default._

object LoadApplication extends JSApp {

  def main(): Unit = {
    val photoField = dom.document.getElementById("photo-link")
      .asInstanceOf[html.Input]
    val loadButton = dom.document.getElementById("load-button")
      .asInstanceOf[html.Input]
    setupLoadBtn(loadButton, photoField)
    val username = dom.document.cookie.substring(dom.document.cookie.indexOf("=") + 1)
    val data = write(RequestListLikes(Integer.parseInt(username)))
    Ajax.post("/GetMyLikes", data, headers = Map("Content-Type" -> "application/json")).foreach { response =>
      val likesData = read[ResponseListLikes](response.responseText)
      dom.document.getElementById("photo-src").asInstanceOf[Image].src = likesData.photo
      dom.document.getElementById("likes").asInstanceOf[Input].value = "Likes = " + Integer.toString(likesData.users.size)
    }
  }

  def setupLoadBtn(loadButton: Input, photoField: Input): Unit = {
    loadButton.onclick = {
      (e: dom.MouseEvent) =>
        val username = dom.document.cookie.substring(dom.document.cookie.indexOf("=") + 1)
        val id = Integer.parseInt(username)
        println("RequestUpload: ", id, photoField.value)
        val photoData = write(RequestUploadPhoto(id, photoField.value))
        dom.document.getElementById("photo-src").asInstanceOf[Image].src = photoField.value
        Ajax.post("/UploadPhoto", photoData, headers = Map("Content-Type" -> "application/json"))
    }
  }
}
