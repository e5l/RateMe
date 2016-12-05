package ru.spbau.mit.scala.rateme.server.actors

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging
import ru.spbau.mit.scala.rateme.server.actors.SessionsActor.{LoginRequest, SessionRequest}
import ru.spbau.mit.scala.rateme.server.models.User

import scala.collection.mutable

object SessionsActor {
  case class LoginRequest(username: String, password: String)
  case class SessionRequest(key: String)

  sealed abstract class LoginResponse
  case class LoginSuccess(key: String) extends LoginResponse
  case class LoginFailed() extends LoginResponse

  sealed abstract class SessionResponse
  case class SessionExists(user: User) extends SessionResponse
  case class SessionNotExists() extends SessionResponse
}

class SessionsActor extends Actor {
  val sessions: mutable.Map[String, User] = mutable.Map[String, User]()
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case LoginRequest(username, password) => ???
    case SessionRequest(key) => ???
    case x => log.warning(s"Unknown request: $x")
  }
}
