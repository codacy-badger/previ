/*
 * Copyright (C) 2019 Matyas Kuti-Kreszacs
 */

import sbt._
import sbt.Keys.{publishMavenStyle, _}

object Settings {
  val testSettings = Seq(
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    fork in Test := true,
    parallelExecution in Test := false,
  )
  
  val publishSettings = Seq(
    publishMavenStyle := true
  )

  lazy val buildSettings = Seq(
    organization := "com.thecodewriter",
    scalaVersion := "2.11.11"
  )

}

