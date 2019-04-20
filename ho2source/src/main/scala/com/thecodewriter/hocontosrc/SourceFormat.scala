package com.thecodewriter.hocontosrc

/**
  * Supported
  */
object SourceFormat extends Enumeration {
  type SourceFormat = Value
  val json, orc, parquet, csv, avro, table = Value
}

