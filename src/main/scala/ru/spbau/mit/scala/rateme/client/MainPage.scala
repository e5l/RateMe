package ru.spbau.mit.scala.rateme.client

import scalatags.Text.all._

object MainPage {
  val skeleton =
    html(
      head(
        script("some script")
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
