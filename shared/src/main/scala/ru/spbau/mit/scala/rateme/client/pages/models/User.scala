package ru.spbau.mit.scala.rateme.client.pages.models

import scala.collection.mutable

final case class User(login: String, password: String, var photo: String, likes: mutable.MutableList[String])
