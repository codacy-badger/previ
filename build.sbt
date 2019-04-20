
import Dependencies._

libraryDependencies  ++= scalatest
enablePlugins(Example)
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test


lazy val buildSettings = Seq(
  organization := "com.thecodewriter",
  scalaVersion := "2.11.11",
  //doctestTestFramework := DoctestTestFramework.ScalaTest
)


lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    name := "previ",
    buildSettings,
    Defaults.itSettings,
    libraryDependencies ++= spark ++ circe ++ scalatest, //commonDependencies,
    excludeDependencies += "eigenbase" % "eigenbase-properties"
  ).aggregate(ho2source)


//partially inspired by http://rml.io/yarrrml/spec/
lazy val ho2source = (project in file("ho2source"))
  .configs(IntegrationTest)
  .settings(
    name := "HOCON 2 sparksource",
    Defaults.itSettings,
    buildSettings,
    libraryDependencies ++= circe ++ scalatest, //commonDependencies,
    excludeDependencies += "eigenbase" % "eigenbase-properties"
  )