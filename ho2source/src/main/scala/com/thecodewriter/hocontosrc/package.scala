package com.thecodewriter

import com.thecodewriter.hocontosrc.SourceFormat.SourceFormat
import io.circe.{Decoder, Encoder}


package object hocontosrc {


  implicit val fileFormatDecoder: Decoder[SourceFormat.Value] = Decoder.enumDecoder(SourceFormat)
  implicit val fileFormatEncoder: Encoder[SourceFormat.Value] = Encoder.enumEncoder(SourceFormat)


  case class Format(name: SourceFormat, separator: Option[String])

  case class Source(format: Format, access: String)

}
