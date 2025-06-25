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
  val newWd = Paths.get("/home/michal/silos/Dropbox/programowanie/python_general")
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

  // ------------------------------------------------------------------
  // # print column content
  // print(df["median_income"])
  // print(df[["median_income", "median_house_value", "households"]])
  df.select("median_income").show(20, truncate = false)   // first 20 rows
  df.select("median_income", "median_house_value", "households").show(20, truncate = false)

  // ------------------------------------------------------------------
  // # number of all obs
  // len(df)

  // total number of rows in the DataFrame
  val nRows: Long = df.count()
  println(s"Number of observations: $nRows")

  // ------------------------------------------------------------------
  // Slicing

  // --------------
  // Example A: numeric thresholds  (median_income > 5 and house value < 200k)

  // percentiles 25 and 75
  val probs = Array(0.25, 0.75)

  // 1)  FAST / APPROXIMATE  (relative error 10 %)
  val Array(p25_approx, p75_approx) =
    df.stat.approxQuantile("median_income", probs, 0.1)
  println(f"≈ 25-th = $p25_approx%.4f   |   ≈ 75-th = $p75_approx%.4f")

  // 2)  EXACT  (set relative error = 0.0) ─ may sort the whole column
  val Array(p25_exact, p75_exact) =
    df.stat.approxQuantile("median_income", probs, 0.0)
  println(f"Exact 25-th = $p25_exact%.4f | Exact 75-th = $p75_exact%.4f")  

  val subsetAapprox = df
    .filter( col("median_income") > p25_approx && col("median_income") < p75_approx )
    .select("median_income", "median_house_value", "households")

  val subsetAexact = df
    .filter( col("median_income") > p25_approx && col("median_income") < p75_approx )
    .select("median_income", "median_house_value", "households")  

  subsetAapprox.show(5)
  subsetAexact.show(5)

  val nRowsApprox: Long = subsetAapprox.count()
  val nRowsExact: Long = subsetAexact.count()

  println(s"Number of observations for approx: $nRowsApprox")
  println(s"Number of observations for exact: $nRowsExact")

  // --------------
  // Example B: range with between()   (like pandas df.loc[mask, cols])

  val subsetB = df
    .filter( col("median_income").between(p25_approx, p75_approx))
    .select("median_income", "median_house_value", "households")

  val nRowsBetween: Long = subsetB.count()
  println(s"Number of observations for approx: $nRowsBetween")

  // --------------
  // Example C: membership test (categorical)   income bucket ∈ {3,4,5}

  val counts = df
  .groupBy("ocean_proximity")   // group by categorical column
  .count()                      // row counts per category
  .orderBy(desc("count"))       // most-frequent first (optional)

  counts.show(truncate = false)
  
  val subsetC = df
    .filter( col("ocean_proximity").isin("<1H OCEAN", "INLAND", "ISLAND") )
    .select("median_income", "median_house_value", "ocean_proximity")

  subsetC.show(5)
  // --------------
  // Example D: multiple independent masks combined with or/and

  val subsetD = df
    .filter(
        (col("median_income") > p75_approx && col("households") > 1000) ||
        (col("median_house_value") > 400000)
    )
    .select("median_income", "median_house_value", "households")

  subsetD.show(20, truncate = false)

  // ------------------------------------------------------------------
  // NULLS count
  // ------------------------------------------------------------------

  // --- in a column
  val nullsMedianIncome: Long =
  df.filter(col("median_income").isNull).count()

  println(s"Nulls in median_income = $nullsMedianIncome")
  
  // --- overall

  // map: transforms every name c into a null-check Column.
  // reduce: folds the sequence with the binary operator || (logical OR
  // defined for Spark Columns). Shorthand _ || _ is equivalent to (x, y) => x || y.

  val anyNullExpr =
    df.columns.map(c => col(c).isNull).reduce(_ || _)

  val nullsOverall: Long = df.filter(anyNullExpr).count()

  println(s"Rows with at least one null = $nullsOverall")

  // --- per column

  // .map { c => … }: Seq[Tuple2[String, Long]] Loops over each name c and returns a pair (c, nullCount)
  // c -> df.filter(col(c).isNull).count(): Tuple2[String, Long] The infix arrow -> is just sugar for Tuple2(c, value)
  // .toMap: Map[String, Long] Turns the sequence of tuples into a Map keyed by the column name.

  val perColumnNulls: Map[String, Long] =
    df.columns.map { c =>
      c -> df.filter(col(c).isNull).count()
    }.toMap

  perColumnNulls.foreach { case (c, n) => println(f"$c%-20s $n") }

  // ------------------------------------------------------------------
  // map and reduce

  // --- map
  // Transform every element
  // a function that turns one element into another
  List(1, 2, 3).map(x => x * 10)
  // res: List(10, 20, 30)

  // --- reduce
  // Combine all elements into one
  // a binary operator that merges two elements
  List(10, 20, 30).reduce(_ + _)
  // res: 60

  // ------------------------------------------------------------------
  // max and min
  val stats = df.agg(
    min("median_income").as("min_income"),
    max("median_income").as("max_income"),
    avg("median_income").as("avg_income")
  )

  stats.show()          // pretty print

  // another option (one row df)
  df.filter(col("median_income").between(p25_approx, p75_approx))
  .select(min("population").as("min_pop"))
  .show()

  // ------------------------------------------------------------------
  // agg and groupby
  val byOcean = df.groupBy("ocean_proximity")
                .agg(
                  count("*").as("n_rows"),
                  avg("median_income").as("avg_income")
                )
  byOcean.show()

  spark.stop()
}
