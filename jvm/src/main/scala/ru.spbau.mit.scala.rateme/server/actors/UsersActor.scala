package ru.spbau.mit.scala.rateme.server.actors

import akka.actor.Props
import akka.event.Logging
import akka.persistence.{PersistentActor, RecoveryCompleted}
import ru.spbau.mit.scala.rateme.server.actors.UsersActor._
import ru.spbau.mit.scala.rateme.client.pages.models.{RequestSign, ResponseRegister, User}

import scala.collection.mutable

object UsersActor {

  case class Register(request: RequestSign)

  case class Auth(request: RequestSign)

  def props: Props = Props(new UsersActor())
}

class UsersActor extends PersistentActor {
  override def persistenceId: String = "UsersId"

  val log = Logging(context.system, this)
  val users: mutable.Map[String, User] = mutable.Map[String, User]()

  override def receiveCommand: Receive = {
    case Register(request) => persist(Register(request))(x => sender ! register(request.login, request.password))
    case Auth(request) => sender ! auth(request.login, request.password)
    case x => log.warning(s"Unknown request: $x")
  }

  override def receiveRecover: Receive = {
    case Register(request) => register(request.login, request.password)
    case RecoveryCompleted => log.debug("Recovery completed")
    case x => log.error(s"Recover WHAT?: $x")
  }

  private def register(name: String, password: String): ResponseRegister = if (users.contains(name)) {
    ResponseRegister(false)
  } else {
    users(name) = User(name, password, List())
    ResponseRegister(true)
  }

  private def auth(name: String, password: String): User =
    if (users.contains(name) && users(name).password == password) users(name) else null
}
