package ru.spbau.mit.scala.rateme.client

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import org.scalajs.dom.html._
import ru.spbau.mit.scala.rateme.client.pages.models.{RequestSign, RequestUploadPhoto, ResponseRegister}
import upickle.default._

import scala.scalajs.js.JSApp


object LoadApplication extends JSApp {

  def main(): Unit = {
    val photoField = dom.document.getElementById("photo-link")
      .asInstanceOf[html.Input]
    val loadButton = dom.document.getElementById("load-button")
      .asInstanceOf[html.Input]
    setupLoadBtn(loadButton, photoField)
  }

  def setupLoadBtn(loadButton: Input, photoField: Input): Unit = {
    loadButton.onclick = {
      (e: dom.MouseEvent) =>
        // TODO: get right session id
        val photoData = write(RequestUploadPhoto(1, photoField.value))
        Ajax.post("/UploadPhoto", photoData, headers = Map("Content-Type" -> "application/json"))
    }
  }
}
