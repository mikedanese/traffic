package com.danese.traffic.util

import com.typesafe.config.Config
import org.uncommons.maths.random.PoissonGenerator
import com.codahale.metrics.{MetricRegistry, Timer}


case class TrafficConfig(appConfig: Config,
                         poissonFunction: PoissonGenerator,
                         akkaConfig : Config,
                         metrics : Map[String, MetricRegistry],
                         timers : Map[String, Timer])
