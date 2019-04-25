/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter

import com.typesafe.config.Config
import io.circe.generic.auto._
import io.circe.config.syntax._
import io.circe._
import com.thecodewriter.hocontosrc.Source
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions._


package object hocontomapping {


  type Access = Array[String]
  /**
    *
    * @param access
    */
  case class Mapping(access:Access) //Maybe referencetype: sql,json

  /**
    * @param access array of access path in the json/complex type. For simple tabular format is the column name
    * @param format Date fromat to parse if string
    */
  case class TimeMapping(access: Array[String], format: String)

  /**
    *
    * @param eventid   optional parameter in case the event has a unique id
    * @param caseid    identifier of the uniquepart for example
    * @param datetime  time of event
    * @param activity  identifier of activity type
    * @param location  where was the event generated
    * @param lifecycle if finished/failed ... https://fluxicon.com/blog/2010/09/intro-to-xes/
    */
  case class EventMapping(eventid: Option[Mapping],
                          caseid: Mapping,
                          datetime: TimeMapping,
                          activity: Mapping,
                          location: Mapping,
                          lifecycle: Mapping)

  case class EventSource(source: Source, mapping: EventMapping)


  def eventMapper(mapping: EventMapping)(df: DataFrame): DataFrame = {
//    df.withColumn().drop()
  ???
  }

  def accessToCol(a: Access): Column={
    a.tail.foldLeft(col(a.head)){(c:Column,s:String)=> c(s)}
  }


  // def configToEventMapping(config: Config): Either[Error, EventMapping] = {
  //  config.as[EventMapping]
  // }

  def configToEventSource(config: Config): Either[Error, EventSource] = {
    config.as[EventSource]
  }

}
