package ru.spbau.mit.scala.rateme.client.pages

import scalatags.Text.all._

object MainPage {
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
        h1("RateMe Application"),

        div(
          a(href:="http://localhost:8080/register") (
            p("Registration Page")
          ),
          a(href:="http://localhost:8080/login") (
            p("Login Page")
          )
          )
      )
    )
}
