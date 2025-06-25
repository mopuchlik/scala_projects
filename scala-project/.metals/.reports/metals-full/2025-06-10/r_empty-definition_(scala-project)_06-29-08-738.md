error id: file://<WORKSPACE>/src/main/scala/example/example.workseet2.sc:`<none>`.
file://<WORKSPACE>/src/main/scala/example/example.workseet2.sc
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:
	 -isGoodEnough.
	 -isGoodEnough#
	 -isGoodEnough().
	 -scala/Predef.isGoodEnough.
	 -scala/Predef.isGoodEnough#
	 -scala/Predef.isGoodEnough().
offset: 103
uri: file://<WORKSPACE>/src/main/scala/example/example.workseet2.sc
text:
```scala
def abs(x:Double) = if (x < 0) -x else x

def sqrtIter(guess: Double, x: Double): Double =
  if (isGood@@Enough(guess, x)) guess
  else sqrtIter(improve(guess, x), x)



def sqrt(x: Double) = sqrtIter(1.0, x)

val res = sqrt(0.001)


```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.