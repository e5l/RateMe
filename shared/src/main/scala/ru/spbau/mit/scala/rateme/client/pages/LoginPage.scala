package ru.spbau.mit.scala.rateme.client.pages

import scalatags.Text.all._

object LoginPage {
  val boot = "ru.spbau.mit.scala.rateme.client.LoginApplication().main()"
  val skeleton =
    html(
      head(
        script(src := "https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"),
        script(src := "/app-fastopt.js"),
        link(
          rel := "stylesheet",
          href := "https://maxcdn.bootstrapcdn.com/bootswatch/3.3.7/sandstone/bootstrap.min.css"
        )
      ),

      body(
        h2("Login page"),

        div(
          input(
            id := "login-text",
            `type` := "text",
            `name` := "Login",
            `value` := ""
          )
        ),

        div(
          input(
            id := "password-text",
            `type` := "password"
          )
        ),

        div(
          input(
            id := "login-button",
            `type` := "submit",
            `class` := "btn btn-primary",
            `value` := "Login"
          )
        ),

        onload := boot
      )
    )
}
