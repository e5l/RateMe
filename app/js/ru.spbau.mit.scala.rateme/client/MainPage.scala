package ru.spbau.mit.scala.rateme.client

import scalatags.Text.all._

object MainPage {
  val skeleton =
    html(
      head(
        script(src:="/rateme-fastopt.js"),
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
        h1("This is main page")
      )
    )
}
