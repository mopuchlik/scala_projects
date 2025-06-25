error id: file://<WORKSPACE>/src/main/scala/example/main.scala:org/apache/spark/sql/functions.col().
file://<WORKSPACE>/src/main/scala/example/main.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol org/apache/spark/sql/functions.col().
empty definition using fallback
non-local guesses:

offset: 2399
uri: file://<WORKSPACE>/src/main/scala/example/main.scala
text:
```scala
import org.apache.spark.sql.SparkSession
import java.nio.file.{Files, Paths}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.{StructField, StructType, DoubleType}

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
  df.select("median_income", "median_house_value", "households").show(20, truncate = false)

  // -----
  // # number of all obs
  // len(df)

  // total number of rows in the DataFrame
  val nRows: Long = df.count()
  println(s"Number of observations: $nRows")

  // --- Slicing
  // ------------------------------------------------------------------
  // Example A: numeric thresholds  (median_income > 5 and house value < 200k)
  // ------------------------------------------------------------------

  // percentiles 25 and 75
  val probs = Array(0.25, 0.75)

  // 1)  FAST / APPROXIMATE  (relative error 1 %)
  val Array(p25_approx, p75_approx) =
    df.stat.approxQuantile("median_income", probs, 0.01)
  println(f"≈ 25-th = $p25_approx%.4f   |   ≈ 75-th = $p75_approx%.4f")

  // 2)  EXACT  (set relative error = 0.0) ─ may sort the whole column
  val Array(p25_exact, p75_exact) =
    df.stat.approxQuantile("median_income", probs, 0.0)
  println(f"Exact 25-th = $p25_exact%.4f | Exact 75-th = $p75_exact%.4f")  

  val subsetA = df
    .filter( col("median_income") > $p25_approx && c@@ol("median_house_value") < 200000 )
    .select("median_income", "median_house_value", "households")

  subsetA.show()

  // ------------------------------------------------------------------
  // Example B: range with between()   (like pandas df.loc[mask, cols])
  // ------------------------------------------------------------------
  val subsetB = df
    .filter( col("median_income").between(100, 4) )
    .select("median_income", "median_house_value", "households")

  // ------------------------------------------------------------------
  // Example C: membership test (categorical)   income bucket ∈ {3,4,5}
  // ------------------------------------------------------------------
  val subsetC = df
    .filter( col("median_income").isin(3, 4, 5) )
    .select("median_income", "median_house_value", "households")

  // ------------------------------------------------------------------
  // Example D: multiple independent masks combined with or/and
  // ------------------------------------------------------------------
  val subsetD = df
    .filter(
        (col("median_income") > 5 && col("households") > 1000) ||
        (col("median_house_value") > 400000)
    )
    .select("median_income", "median_house_value", "households")

  // ------------------------------------------------------------------
  // Peek at any subset
  // ------------------------------------------------------------------
  subsetD.show(20, truncate = false)


 


  spark.stop()
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 