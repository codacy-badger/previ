/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter.hocontosrc

import io.circe.{Decoder, Encoder}

/**
  * Supported input sources
  */
object SourceFormat extends Enumeration {
  type SourceFormat = Value
  val json, orc, parquet, csv, avro, table = Value



  //Decoder & encoder for the source format
  //https://stackoverflow.com/questions/42068680/circe-and-scalas-enumeration-type
  implicit val fileFormatDecoder: Decoder[SourceFormat.Value] = Decoder.enumDecoder(SourceFormat)
  implicit val fileFormatEncoder: Encoder[SourceFormat.Value] = Encoder.enumEncoder(SourceFormat)

  /**
    * Converts enum values to String automatically
    * @param s
    * @return String representation of the enum
    */
  implicit def srcFormatToString(s:SourceFormat):String =
    s.toString
}
