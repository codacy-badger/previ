/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter.hocontosrc

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FeatureSpec, GivenWhenThen}

class SparkSourceSuite extends FeatureSpec with GivenWhenThen with BeforeAndAfterAll {

  override def beforeAll() {
    System.setSecurityManager(null)
    val spark = SparkSession.builder()
      .master("local")
      .config("hive.exec.scratchdir", "~/tmp/hive")
      .enableHiveSupport()
      .getOrCreate()
  }

  override def afterAll() {
    SparkSession.builder().getOrCreate().close()
  }

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
        }
      }
    }
    ///Test table input


    scenario("Having a table config") {
      val confFile = "configs/table_source.conf"
      Given(s"$confFile file as config and the necessary table")
      //Create test table
      val config: Config = ConfigFactory.load(confFile).getConfig("source")
      SparkSource(ConfigFactory.load("configs/csv_source.conf").getConfig("source")).
        df.createOrReplaceTempView(config.getString("access"))
      When(s"$config is mapped to the source")
      val sparkSource = SparkSource(config)
      Then("should provide a DataFrame with content")
      assert(sparkSource.df.count() > 0)
    }

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
