package ru.spbau.mit.scala.rateme.client

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import org.scalajs.dom.html.Input
import ru.spbau.mit.scala.rateme.client.pages.models.RequestSign

import scala.scalajs.js.{JSApp, JSON}


object RegisterApplication extends JSApp {

  override def main(): Unit = {
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
        val registerData = RequestSign(loginField.value, passwordField.value)
        Ajax.post(
          "/register",
          data = ""
        )
    }
  }
}
