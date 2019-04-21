/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter.hocontosrc

import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{FeatureSpec, GivenWhenThen}

class SparkSourceSuite extends FeatureSpec with GivenWhenThen {

  feature("Dataframe from HOCON config input") {
    scenario(s"Having a csv config and csv input file") {
      val configFile = "configs/csv_source"
      Given(s"$configFile file as config")
      val config: Config = ConfigFactory.load(configFile).getConfig("source")
        When(s"$config is mapped to the source")
      val sparkSource = SparkSource(config)
      Then("should provide a DataFrame with contet")
      assert(sparkSource.df.count() > 0)
    }

  }

}
