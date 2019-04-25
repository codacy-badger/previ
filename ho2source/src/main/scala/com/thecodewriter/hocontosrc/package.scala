/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

package com.thecodewriter

import com.thecodewriter.hocontosrc.SourceFormat.SourceFormat
import com.typesafe.config.Config
import io.circe.generic.auto._
import io.circe.config.syntax._
import io.circe._
import org.slf4j.LoggerFactory


package object hocontosrc {

  val logger = LoggerFactory.getLogger(this.getClass)

  case class Format(name: SourceFormat, separator: Option[String])

  case class Source(format: Format, access: String)

  def configToSource(config: Config): Either[Error, Source] =
    config.as[Source]

}
