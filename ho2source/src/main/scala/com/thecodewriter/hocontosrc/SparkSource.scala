/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter.hocontosrc

import com.thecodewriter.hocontosrc.SourceFormat._
import com.typesafe.config.{Config, ConfigFactory}
import io.circe.Error
import org.apache.spark.sql.{DataFrame, SparkSession}

class SparkSource(config: Either[Error, Source]) {

  lazy val spark = SparkSession.builder()
    .master("local") //TODO: figure out how to make it configurable
    .enableHiveSupport()
    .getOrCreate()
  val df: DataFrame = config.fold(handleError, srcToDf)

  /**
    * Converts the Source object to DataFrame
    *
    * @param source : Source object created from HOCON config
    * @return
    */
  implicit def srcToDf(source: Source): DataFrame = source.format.name match {
    case SourceFormat.table => spark.read.table(source.access)
    case SourceFormat.csv => spark.read
      .option("delimiter", source.format.separator.getOrElse(","))
      .option("header", "true")
      .csv(source.access)
    case _: SourceFormat => spark.read.format(source.format.name).load(source.access)
  }

  def handleError(error: Error): DataFrame = {
    logger.error("Promblem while parsing source configuration", error.fillInStackTrace())
    spark.emptyDataFrame
  }
}

object SparkSource {
  def apply(config: Config): SparkSource = new SparkSource(configToSource(config))

  def apply(source: Source): SparkSource = new SparkSource(Right(source))
}



