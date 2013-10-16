package com.danese.traffic.actor

import com.danese.traffic.TrafficTestSuite
import com.danese.traffic.util.Configurator
import org.uncommons.maths.random.MersenneTwisterRNG

class TrafficSourceTest extends TrafficTestSuite {

  var rng : MersenneTwisterRNG = null

  before {
    rng = Configurator.getMersenneTwister
  }

  "Mersenne Twister" should "not be zero" in {
    for (i: Int <- 0 until 100) {
      val rn: Int = rng.nextInt(6)
      assert(rn >= 0)
      assert(rn < 6)
    }
  }

}
