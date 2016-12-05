package ru.spbau.mit.scala.rateme.client

import scala.scalajs.js.{JSApp, JSON}
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import org.scalajs.dom.html.Input


object RateApplication extends JSApp {

  override def main(): Unit = {
    println("sad")
    val loginField = dom.document.getElementById("login-text")
      .asInstanceOf[html.Input]
    val registerButton = dom.document.getElementById("register-button")
      .asInstanceOf[html.Input]
    setupSubmitBtn(registerButton, loginField)
  }

  def setupSubmitBtn(registerButton: Input, loginField: Input): Unit = {
    registerButton.onclick = {
      (e: dom.MouseEvent) =>
        val description = loginField.value
        println("register-button")
        Ajax.post(
          "/register",
          data = JSON.stringify(description)
        )
    }
  }
}
