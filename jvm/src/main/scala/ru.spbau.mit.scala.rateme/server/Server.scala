package ru.spbau.mit.scala.rateme.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import akka.pattern._
import ru.spbau.mit.scala.rateme.client.pages.models.{RequestSign, ResponseLogin, ResponseRegister}
import ru.spbau.mit.scala.rateme.client.pages.{LoginPage, RegisterPage}
import ru.spbau.mit.scala.rateme.server.actors.SessionsActor.LoginRequest
import ru.spbau.mit.scala.rateme.server.actors.UsersActor.{Auth, Register, RegisterResponse}

import scala.concurrent.duration._
import ru.spbau.mit.scala.rateme.server.actors.{LikesActor, PhotosActor, SessionsActor, UsersActor}

import scala.io.StdIn

object Server extends App with JsonFormatter {
  implicit val system = ActorSystem("rateme-actor-system")
  implicit val timeout: Timeout = Timeout(5 seconds)
  implicit val materalizer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val users = system.actorOf(UsersActor.props)
  val sessions = system.actorOf(SessionsActor.props(users))
  //  val photos = system.actorOf(PhotosActor.props)
  //  val likes = system.actorOf(LikesActor.props)

  val dummy = HttpEntity(ContentTypes.`application/json`, "<h1>Say hello to akka-http</h1>")
  println(s"Starting server on ${Config.PORT}")
  val route: Route =
    get {
      path("") {
        complete(dummy)
      } ~
        path("register") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, RegisterPage.skeleton.render))
        } ~
        path("login") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, LoginPage.skeleton.render))
        } ~ getFromDirectory("js/target/scala-2.11/")
    } ~
      post {
        path("register") {
          entity(as[RequestSign]) { request =>
            val response = (users ? Register(request)).mapTo[ResponseRegister]
            complete(response)
          }
        } ~
          path("login") {
            entity(as[RequestSign]) { request =>
              val response = (sessions ? LoginRequest(request)).mapTo[ResponseLogin]
              complete(response)
            }
          } ~
          path("IWantToLike") {
            complete(dummy)
          } ~
          path("Like") {
            complete(dummy)
          } ~
          path("GetMyLikes") {
            complete(dummy)
          } ~
          path("UploadPhoto") {
            complete(dummy)
          }
      }

  val bindingFuture = Http().bindAndHandle(route, Config.URL, Config.PORT)
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
