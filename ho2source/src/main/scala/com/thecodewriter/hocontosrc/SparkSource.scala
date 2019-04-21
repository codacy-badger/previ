package com.thecodewriter.hocontosrc

import com.thecodewriter.hocontosrc.SourceFormat._
import com.typesafe.config.Config
import io.circe.Error
import org.apache.spark.sql.{DataFrame, DataFrameReader, SparkSession}

class SparkSource(config: Config) {

  val spark = SparkSession.builder().enableHiveSupport().getOrCreate()
  val src: DataFrame = configToSource(config).fold(l=>handleError(l), srcToDf)

  /**
    * Converts the Source object to DataFrame
    *
    * @param source : Source object created from HOCON config
    * @return
    */
  implicit def srcToDf(source: Source): DataFrame = source.format.name match {
    case SourceFormat.table => spark.read.table(source.access)
    case SourceFormat.csv => spark.read.option("delimiter", source.format.separator.getOrElse(",")).csv(source.access)
    case _: SourceFormat => spark.read.format(source.format.name).load(source.access)
  }

  def handleError(error: Error): DataFrame = {
    logger.error("Promblem while parsing source configuration", error.fillInStackTrace())
    spark.emptyDataFrame
  }
}

object SparkSource {
  def apply(config: Config): SparkSource = new SparkSource(config)
}



