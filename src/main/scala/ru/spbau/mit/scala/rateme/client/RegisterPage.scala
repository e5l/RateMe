package ru.spbau.mit.scala.rateme.client

import scalatags.Text.all._

object RegisterPage {
  val boot = ""
  val skeleton =
    html(
      head(
        link(
          rel:="stylesheet",
          href:="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
        ),
        link(
          rel:="stylesheet",
          href:="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
        )
      ),

      body(
          h2("Registration page"),

          div(
            input(
              id:="login-text",
              `type`:="text",
              `name`:="Login",
              `value`:="Сын собаки"
            )
          ),

          div(
            input(
              id:="password-text",
              `type`:="password"
            )
          ),

          div(
            input(
              id:="register-button",
              `type`:="submit",
              `value`:="Register"
            )
          ),

          onload := boot
      )
    )
}
