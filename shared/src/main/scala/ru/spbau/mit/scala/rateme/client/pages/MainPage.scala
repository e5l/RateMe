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
          href:="https://maxcdn.bootstrapcdn.com/bootswatch/3.3.7/sandstone/bootstrap.min.css"
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
          ),
          a(href:="http://localhost:8080/load") (
            p("Load Page")
          ),
          a(href:="http://localhost:8080/like") (
            p("Like Someone")
          )
          )
      )
    )
}
