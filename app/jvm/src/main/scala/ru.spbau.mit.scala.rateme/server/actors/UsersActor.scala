package ru.spbau.mit.scala.rateme.server.actors

import akka.actor.Props
import akka.event.Logging
import akka.persistence.{PersistentActor, RecoveryCompleted}
import ru.spbau.mit.scala.rateme.server.actors.UsersActor._
import ru.spbau.mit.scala.rateme.server.models.User

import scala.collection.mutable

object UsersActor {
  case class Register(username: String, password: String)
  case class Auth(username: String, password: String)

  sealed trait RegisterResponse
  case class RegisterSuccess() extends RegisterResponse
  case class RegisterFail() extends RegisterResponse

  sealed trait AuthResponse
  case class AuthSuccess(user: User) extends AuthResponse
  case class AuthFail() extends AuthResponse

  def props: Props = Props(new UsersActor())
}

class UsersActor extends PersistentActor {
  override def persistenceId: String = "UsersId"

  val log = Logging(context.system, this)
  val users: mutable.Map[String, User] = mutable.Map[String, User]()

  override def receiveCommand: Receive = {
    case x: Register => persist(x)(x => sender ! register(x.username, x.password))
    case Auth(name, password) => sender ! auth(name, password)
    case x => log.warning(s"Unknown request: $x")
  }

  override def receiveRecover: Receive = {
    case Register(name, password) => register(name, password)
    case RecoveryCompleted => log.debug("Recovery completed")
    case x => log.error(s"Recover WHAT?: $x")
  }

  private def register(name: String, password: String): RegisterResponse = if (users.contains(name)) {
    RegisterFail()
  } else {
    users(name) = User(name, password, List())
    RegisterSuccess()
  }

  private def auth(name: String, password: String): AuthResponse = if (users.contains(name) && users(name).password == password) {
    AuthSuccess(users(name))
  } else {
    AuthFail()
  }
}
