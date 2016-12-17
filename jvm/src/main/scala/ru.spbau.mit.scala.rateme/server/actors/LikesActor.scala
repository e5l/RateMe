package ru.spbau.mit.scala.rateme.server.actors

import akka.actor.Props
import akka.persistence.PersistentActor

object LikesActor {
  def props(): Props = Props()
}

class LikesActor extends PersistentActor {
  override def receiveRecover: Receive = ???

  override def receiveCommand: Receive = ???

  override def persistenceId: String = ???
}
