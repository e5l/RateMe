import sbt.Keys.libraryDependencies

name := "RateMe"
version := "1.0"
scalaVersion in ThisBuild := "2.11.8"

lazy val root = project.in(file(".")).
  aggregate(appJS, appJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

val app = crossProject.in(file(".")).
  settings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % "0.6.1",
      "com.lihaoyi" %%% "upickle" % "0.4.3"
    )
  ).
  jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1"
    )
  ).
  jvmSettings(
    libraryDependencies ++= Seq(
      "io.spray" %% "spray-can" % "1.3.2",
      "io.spray" %% "spray-routing" % "1.3.2",
      "com.typesafe.akka" %% "akka-http" % "10.0.0",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.0",
      "com.typesafe.akka" %% "akka-persistence" % "2.4.14",
      "org.iq80.leveldb" % "leveldb" % "0.7",
      "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
    )
  )

lazy val appJS = app.js
lazy val appJVM = app.jvm