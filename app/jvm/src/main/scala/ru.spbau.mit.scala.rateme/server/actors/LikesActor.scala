package ru.spbau.mit.scala.rateme.server.actors

import akka.persistence.PersistentActor

object LikesActor {

  def props(): Props = Props(userProps)
}

class LikesActor extends PersistentActor {
}
