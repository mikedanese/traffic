package com.danese.traffic.actor

import akka.actor.{Props, Actor}
import org.uncommons.maths.random.MersenneTwisterRNG
import com.danese.traffic.util.Configurator
import com.danese.traffic.entity._
import com.danese.traffic.entity.North
import com.danese.traffic.entity.South
import com.danese.traffic.entity.East

object TrafficSource {
  def props : Props = Props[TrafficSource]
}

class TrafficSource extends Actor {
  val roundAbout = context.actorOf(RoundAboutConductor.props)
  val rng : MersenneTwisterRNG = Configurator.getMersenneTwister

  def receive = {
    case waitTime : Double => sendCar(waitTime)
    case _ => throw new UnsupportedOperationException
  }

  private def sendCar(waitTime: Double): Unit = {
    Thread.sleep((waitTime * 1000).toLong)
    val direction : Direction = rng.nextInt(4) match {
      case 0 => North()
      case 1 => South()
      case 2 => East()
      case 3 => West()
    }
    val intent : Intent = rng.nextInt(3) match {
      case 0 => Right()
      case 1 => Left()
      case 2 => Straight()
    }
    roundAbout ! Car(0, direction, intent)
  }
}
