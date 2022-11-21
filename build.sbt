lazy val root = project
  .in(file("."))
  .aggregate(simple3.js, simple3.jvm)

lazy val simple3 = crossProject(JSPlatform, JVMPlatform)
  .in(file("."))
  .settings(
    name := "Simple3",
    scalaVersion := "2.13.10",
    version := "0.1-SNAPSHOT",
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
