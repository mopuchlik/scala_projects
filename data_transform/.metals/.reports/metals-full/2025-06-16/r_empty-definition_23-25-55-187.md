error id: file://<WORKSPACE>/src/main/scala/example/main.scala:java/nio/file/Path#toString().
file://<WORKSPACE>/src/main/scala/example/main.scala
empty definition using pc, found symbol in pc: java/nio/file/Path#toString().
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -newWd/toString.
	 -newWd/toString#
	 -newWd/toString().
	 -scala/Predef.newWd.toString.
	 -scala/Predef.newWd.toString#
	 -scala/Predef.newWd.toString().
offset: 563
uri: file://<WORKSPACE>/src/main/scala/example/main.scala
text:
```scala
import org.apache.spark.sql.SparkSession
import java.nio.file.{Files, Paths}

object CsvApp extends App {

  // 1) Spark session (local[*] = use all CPU cores, no cluster)
  val spark = SparkSession.builder
    .appName("CSV-reader")
    .master("local[*]")
    .getOrCreate()

  // 2) change / report working directory
  val newWd = Paths.get("<HOME>/silos/Dropbox/programowanie/python_general")
  // Make sure the directory exists before switching!
  require(Files.exists(newWd), s"Directory $newWd does not exist")
  System.setProperty("user.dir", newWd.@@toString)

  println(s"Current working directory: ${Paths.get("").toAbsolutePath}")

  // 3) load the CSV
  val df = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("housing.csv")        // relative to the just-set working dir

  df.show(5)                   // preview

  spark.stop()
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/nio/file/Path#toString().