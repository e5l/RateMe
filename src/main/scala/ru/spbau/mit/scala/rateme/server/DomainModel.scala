package ru.spbau.mit.scala.rateme.server

import scala.collection.mutable

/* requests */
final case class SignRequest(login: String, password: String)

/* responses */
final case class LoginResponse(success: Boolean, login: String = "", sessionKey: Int = -1)
final case class RegisterResponse(success: Boolean)

/* data */
final case class Photo(string: String)
final case class User(login: String, password: String, photos: List[Photo])
final case class Session(id: Int)

class DomainModel {

  /* server data */
  val users: mutable.Map[String, User] = mutable.Map[String, User]()
  val sessions: mutable.Map[String, Session] = mutable.Map[String, Session]()

  /* contracts*/
  def register(request: SignRequest): RegisterResponse = {
    val (login, password) = (request.login, request.password)
    if (users.contains(login)) {
      return RegisterResponse(false)
    }

    users(login) = User(login, password, List())
    RegisterResponse(true)
  }

  def login(request: SignRequest): LoginResponse = {
    val (login, password) = (request.login, request.password)
    if (!users.contains(login)) {
      return LoginResponse(success = false)
    }

    val id = sessions.size
    sessions(login) = Session(id)

    LoginResponse(success = true, login, id)
  }
}

object DomainModel {
}