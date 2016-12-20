package ru.spbau.mit.scala.rateme.client

import org.scalajs.dom
import org.scalajs.dom.experimental.ResponseType
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import org.scalajs.dom.html.Input
import ru.spbau.mit.scala.rateme.client.pages.models.{RequestSign, ResponseRegister}
import org.scalajs.jquery._
import ru.spbau.mit.scala.rateme.client.utils.ojax

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.{JSApp, JSON}
import upickle.default._

object RegisterApplication extends JSApp {

  def main(): Unit = {
    val loginField = dom.document.getElementById("login-text")
      .asInstanceOf[html.Input]
    val passwordField = dom.document.getElementById("password-text")
      .asInstanceOf[html.Input]
    val registerButton = dom.document.getElementById("register-button")
      .asInstanceOf[html.Input]
    setupSubmitBtn(registerButton, loginField, passwordField)
  }

  def setupSubmitBtn(registerButton: Input, loginField: Input, passwordField : Input): Unit = {
    registerButton.onclick = {
      (e: dom.MouseEvent) =>
        val registerData = write(RequestSign(loginField.value, passwordField.value))

        Ajax.post("/register", registerData, headers = Map("Content-Type" -> "application/json")).foreach { response =>
          val result = read[ResponseRegister](response.responseText)
          println(result)
        }
    }
  }

  def registerSuccess(data: js.Any, textStatus: String, jqXHR: JQueryXHR): js.Dynamic = null
}
