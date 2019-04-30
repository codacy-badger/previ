/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter.hocontomapping

/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

import com.typesafe.config.ConfigFactory
import org.scalatest.{FeatureSpec, GivenWhenThen}


class SparkEventSourceSuite extends FeatureSpec with GivenWhenThen {

  feature("Eventsource") {
    scenario(s"Having a  config") {
      Given(s"A file as config")
      val conf = ConfigFactory.load("configs/json_event.conf").getConfig("event")
      println(conf)
      When(s"config source is mapped to Source object")
      val s = SparkEventSource(conf)
      Then(" should be Right and format should be ")
      s.eventDf.get.show(false)
      s.eventDf.get.printSchema()
      assert(!conf.isEmpty)
    }
  }

  //SparkEventSource("configs/json")
  /*
    val goodScenarios = {}
    goodScenarios.foreach {
      case (scen, configFile) => {
        scenario(s"Having a $scen config") {
          Given(s"$configFile file as config and the necessary file")
          val config: Config = ConfigFactory.load(configFile).getConfig("source")
          When(s"$config is mapped to the source")
          val sparkSource = SparkSource(config)
          Then("should provide a DataFrame with content")
          assert(sparkSource.df.count() > 0)
        }
      }
    }

  (*/
}
