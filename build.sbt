
import Dependencies._
import Resolvers._

fork in Test := true
parallelExecution in Test := false

libraryDependencies ++= scalatest
//enablePlugins(Example)
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test


lazy val buildSettings = Seq(
  organization := "com.thecodewriter",
  scalaVersion := "2.11.11"
  //doctestTestFramework := DoctestTestFramework.ScalaTest
)


lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    name := "previ",
    buildSettings,
    Defaults.itSettings,
    resolvers += cloudera,
    libraryDependencies ++= spark ++ circe ++ scalatest, //commonDependencies,
    excludeDependencies += "eigenbase" % "eigenbase-properties"
  ).aggregate(ho2source,ho2mapping)


//partially inspired by http://rml.io/yarrrml/spec/
lazy val ho2source = (project in file("ho2source"))
  .configs(IntegrationTest)
  .settings(
    name := "HOCON to sparksource",
    Defaults.itSettings,
    buildSettings,
    resolvers += cloudera,
    libraryDependencies ++= spark ++ circe ++ scalatest, //commonDependencies,
    excludeDependencies += "eigenbase" % "eigenbase-properties"
  )

lazy val ho2mapping = (project in file("ho2mapping"))
  .configs(IntegrationTest)
  .settings(
    name := "HOCON to event source mapping",
    Defaults.itSettings,
    buildSettings,
    resolvers += cloudera,
    libraryDependencies ++= spark ++ circe ++ scalatest,
    excludeDependencies += "eigenbase" % "eigenbase-properties"
  ).dependsOn(ho2source)