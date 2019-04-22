/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter.hocontosrc

import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{FeatureSpec, GivenWhenThen}

class SparkSourceSuite extends FeatureSpec with GivenWhenThen {

  feature("Dataframe from HOCON config input") {
    val goodScenarios = Seq(
      ("CSV", "configs/csv_source.conf"),
      ("JSON", "configs/json_source.conf")
    )

    goodScenarios.foreach {
      case (scen, configFile) => {
        scenario(s"Having a $scen config") {
          Given(s"$configFile file as config and the necessary file")
          val config: Config = ConfigFactory.load(configFile).getConfig("source")
          When(s"$config is mapped to the source")
          val sparkSource = SparkSource(config)
          Then("should provide a DataFrame with content")
          assert(sparkSource.df.count() > 0)
          An
        }
      }
    }
    ///Test table input

    //Incomplete config
    val badScenarios = Seq(
      ("Incomplete_CSV", "configs/csv_incomplete_source.conf")
    )

    badScenarios.foreach {
      case (scen, configFile) => {
        scenario(s"Having a $scen config") {
          Given(s"$configFile file as config and the necessary file")
          val config: Config = ConfigFactory.load(configFile).getConfig("source")
          When(s"$config is mapped to the source")
          val sparkSource = SparkSource(config)
          Then("should provide an Empty dataframe with no records")
          assert(sparkSource.df.count() == 0)
        }
      }
    }

  }

  //TODO: move these to integration tests
  //TODO: bintray release setup

}
