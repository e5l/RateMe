package ru.spbau.mit.scala.rateme.server

import java.io._

import scala.collection.mutable
import scala.io.Source
import scala.pickling.Defaults._
import scala.pickling.json._

/* requests */
final case class SignRequest(login: String, password: String)

/* responses */
final case class LoginResponse(success: Boolean, login: String = "", sessionKey: Int = -1)
final case class RegisterResponse(success: Boolean)

/* data */
final case class User(login: String, password: String, photos: List[String]) extends Serializable

case class DomainModel() extends Serializable {
  /* server data */
  val users: mutable.Map[String, User] = mutable.Map[String, User]()
  val sessions: mutable.Map[String, Int] = mutable.Map[String, Int]()

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
    sessions(login) = id

    LoginResponse(success = true, login, id)
  }
}

object DomainModel {

  def readOrCreate(dbPath: String): DomainModel = {
    if (!new File(dbPath).exists()) {
      return new DomainModel()
    }

    val stream = new ObjectInputStream(new FileInputStream(dbPath))
    stream.readObject().asInstanceOf[DomainModel]
  }

  def save(model: DomainModel, dbPath: String) = {
    val stream = new ObjectOutputStream(new FileOutputStream(dbPath))
    stream.writeObject(model)
    stream.close()
  }
}