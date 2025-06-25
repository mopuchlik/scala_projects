error id: file://<WORKSPACE>/project/Dependencies.scala:`<none>`.
file://<WORKSPACE>/project/Dependencies.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 69
uri: file://<WORKSPACE>/project/Dependencies.scala
text:
```scala
import sbt._

object Dependencies {
  lazy val scalaTest = "org.scala@@test" %% "scalatest" % "3.0.5"
  scalaVersion := "3.7.0"          // or 3.3.x
  libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.5.0"

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.