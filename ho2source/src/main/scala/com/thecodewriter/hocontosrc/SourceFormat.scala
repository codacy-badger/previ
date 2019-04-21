/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter.hocontosrc

/**
  * Supported input sources
  */
object SourceFormat extends Enumeration {
  type SourceFormat = Value
  val json, orc, parquet, csv, avro, table = Value


  /**
    * Converts enum values to String automatically
    * @param s
    * @return String representation of the enum
    */
  implicit def srcFormatToString(s:SourceFormat):String =
    s.toString
}

