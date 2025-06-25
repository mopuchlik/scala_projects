1 + 1

val x = 42

x * x


case class User(name: String, age: Int, country: String, activity: Double)

val users = List(
  User("Anna", 25, "Poland", 72.5),
  User("John", 17, "Poland", 80.0),
  User("Marek", 34, "Poland", 65.0),
  User("Alice", 45, "USA", 90.0),
  User("Ewa", 29, "Poland", 95.0)
)

// 1. Filter adults from Poland
val polishAdults = users.filter(u => u.age >= 18 && u.country == "Poland")

// 2. Normalize scores to [0, 100]
val maxScore = polishAdults.map(_.activity).max
val normalized = polishAdults.map(u => u.copy(activity = (u.activity / maxScore) * 100))

// 3. Compute average activity
val avgActivity = normalized.map(_.activity).sum / normalized.size

// 4. Top 3 names by activity
val topUsers = normalized.sortBy(-_.activity).take(3).map(_.name)

// Results
println(f"Average normalized activity: $avgActivity%.2f")
println(s"Top 3 users: ${topUsers.mkString(", ")}")







