package com.danese.traffic.entity

import com.codahale.metrics.Timer.Context
import com.danese.traffic.Traffic

trait Direction {
  def toString : String
  val timer: Context = Traffic.config.metrics.get("directional").get.timer(toString).time()

}

case class North extends Direction {
  override def toString : String = """North"""
}

case class South extends Direction {
  override def toString : String = """South"""
}

case class East extends Direction {
  override def toString : String = """East"""
}

case class West extends Direction {
  override def toString : String = """West"""
}
