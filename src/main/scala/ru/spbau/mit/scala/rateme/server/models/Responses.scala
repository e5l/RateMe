package ru.spbau.mit.scala.rateme.server.models

final case class LoginResponse(success: Boolean, login: String = "", sessionKey: Int = -1)
final case class RegisterResponse(success: Boolean)

