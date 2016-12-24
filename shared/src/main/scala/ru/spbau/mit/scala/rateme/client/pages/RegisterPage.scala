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
          href:="https://maxcdn.bootstrapcdn.com/bootswatch/3.3.7/sandstone/bootstrap.min.css"
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
            a(href:="http://localhost:8080")(
              input(
                id:="register-button",
                `class`:="btn btn-primary",
                `type`:="submit",
                `value`:="Register"
              )
            )
          ),

          onload := boot
      )
    )
}
