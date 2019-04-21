package com.thecodewriter

import com.thecodewriter.hocontosrc.SourceFormat.SourceFormat
import com.typesafe.config.Config
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._
import io.circe.config.syntax._
import io.circe._
import io.circe.generic.semiauto._
import org.slf4j.LoggerFactory


package object hocontosrc {

  val logger = LoggerFactory.getLogger(this.getClass)

  implicit val fileFormatDecoder: Decoder[SourceFormat.Value] = Decoder.enumDecoder(SourceFormat)
  implicit val fileFormatEncoder: Encoder[SourceFormat.Value] = Encoder.enumEncoder(SourceFormat)


  case class Format(name: SourceFormat, separator: Option[String])

  case class Source(format: Format, access: String)

  def configToSource(config: Config): Either[Error, Source] =
    config.as[Source]


}
