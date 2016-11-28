import sbt.Keys.libraryDependencies

name := "RateMe"
version := "1.0"
scalaVersion := "2.11.8"

enablePlugins(ScalaJSPlugin)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.0",
  "com.lihaoyi" %%% "scalatags" % "0.6.1",
  "com.lihaoyi" %%% "upickle" % "0.4.3"
)
