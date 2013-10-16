package com.danese.traffic.util

import com.typesafe.config.{Config, ConfigFactory}
import org.uncommons.maths.random.{DevRandomSeedGenerator, MersenneTwisterRNG, PoissonGenerator}

object Configurator {
  val config: Config = ConfigFactory.load("traffic")
  val cpm: Int = config.getInt("traffic.cpm")
  val carCount: Int = config.getInt("traffic.cars")
  val akkaConfig: Config = config.getConfig("traffic.akka")

  def getSeedGenerator = new DevRandomSeedGenerator

  def getMersenneTwister: MersenneTwisterRNG = new MersenneTwisterRNG(getSeedGenerator)

  def getPoissonFunction: PoissonGenerator = new PoissonGenerator(cpm, getMersenneTwister)



  def getConfig = TrafficConfig(config, getPoissonFunction, akkaConfig, Metrics.metricRegistries, Metrics.timers)
}

