package ru.spbau.mit.scala.rateme.client.pages

import scalatags.Text.all._


object WantToLikePage {
  val boot = "ru.spbau.mit.scala.rateme.client.WantToLikeApplication().main()"
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
        h2("Like page"),

        div(`class` := "content")(
          img(id := "left", width := "400", height := "400", src := "https://i.stack.imgur.com/uguXk.jpg?s=328&g=1")
        ),

        div(
          a(href := "http://localhost:8080")(
            input(
              id := "left-button",
              `class` := "btn btn-primary",
              `type` := "submit",
              `value` := "Like"
            )
          )
        ),

        div(`class` := "content")(
          img(id := "right", width := "400", height := "400", src := "https://i.stack.imgur.com/uguXk.jpg?s=328&g=1")
        ),

        div(
          a(href := "http://localhost:8080")(
            input(
              id := "right-button",
              `class` := "btn btn-primary",
              `type` := "submit",
              `value` := "Like"
            )
          )
        ),

        onload := boot
      )
    )
}
