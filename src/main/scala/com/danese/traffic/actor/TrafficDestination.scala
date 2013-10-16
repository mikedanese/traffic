package com.danese.traffic.actor

import akka.actor.{Props, Actor}
import com.danese.traffic.entity.Car

object TrafficDestination {
  def props : Props = Props[TrafficDestination]
}

class TrafficDestination extends Actor {

  def receive = {
    case car : Car => stopCar(car)
  }

  def stopCar(car : Car) {
    car.comboTimer.stop()
    car.totalTimer.stop()
    car.intent.timer.stop()
    car.direction.timer.stop()
  }
}
