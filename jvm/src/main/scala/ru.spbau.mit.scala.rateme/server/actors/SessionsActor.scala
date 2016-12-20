package ru.spbau.mit.scala.rateme.server.actors

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern._
import akka.event.Logging
import akka.util.Timeout
import ru.spbau.mit.scala.rateme.server.actors.SessionsActor._
import ru.spbau.mit.scala.rateme.server.actors.UsersActor.{Auth, AuthFail, AuthResponse, AuthSuccess}
import ru.spbau.mit.scala.rateme.client.pages.models.{RequestSign, ResponseLogin, User}

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.concurrent.duration._

object SessionsActor {
  case class LoginRequest(request: RequestSign)
  case class SessionRequest(key: Int)

  sealed abstract class SessionResponse
  case class SessionExists(user: User) extends SessionResponse
  case class SessionNotExists() extends SessionResponse

  def props(userProps: ActorRef): Props = Props(new SessionsActor(userProps))
}

class SessionsActor(val users: ActorRef) extends Actor {
  implicit val timeout: Timeout = Timeout(5 seconds)
  implicit val executionContext: ExecutionContextExecutor = ExecutionContext.global

  val log = Logging(context.system, this)
  val sessions: mutable.Map[Int, User] = mutable.Map[Int, User]()

  def login(request: RequestSign): Future[ResponseLogin] =
    (users ? Auth(request)).mapTo[User].map[ResponseLogin]({ x =>
      if (x == null) ResponseLogin(success = false)
      else ResponseLogin(success = true, x.login, generateKey(x))
    })

  private def generateKey(user: User): Int = {
    val key = sessions.size
    sessions(key) = user
    key
  }

  private def session(key: Int): SessionResponse = {
    if (sessions.contains(key)) {
      SessionExists(sessions(key))
    } else {
      SessionNotExists()
    }
  }

  override def receive: Receive = {
    case LoginRequest(request) => pipe(login(request)) to sender()
    case SessionRequest(key) => sender() ! session(key)
    case x => log.error(s"Unknown request: $x")
  }
}
