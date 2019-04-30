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
  case class Mapping(access: Access) //Maybe referencetype: sql,json

  /**
    * @param access array of access path in the json/complex type. For simple tabular format is the column name
    * @param format Date fromat to parse if string
    */
  case class TimeMapping(access: Access, format: String)

  /**
    *
    * @param eventid  optional parameter in case the event has a unique id
    * @param caseid   identifier of the uniquepart for example
    * @param datetime time of event
    * @param activity identifier of activity type
    * @param location where was the event generated
    *                 // @param lifecycle if finished/failed ... https://fluxicon.com/blog/2010/09/intro-to-xes/
    */
  case class EventMapping(caseid: Mapping,
                          datetime: TimeMapping,
                          eventid: Option[Mapping],
                          activity: Mapping,
                          location: Mapping)

  // lifecycle: Option[Mapping])

  case class EventSource(source: Source, mapping: EventMapping)

  //mappings -> map(newcol,col(access)) -> list(mapcol) -> apply multipe transformations

  def eventMapper(mapping: EventMapping)(df: DataFrame): DataFrame = {
    //val transformationList: List[Column]=List()
    val mappings = Map[String, Column](
      "eventid" -> withEventID(mapping), //has to be first as it might depend on the case+ts
      "caseid" -> mapping.caseid.access,
      "event_ts" -> withEventTime(mapping.datetime),
      "activity" -> mapping.activity.access,
      "location" -> mapping.location.access
    )

    mappings.foldRight(df) {
      (x: (String, Column), dataframe: DataFrame) => dataframe.transform(mappingToCol(x._1, x._2))
    }
  }

  def mappingToCol(colname: String, col: Column)(df: DataFrame): DataFrame = df.withColumn(colname, col).drop(col)


  implicit def accessToCol(a: Access): Column = {
    a.tail.foldRight(col(a.head)) { (s: String, c: Column) => c(s) }
  }

  def withEventID(eventMapping: EventMapping): Column = {
    eventMapping.eventid match {
      case Some(x) => x.access
      case None => concat_ws("_",
        eventMapping.caseid.access,
        eventMapping.datetime.access)
    }
  }

  def withEventTime(timestampMapping: TimeMapping): Column = {
    from_unixtime(timestampMapping.access, timestampMapping.format)
  }

  def mapCol(newcol: String, column: Column)(df: DataFrame): DataFrame = {
    df.withColumn(newcol, column).drop(column)
  }


  // def configToEventMapping(config: Config): Either[Error, EventMapping] = {
  //  config.as[EventMapping]
  // }

  def configToEventSource(config: Config): Either[Error, EventSource] = {
    config.as[EventSource]
  }

}
