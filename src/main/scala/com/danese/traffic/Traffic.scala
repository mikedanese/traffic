package com.danese.traffic

import akka.actor.ActorSystem
import com.danese.traffic.util.Configurator
import com.danese.traffic.actor.TrafficSource


object Traffic extends App {

  val config = Configurator.getConfig
  val system = ActorSystem("traffic", config.akkaConfig)
  val trafficSource = system.actorOf(TrafficSource.props)
  val log = system.log

  log.info("system initialized")

  for (index : Int <- 0 until Configurator.carCount) {
    trafficSource ! (config.poissonFunction.nextValue() / Configurator.cpm.toDouble)
  }
}