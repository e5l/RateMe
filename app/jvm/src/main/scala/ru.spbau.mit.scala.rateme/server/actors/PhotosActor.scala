package ru.spbau.mit.scala.rateme.server.actors

import akka.actor.Props
import akka.persistence.PersistentActor
import ru.spbau.mit.scala.rateme.server.models.User

object PhotosActor {
  def props: Props = Props(new PhotosActor())

  case class SetPhoto(user: User, photoUrl: String)
  case class SelectPhoto(user: User)
}

class PhotosActor extends PersistentActor {
  override def receiveRecover: Receive = ???

  override def receiveCommand: Receive = ???

  override def persistenceId: String = "PhotosId"
}
