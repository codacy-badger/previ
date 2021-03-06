/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter.hocontosrc

import io.circe.generic.auto._
import io.circe.config.syntax._
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{FeatureSpec, GivenWhenThen}

class ParserSuite extends FeatureSpec with GivenWhenThen {

  feature("Source defining classes maps to HOCON config") {
    val goodScenarios = Seq(
      ("CSV", "configs/csv_source.conf", SourceFormat.csv),
      ("AVRO", "configs/avro_source.conf", SourceFormat.avro),
      ("table", "configs/table_source.conf", SourceFormat.table)
    )
    goodScenarios.foreach {
      case (scen, configFile, format) => {
        scenario(s"Having a $scen config") {
          Given(s"$configFile file as config")
          val config: Config = ConfigFactory.load(configFile)
          When(s"config source is mapped to Source object")
          val e: Either[io.circe.Error, Source] = config.as[Source]("source")
          Then(e + " should be Right and format should be " + format)
          assert(e.isRight)
          assert(e.right.get.format.name == format)
        }
      }
    }
    val badScenarios = Seq(
      ("incomplete CSV", "configs/csv_incomplete_source.conf")
    )
    badScenarios.foreach {
      case (scen, configFile) => {
        scenario(s"Having an $scen config") {
          Given(s"$configFile file as config")
          val config: Config = ConfigFactory.load(configFile)
          When(s"config source is mapped to Source object")
          val e: Either[io.circe.Error, Source] = config.as[Source]("source")
          Then(e + " should be Left")
          assert(e.isLeft)
        }
      }
    }
  }
}
