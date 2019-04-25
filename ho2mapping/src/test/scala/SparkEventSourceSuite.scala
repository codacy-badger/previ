import com.thecodewriter.hocontomapping.SparkEventSource
import com.thecodewriter.hocontosrc.SparkSource
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{FeatureSpec, GivenWhenThen}

/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

class SparkEventSourceSuite extends FeatureSpec with GivenWhenThen {

  feature("Eventsource") {
    scenario(s"Having a  config") {
      val conf = ConfigFactory.load("configs/json_event.conf").getConfig("event")
      println(conf)
      val s = SparkEventSource(conf)
      s.eventDf.get.show(false)
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
