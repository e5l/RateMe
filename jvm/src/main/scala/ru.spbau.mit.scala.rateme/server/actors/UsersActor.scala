package ru.spbau.mit.scala.rateme.server.actors

import akka.actor.Props
import akka.event.Logging
import akka.persistence.{PersistentActor, RecoveryCompleted}
import ru.spbau.mit.scala.rateme.server.actors.UsersActor._
import ru.spbau.mit.scala.rateme.client.pages.models._

import scala.collection.mutable
import scala.util.Random

object UsersActor {

  case class Register(request: RequestSign)

  case class Auth(request: RequestSign)

  case class Like(request: RequestLike, user: User)

  case class UploadPhoto(request: RequestUploadPhoto, user: User)

  case class GetPhotos(user: User, request: RequestPhotos)

  def props: Props = Props(new UsersActor())
}

class UsersActor extends PersistentActor {
  override def persistenceId: String = "UsersId"

  val log = Logging(context.system, this)
  val users: mutable.Map[String, User] = mutable.Map[String, User]()
  val random = new Random(System.currentTimeMillis())

  override def receiveCommand: Receive = {
    case Register(request) => persist(Register(request))(_ => sender ! register(request.login, request.password))
    case Auth(request) => sender ! auth(request.login, request.password)
    case Like(request, user) => persist(Like(request, user))(_ => like(request, user))
    case UploadPhoto(request, user) => persist(UploadPhoto(request, user))(_ => uploadPhoto(request, user))
    case GetPhotos(user, request) => sender ! getRandomPhotos(user, request)
    case x => log.warning(s"Unknown request: $x")
  }

  override def receiveRecover: Receive = {
    case Register(request) => register(request.login, request.password)
    case Like(request, user) => like(request, user)
    case UploadPhoto(request, user) => uploadPhoto(request, user)
    case RecoveryCompleted => log.debug("Recovery completed")
    case x => log.error(s"Recover WHAT?: $x")
  }

  private def register(name: String, password: String): ResponseRegister = if (users.contains(name)) {
    ResponseRegister(false)
  } else {
    users(name) = User(name, password, "", mutable.MutableList())
    ResponseRegister(true)
  }

  private def auth(name: String, password: String): User =
    if (users.contains(name) && users(name).password == password) users(name) else null

  private def like(request: RequestLike, user: User) =
    if (!users.contains(user.login) || !users.contains(request.login)) null
    else users(request.login).likes += user.login

  private def uploadPhoto(request: RequestUploadPhoto, user: User) =
    if (!users.contains(user.login)) null
    else {
      users(user.login).photo = request.photoUrl
      users(user.login).likes.clear()
    }

  private def getRandomPhotos(user: User, request: RequestPhotos): ResponsePhotos = {
    val result = mutable.MutableList[User]()
    val data = mutable.MutableList[User]()
    data ++= users.values

    while (result.size < 2) {
      val index = random.nextInt() % data.size
      result += data(index)
    }

    ResponsePhotos(result.head.login, result.head.photo, result(1).login, result(1).photo)
  }
}
