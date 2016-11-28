package ru.spbau.mit.scala.rateme.server

import scala.collection.mutable

object DomainModel {

  /* data classes */
  final case class Photo(string: String)
  final case class User(login: String, password: String, photos: List[Photo])
  final case class Session(id: Int)

  /* server data */
  val users: mutable.Map[String, User] = mutable.Map[String, User]()
  val sessions: mutable.Map[String, Session] = mutable.Map[String, Session]()

  /* requests */
  final case class SignRequest(login: String, password: String)

  /* responses */
  final case class LoginResponse(success: Boolean, login: String = "", sessionKey: Session = Session(-1))
  final case class RegisterResponse(success: Boolean)

  /* contracts*/
  def register(request: SignRequest): RegisterResponse = {
    val (login, password) = request
    if (users.contains(login)) {
      return RegisterResponse(false)
    }

    users(login) = User(login, password, List())
    RegisterResponse(true)
  }

  def login(request: SignRequest): LoginResponse = {
    val (login, password) = request
    if (!users.contains(login)) {
      return LoginResponse(success = false)
    }

    val id = sessions.size
    sessions(login) = Session(id)

    LoginResponse(success = true, login, Session(id))
  }
}