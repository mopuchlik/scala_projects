error id: file://<WORKSPACE>/src/main/scala/example/main.scala:read.
file://<WORKSPACE>/src/main/scala/example/main.scala
empty definition using pc, found symbol in pc: read.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -spark/read.
	 -scala/Predef.spark.read.
offset: 456
uri: file://<WORKSPACE>/src/main/scala/example/main.scala
text:
```scala
// src/main/scala/csv_reader.scala
import org.apache.spark.sql.SparkSession
import java.nio.file.Paths

@main def runCsv(): Unit =
  val spark = SparkSession.builder
    .appName("CSV-reader")
    .master("local[*]")
    .getOrCreate()

  val cwd = Paths.get("<HOME>/silos/Dropbox/programowanie/python_general")
  System.setProperty("user.dir", cwd.toString)
  println(s"Current working directory: ${Paths.get("").toAbsolutePath}")

  val df = spark.@@read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("housing.csv")

  df.show(5)
  spark.stop()

```


#### Short summary: 

empty definition using pc, found symbol in pc: read.