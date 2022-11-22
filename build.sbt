ThisBuild / publishTo := sonatypePublishToBundle.value
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"
ThisBuild / versionScheme := Some("semver-spec")
ThisBuild / organization := "io.github.keirlawson"
ThisBuild / version := "0.3"
ThisBuild / licenses := Seq(
  "APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")
)
ThisBuild / homepage := Some(url("https://github.com/keirlawson/simple3"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/keirlawson/simple3"),
    "scm:git@github.com:keirlawson/simple3.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "keirlawson",
    name = "Keir Lawson",
    email = "keirlawson@gmail.com",
    url = url("https://github.com/keirlawson")
  )
)

lazy val root = project
  .in(file("."))
  .aggregate(simple3.js, simple3.jvm)
  .settings(
    publish / skip := true
  )

lazy val simple3 = crossProject(JSPlatform, JVMPlatform)
  .in(file("."))
  .settings(
    name := "Simple3",
    scalaVersion := "2.13.10",
    publishMavenStyle := true,
    libraryDependencies += "org.typelevel" %%% "cats-effect" % "3.4.0",
    libraryDependencies += "co.fs2" %%% "fs2-io" % "3.4.0"
  )
  .jvmSettings(
    libraryDependencies += "software.amazon.awssdk" % "s3" % "2.18.21"
  )
  .jsSettings(
    scalaJSUseMainModuleInitializer := true,
    Compile / npmDependencies ++= Seq(
      "@aws-sdk/client-s3" -> "3.213.0"
    )
  )
  .jsConfigure { project => project.enablePlugins(ScalaJSBundlerPlugin) }
