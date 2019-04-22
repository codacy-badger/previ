import sbt.Keys.libraryDependencies
import sbt._

object Dependencies {

  private val sparkVersion = "2.4.0.cloudera1"
  private val sparkScope = "provided"

  val spark: Seq[ModuleID] = Seq("core", "sql", "hive")
    .map(l => "org.apache.spark" %% s"spark-$l" % sparkVersion % sparkScope) ++
    Seq("org.apache.spark" %% "spark-avro" % "2.4.0" % "compile")

  val sparktest: Seq[ModuleID] = Seq(
    "com.holdenkarau" %% "spark-testing-base" % "2.4.0_0.11.0" % "it,test",
    "org.apache.spark" %% "spark-hive" % "2.0.0" % "it,test"
  )
  val scalatest: Seq[ModuleID] = Seq(

    "org.scalatest" %% "scalatest" % "3.0.7" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"

  )

  val circe = Seq(
    //"io.circe" %% "circe-yaml" % "0.9.0" % "compile",
    "io.circe" %% "circe-generic" % "0.11.0" % "compile",
    "io.circe" %% "circe-config" % "0.6.0" % "compile"
  )

}
