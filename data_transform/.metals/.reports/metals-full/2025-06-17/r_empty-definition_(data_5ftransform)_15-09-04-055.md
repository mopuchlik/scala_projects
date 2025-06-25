file://<WORKSPACE>/src/main/scala/example/main.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 1305
uri: file://<WORKSPACE>/src/main/scala/example/main.scala
text:
```scala
import org.apache.spark.sql.SparkSession
import java.nio.file.{Files, Paths}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

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
  System.setProperty("user.dir", newWd.toString)

  println(s"Current working directory: ${Paths.get("").toAbsolutePath}")

  // 3) load the CSV
  val df = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("housing.csv")        // relative to the just-set working dir

  df.show(5)                   // preview

  // -----
  // # print column content
  // print(df["median_income"])
  // print(df[["median_income", "median_house_value", "households"]])
  df.select("median_income").show(20, truncate = false)   // first 20 rows
  df.select("median_income", "median_house_value", 
  "households").show(20, truncate = false)

  // -----
  // # number of all obs@@
  // len(df)

  // total number of rows in the DataFrame
  val nRows: Long = df.count()
  println(s"Number of observations: $nRows")

  // # NOTE: indices x:y goes from x to y-1 !!!
  // df.iloc[:, 2:4]
  // # but loc is inclusive (!!!)
  // df.loc[:, "longitude":"housing_median_age"]

  // ## the same for rows
  // df.iloc[2:4, :]  # --> does not include 4
  // df.loc[2:4, :]  # --> includes 4
  val withIdx = df.withColumn("idx",row_number().over(Window.orderBy(lit(1))) - 1)      // 0-based index

  // // iloc[2:4 , :]
  // val iloc24 = withIdx.filter($"idx" >= 2 && $"idx" < 4).drop("idx")
  // iloc24.show()

  // // loc[2:4 , :]   (# inclusive upper bound)
  // val loc24 = withIdx.filter($"idx" >= 2 && $"idx" <= 4).drop("idx")
  // loc24.show()



  spark.stop()
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 