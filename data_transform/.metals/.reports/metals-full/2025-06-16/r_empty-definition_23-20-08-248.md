error id: file://<WORKSPACE>/build.sbt:
file://<WORKSPACE>/build.sbt
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 554
uri: file://<WORKSPACE>/build.sbt
text:
```scala
// import Dependencies._

// ThisBuild / scalaVersion     := "2.12.8"
// ThisBuild / version          := "0.1.0-SNAPSHOT"
// ThisBuild / organization     := "com.example"
// ThisBuild / organizationName := "example"

// lazy val root = (project in file("."))
//   .settings(
//     name := "data_transform",
//     libraryDependencies += scalaTest % Test
//   )

// build.sbt  ─────────────
ThisBuild / scalaVersion := "3.4.2"        // pick a real Scala version

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.5.0",
  "org.scalate@@st"    %% "scalatest" % "3.2.18" % Test
)



// Uncomment the following for publishing to Sonatype.
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for more detail.

// ThisBuild / description := "Some descripiton about your project."
// ThisBuild / licenses    := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
// ThisBuild / homepage    := Some(url("https://github.com/example/project"))
// ThisBuild / scmInfo := Some(
//   ScmInfo(
//     url("https://github.com/your-account/your-project"),
//     "scm:git@github.com:your-account/your-project.git"
//   )
// )
// ThisBuild / developers := List(
//   Developer(
//     id    = "Your identifier",
//     name  = "Your Name",
//     email = "your@email",
//     url   = url("http://your.url")
//   )
// )
// ThisBuild / pomIncludeRepository := { _ => false }
// ThisBuild / publishTo := {
//   val nexus = "https://oss.sonatype.org/"
//   if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
//   else Some("releases" at nexus + "service/local/staging/deploy/maven2")
// }
// ThisBuild / publishMavenStyle := true

```


#### Short summary: 

empty definition using pc, found symbol in pc: 