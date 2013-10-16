package com.danese.traffic.entity

import com.danese.traffic.Traffic
import com.codahale.metrics.Timer.Context

trait Intent {
  def toString : String
  val timer: Context = Traffic.config.metrics.get("intentional").get.timer(toString).time()
}

case class Right extends Intent {
  override def toString : String = """turning right"""
}
case class Left extends Intent {
  override def toString : String = """turning left"""
}
case class Straight extends Intent {
  override def toString : String = """continuing straight"""
}
