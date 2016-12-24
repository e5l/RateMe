package ru.spbau.mit.scala.rateme.client.pages

import scalatags.Text.all._

object LoadPage {
  val boot = "ru.spbau.mit.scala.rateme.client.LoadApplication().main()"
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
        h2("Photo load page"),

        div(
          input(
            id:="photo-link",
            `type`:="text",
            `class`:= "form-control"
          )
        ),

        div(
          a(href:="http://localhost:8080") (
            div(
              input(
                id:="load-button",
                `class`:="btn btn-primary",
                `type`:="submit",
                `value`:="Load Photo Link"
              )
            )
          )
        ),

        onload := boot
      )
    )
}
