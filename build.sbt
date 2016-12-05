import sbt.Keys.libraryDependencies

name := "RateMe"
version := "1.0"
scalaVersion := "2.11.8"

enablePlugins(ScalaJSPlugin)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.0",
  "com.typesafe.akka" %% "akka-persistence" % "2.4.14",
  "com.lihaoyi" %%% "scalatags" % "0.6.1",
  "com.lihaoyi" %%% "upickle" % "0.4.3",
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
)
