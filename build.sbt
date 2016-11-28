import sbt.Keys.libraryDependencies

name := "RateMe"
version := "1.0"
scalaVersion := "2.12.0"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.0")
