package ru.spbau.mit.scala.rateme.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import ru.spbau.mit.scala.rateme.client.pages.{LoginPage, RegisterPage}

import scala.concurrent.duration._
import ru.spbau.mit.scala.rateme.server.actors.{LikesActor, PhotosActor, SessionsActor, UsersActor}

import scala.io.StdIn

object Server extends App {
  implicit val system = ActorSystem("rateme-actor-system")
  implicit val timeout: Timeout = Timeout(5 seconds)
  implicit val materalizer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val users = system.actorOf(UsersActor.props)
  val sessions = system.actorOf(SessionsActor.props(users))
  val photos = system.actorOf(PhotosActor.props)
  val likes = system.actorOf(LikesActor.props)

  val dummy = HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>")
  println(s"Starting server on ${Config.PORT}")
  val route: Route =
    get {
      path("") {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      } ~
        path("register") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, RegisterPage.skeleton.render))
        } ~
        path("login") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, LoginPage.skeleton.render))
        } ~ getFromDirectory("./target/scala-2.11/")
    } ~
      post {
        path("register") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        } ~
          path("login") {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          } ~
          path("IWantToLike") {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          } ~
          path("Like") {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          } ~
          path("GetMyLikes") {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          } ~
          path("UploadPhoto") {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          }
      }

  val bindingFuture = Http().bindAndHandle(route, Config.URL, Config.PORT)
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
