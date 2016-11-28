package ru.spbau.mit.scala.rateme.client

import scalatags.Text.all._

object LoginPage {
  val skeleton =
    html(
      head(
        script("some script"),
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
        h1("This is my title"),
        div(
          p("This is my first paragraph"),
          p("This is my second paragraph")
        )
      )
    )
}
