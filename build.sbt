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
      "com.lihaoyi" %%% "scalatags" % "0.6.1"
    )
  ).
  jsSettings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "0.4.3",
      "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
      "org.webjars" % "jquery" % "2.1.3",
      "org.scala-js" %%% "scalajs-dom" % "0.9.1"
    )
  ).
  jvmSettings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.4",
      "com.typesafe.akka" %% "akka-http" % "10.0.0",
      "com.typesafe.akka" %% "akka-persistence" % "2.4.14",
      "org.iq80.leveldb" % "leveldb" % "0.7",
      "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
    )
  )

lazy val appJS = app.js
lazy val appJVM = app.jvm