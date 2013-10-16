package com.danese.traffic.util

import com.codahale.metrics.{Timer, CsvReporter, MetricRegistry}
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.io.File
import scala.collection.mutable.Map
import scala.collection.immutable

object Metrics {

  val metricRegistries: immutable.Map[String, MetricRegistry] = getMetricRegistries.toMap[String, MetricRegistry]

  val timers: immutable.Map[String, Timer] = getTimers(metricRegistries).toMap[String, Timer]



  private def getMetricRegistries: Map[String, MetricRegistry] = {

    var registries = Map[String, MetricRegistry]()
    val names: List[String] = List("total", "directional", "intentional", "combinational")
    for (name: String <- names) {
      registries = registries ++ Map[String, MetricRegistry](name -> new MetricRegistry())
    }
    registerMetrics(registries)
    registries

  }


  private def registerMetrics(metrics: Map[String, MetricRegistry]) {
    for (tup <- metrics) {
      val reporter: CsvReporter = CsvReporter.forRegistry(tup._2)
        .formatFor(Locale.US)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.NANOSECONDS)
        .build(new File( s"""out/${tup._1}"""));

      reporter.start(5, TimeUnit.SECONDS);
    }
  }

  private def getTimers(metrics: immutable.Map[String, MetricRegistry]): Map[String, Timer] = {

    var timers: Map[String, Timer] = Map[String, Timer]()

    val totalTimer: Timer = metrics.get("total").get.timer("cars")
    timers = timers ++ Map[String, Timer]("total" -> totalTimer)

    val comboTimers : Map[String, Timer] = getComboTimers(metrics)
    timers = timers ++ comboTimers

    timers
  }

  private def getComboTimers(metrics: immutable.Map[String, MetricRegistry]) : Map[String, Timer] = {
    val intents: List[String] = List("turning right", "turning left", "continuing straight")
    val directions: List[String] = List("North", "South", "East", "West")
    var timers: Map[String, Timer] = Map[String, Timer]()

    for (intent: String <- intents) {
      for (direction: String <- directions) {
        val timerName: String = "%s %s".format(direction, intent)
        val timer: Timer = metrics.get("combinational").get.timer(timerName)
        timers = timers ++ Map[String, Timer](timerName -> timer)
      }
    }
    timers
  }

}
