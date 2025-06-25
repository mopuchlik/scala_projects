error id: file://<WORKSPACE>/src/main/scala/example/main.scala:
file://<WORKSPACE>/src/main/scala/example/main.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 734
uri: file://<WORKSPACE>/src/main/scala/example/main.scala
text:
```scala
//> using lib "org.apache.spark::spark-sql:3.5.0"   // (mill / sbt: add to build)
import org.apache.spark.sql.SparkSession
import java.nio.file.{Paths, Files}

// 1) start Spark (local)
val spark = SparkSession.builder()
  .appName("CSV-reader")
  .master("local[*]")
  .getOrCreate()

// 2) change working directory  (optional)
val cwd = Paths.get("<HOME>/silos/Dropbox/programowanie/python_general")
System.setProperty("user.dir", cwd.toString)          // JVM’s notion of CWD
println(s"Current working directory: ${Paths.get("").toAbsolutePath}")

// 3) read the CSV as a DataFrame
val df = spark.read
  .option("header", "true")
  .option("inferSchema", "true")
  .csv("housing.csv")     // path is relative to cwd
df.show(5@@)                // quick peek

spark.stop()
```


#### Short summary: 

empty definition using pc, found symbol in pc: 