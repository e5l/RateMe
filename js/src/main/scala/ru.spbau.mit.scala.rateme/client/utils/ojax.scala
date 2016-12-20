package ru.spbau.mit.scala.rateme.client.utils

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.ext.AjaxException

import scala.concurrent.{Future, Promise}
import scala.scalajs.js.JSON

object ojax {
  def post(url: String, data: String): Future[dom.XMLHttpRequest] = {
    val req = new dom.XMLHttpRequest()
    val promise = Promise[dom.XMLHttpRequest]()

    req.onreadystatechange = { (e: dom.Event) =>
      if (req.readyState.toInt == 4) {
        if ((req.status >= 200 && req.status < 300) || req.status == 304) {
          promise.success(req)
          println(JSON.stringify(e))
        }
        else
          promise.failure(AjaxException(req))
      }
    }

    req.open("POST", url)
    req.setRequestHeader("Content-Type", "application/json")
    req.timeout = 0
    req.withCredentials = false
    if (data == null)
      req.send()
    else
      req.send(data)
    promise.future
  }
}
