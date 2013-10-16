package com.danese.traffic.entity

import com.codahale.metrics.Timer.Context
import com.danese.traffic.Traffic
import com.codahale.metrics.{MetricRegistry, Timer}

object Car {
  private def getStopWatch(timerName: String): Context = Traffic.config.timers.get(timerName).get.time()
}


case class Car(id: Long, direction: Direction, intent: Intent) {
  val totalTimer: Context = Car.getStopWatch("total")
  val comboTimerName: String = s"""${direction.toString} ${intent.toString}"""
  val comboTimer : Context = Car.getStopWatch(comboTimerName)
}





