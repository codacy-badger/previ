package com.thecodewriter.hocontosrc

/**
  * Supported
  */
object SourceFormat extends Enumeration {
  type SourceFormat = Value
  val json, orc, parquet, csv, avro, table = Value


  /**
    * Converts enum values to String automatically
    * @param s
    * @return String representation of the enum
    */
  implicit def srcFormatAsString(s:SourceFormat):String =
    s.toString
}

