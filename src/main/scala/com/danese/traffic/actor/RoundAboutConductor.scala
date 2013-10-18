package com.danese.traffic.actor

import akka.actor._
import com.danese.traffic.entity._
import scala.concurrent.duration._
import scala.collection.mutable.{ListBuffer, SynchronizedQueue}
import scala._
import com.danese.traffic.entity.North
import com.danese.traffic.entity.East

object RoundAboutConductor {
  def props: Props = Props[RoundAboutConductor]
}


class RoundAboutConductor extends Actor with ActorLogging {

  implicit val ec = context.dispatcher

  val trafficDestination = context.actorOf(TrafficDestination.props)
  var queues: List[SynchronizedQueue[Car]] = {
    val queuesBuffer = new ListBuffer[SynchronizedQueue[Car]]()
    for (i : Int <- 0 until 4) {
      queuesBuffer.append(new SynchronizedQueue[Car]())
    }
    queuesBuffer.toList
  }
  context.system.scheduler.schedule(0 millisecond, 50 milliseconds, executeStep)

  def receive = {
    case car: Car => {
      car.direction match {
        case _ : North => queues(0).enqueue(car)
        case _ : South => queues(1).enqueue(car)
        case _ : East => queues(2).enqueue(car)
        case _ : West => queues(3).enqueue(car)
      }
    }
  }

  def executeStep: Runnable = new Runnable {
    def run() = {
      for (q <- queues) {

          try {
            trafficDestination ! q.dequeue()
          }
          catch {
            case e : Exception => {}
          }


      }
    }
  }
}
