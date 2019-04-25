/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter.hocontomapping

import com.thecodewriter.hocontosrc.SparkSource
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.DataFrame
import io.circe.Error
import org.apache.spark.sql.DataFrame
import org.slf4j.LoggerFactory


class SparkEventSource(eventSource: Either[Error, EventSource]) { //extends
  val logger = LoggerFactory.getLogger(this.getClass  )

  //override
  val eventDf: Option[DataFrame] = eventSource.fold(handleErrror, srcToEventDf)

  implicit def srcToEventDf(sourceConfig: EventSource): Option[DataFrame] = {
    val df = SparkSource(sourceConfig.source).
      df//.
      //transform(eventMapper(sourceConfig.mapping))
    Some(df)
  }


  def handleErrror(e: Error): Option[DataFrame] = {
    logger.error("Error parsing the configuration: ",e.fillInStackTrace())
    None
  }

}

object SparkEventSource {
  def apply(config: Config): SparkEventSource = new SparkEventSource(configToEventSource(config))

  def apply(path: String): SparkEventSource = SparkEventSource(ConfigFactory.load(path))
}
