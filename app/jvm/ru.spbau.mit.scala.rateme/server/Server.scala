package ru.spbau.mit.scala.rateme.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.pattern._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.util.Timeout
import ru.spbau.mit.scala.rateme.client.{LoginPage, RegisterPage}

import scala.concurrent.duration._
import ru.spbau.mit.scala.rateme.server.actors.SessionsActor.{LoginFailed, LoginRequest, LoginResponse, LoginSuccess}
import ru.spbau.mit.scala.rateme.server.actors.UsersActor.{RegisterFail, RegisterResponse, RegisterSuccess}
import ru.spbau.mit.scala.rateme.server.actors.{SessionsActor, UsersActor}
import ru.spbau.mit.scala.rateme.server.models._
import spray.json.RootJsonFormat

import scala.io.StdIn

object Server extends App {
  implicit val system = ActorSystem("rateme-actor-system")
  implicit val timeout: Timeout = Timeout(5 seconds)
  implicit val materalizer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  implicit val signFormat: RootJsonFormat[RequestSign] = jsonFormat2(RequestSign)
  implicit val registerResponseFormat: RootJsonFormat[ResponseRegister] = jsonFormat1(ResponseRegister)
  implicit val loginResponseFormat: RootJsonFormat[ResponseLogin] = jsonFormat3(ResponseLogin)

  val users = system.actorOf(UsersActor.props)
  val sessions = system.actorOf(SessionsActor.props(users))

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
          entity(as[RequestSign]) { request =>
            complete((users ? UsersActor.Register(request.login, request.password)).mapTo[RegisterResponse].map {
              case _: RegisterSuccess => ResponseRegister(true)
              case _: RegisterFail => ResponseRegister(false)
            })
          }
        } ~
          path("login") {
            entity(as[RequestSign]) { request =>
              complete((sessions ? LoginRequest(request.login, request.password)).mapTo[LoginResponse].map {
                case x: LoginSuccess => ResponseLogin(success = true, request.login, x.key)
                case _: LoginFailed => ResponseLogin(success = false)
              })
            }
          }
      }

  val bindingFuture = Http().bindAndHandle(route, Config.URL, Config.PORT)
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
