
import Dependencies._
import Resolvers._
import Settings._

libraryDependencies ++= scalatest
//enablePlugins(Example)
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

enablePlugins(SiteScaladocPlugin)

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    name := "previ",
    buildSettings,
    Defaults.itSettings,
    resolvers += cloudera,
    libraryDependencies ++= spark ++ circe ++ scalatest, //commonDependencies,
    excludeDependencies += "eigenbase" % "eigenbase-properties"
  ).aggregate(ho2source, ho2mapping, spark_utils)


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
    libraryDependencies ++= spark ++ circe ++ scalatest ++ sparktest,
    excludeDependencies += "eigenbase" % "eigenbase-properties"
  ).dependsOn(ho2source, spark_utils)



lazy val spark_utils = (project in file("spark-utils"))
  .configs(IntegrationTest)
  .settings(
    name := "Spark helper functions",
    Defaults.itSettings,
    buildSettings,
    resolvers += cloudera,
    libraryDependencies ++= spark ++ scalatest ++ sparktest,
    excludeDependencies += "eigenbase" % "eigenbase-properties"
  )

