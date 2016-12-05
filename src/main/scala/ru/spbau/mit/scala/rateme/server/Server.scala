package ru.spbau.mit.scala.rateme.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.RootJsonFormat

import scala.io.StdIn

object Server extends App {
  implicit val system = ActorSystem("rateme-actor-system")
  implicit val materalizer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  implicit val signFormat: RootJsonFormat[SignRequest] = jsonFormat2(SignRequest)
  implicit val registerResponseFormat: RootJsonFormat[RegisterResponse] = jsonFormat1(RegisterResponse)
  implicit val loginResponseFormat: RootJsonFormat[LoginResponse] = jsonFormat3(LoginResponse)

  val domain = DomainModel.readOrCreate(Config.DB_PATH)

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
          entity(as[SignRequest]) { request =>
            complete(domain.register(request))
          }
        } ~
          path("login") {
            entity(as[SignRequest]) { request =>
              complete(domain.login(request))
            }
          }
      }

  val bindingFuture = Http().bindAndHandle(route, Config.URL, Config.PORT)
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

  DomainModel.save(domain, Config.DB_PATH)
}
