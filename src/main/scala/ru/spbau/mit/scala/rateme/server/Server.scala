package ru.spbau.mit.scala.rateme.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.Done
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.io.StdIn


object Config {
  val PORT = 8080
  val URL = "0.0.0.0"
}

object Server extends App {
  implicit val system = ActorSystem("rateme-actor-system")
  implicit val materalizer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  implicit val signFormat = jsonFormat2(DomainModel.SignRequest)

  println(s"Starting server on ${Config.PORT}")

  val route: Route =
    get {
      path("") {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      } ~
        path("register") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        } ~
        path("login") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
    } ~
      post {
        path("register") {
          entity(as[DomainModel.SignRequest]) { request =>
            println(s"Register request: $request")
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          }
        } ~
          path("login") {
            //            entity(as[DomainModel.SignRequest]) { request =>
            //              println(s"Register request: $request")
            //            }
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          }
      }

  val bindingFuture = Http().bindAndHandle(route, Config.URL, Config.PORT)
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
