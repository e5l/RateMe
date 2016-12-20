package ru.spbau.mit.scala.rateme.client.pages

import scalatags.Text.all._

object RegisterPage {
  val boot = "ru.spbau.mit.scala.rateme.client.RegisterApplication().main()"
  val skeleton =
    html(
      head(
        script(src:="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"),
        script(src:="/app-fastopt.js"),
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
              `value`:=""
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
