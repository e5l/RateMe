package ru.spbau.mit.scala.rateme.server.actors

import akka.event.Logging
import akka.persistence.PersistentActor
import ru.spbau.mit.scala.rateme.server.actors.UsersActor._
import ru.spbau.mit.scala.rateme.server.models.User

import scala.collection.mutable

object UsersActor {
  case class Register(username: String, password: String)
  case class Auth(username: String, password: String)

  sealed abstract class RegisterResponse
  case class RegisterSuccess() extends RegisterResponse
  case class RegisterFail() extends RegisterResponse

  sealed abstract class AuthResponse
  case class AuthSuccess(user: User) extends AuthResponse
  case class AuthFail() extends AuthResponse
}

class UsersActor extends PersistentActor {
  val log = Logging(context.system, this)
  override def persistenceId: String = "UsersId"

  val users: mutable.Map[String, User] = mutable.Map[String, User]()

  override def receiveCommand: Receive = {
    case Register(name, password) =>
      if (users.contains(name)) {
        sender() ! RegisterFail()
      } else {
        users(name) = User(name, password, List())
        sender() ! RegisterSuccess()
      }

    case Auth(name, password) =>
      if (users.contains(name) && users(name).password == password) {
        sender() ! AuthSuccess(users(name))
      } else {
        sender() ! AuthFail()
      }

    case x => log.warning(s"Unknown request: $x")
  }

  override def receiveRecover: Receive = ???
}
