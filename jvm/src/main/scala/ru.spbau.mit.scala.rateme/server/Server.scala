package ru.spbau.mit.scala.rateme.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import akka.pattern._
import ru.spbau.mit.scala.rateme.client.pages.models._
import ru.spbau.mit.scala.rateme.client.pages.{LoginPage, RegisterPage}
import ru.spbau.mit.scala.rateme.server.actors.SessionsActor.{LoginRequest, SessionRequest}
import ru.spbau.mit.scala.rateme.server.actors.UsersActor.{Like, Likes, Register, UploadPhoto}

import scala.concurrent.duration._
import ru.spbau.mit.scala.rateme.server.actors.{SessionsActor, UsersActor}
import spray.json.JsValue

import scala.concurrent.{CanAwait, Future}
import scala.io.StdIn

object Server extends App with JsonFormatter {
  implicit val system = ActorSystem("rateme-actor-system")
  implicit val timeout: Timeout = Timeout(5 seconds)
  implicit val duration: Duration = Duration.Inf
  implicit val materalizer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val users = system.actorOf(UsersActor.props)
  val sessions = system.actorOf(SessionsActor.props(users))

  val dummy = HttpEntity(ContentTypes.`application/json`, "<h1>Say hello to akka-http</h1>")
  val fail = HttpEntity(ContentTypes.`application/json`, "")
  val success = HttpEntity(ContentTypes.`application/json`, """{"status":"ok"}""")

  private def checkSession(key: Integer): Future[User] = (sessions ? SessionRequest(key)).mapTo[User]

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
            entity(as[RequestPhotos]) { request =>
              complete(dummy)
            }
          } ~
          path("Like") {
            entity(as[RequestLike]) { request =>
              val response = checkSession(request.key).map(user => {
                if (user == null) fail
                else {
                  users ? Like(request, user)
                  success
                }
              })

              complete(response)
            }
          } ~
          path("GetMyLikes") {
            entity(as[RequestListLikes]) { request =>
              val response = checkSession(request.key).map(user => (users ? Likes(user)).mapTo[ResponseListLikes])
              complete(response)
            }
          } ~
          path("UploadPhoto") {
            entity(as[RequestUploadPhoto]) { request =>
              val response = checkSession(request.key).map(user => {
                if (user == null) fail
                else {
                  users ? UploadPhoto(request, user)
                  success
                }
              })

              complete(response)
            }
          }
      }

  val bindingFuture = Http().bindAndHandle(route, Config.URL, Config.PORT)
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
