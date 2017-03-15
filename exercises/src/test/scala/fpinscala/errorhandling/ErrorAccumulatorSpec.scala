package fpinscala.errorhandling

import org.scalatest.{FlatSpec, Matchers}

/**
  * Test specifications for the ErrorAccumulator class.
  */
class ErrorAccumulatorSpec extends FlatSpec with Matchers {

  "orElse" should "propagate values" in {
    Value(3).orElse(Value(4)) shouldBe Value(3)
    Value(3).orElse(Errors(List("error"))) shouldBe Value(3)
  }

  it should "pass along the else case when errors are present, accumulating them" in {
    Errors(List("error")).orElse(Value(4)) shouldBe Value(4)
    Errors(List("error")).orElse(Errors(List("error2"))) shouldBe Errors(List("error", "error2"))
  }

  "map2" should "only return a value when both parameters are present" in {
    val f = (a: Int, b: Int) => a + b
    Value(1).map2(Value(2))(f) shouldBe Value(3)
    Value(1).map2(Errors(List("error")))(f) shouldBe Errors(List("error"))
    Errors(List("error")).map2(Value(2))(f) shouldBe Errors(List("error"))
    Errors(List("error")).map2(Errors(List("error2")))(f) shouldBe Errors(List("error", "error2"))
  }

  "sequence" should "return a list of values iff all values passed in are present" in {
    ErrorAccumulator.sequence(List(Value(1), Value(2), Value(3))) shouldBe Value(List(1, 2, 3))
    ErrorAccumulator.sequence(List()) shouldBe Value(List())
    ErrorAccumulator.sequence(List(Value(1), Errors(List("error")), Value(3))) shouldBe Errors(List("error"))
    ErrorAccumulator.sequence(List(Errors(List("error")), Value(2), Errors(List("error2")))) shouldBe
      Errors(List("error", "error2"))
    ErrorAccumulator.sequence(List(Value(1), Value(2), Errors(List("error")))) shouldBe Errors(List("error"))
  }

  "traverse" should "return a value only if all computations succeed" in {
    def oddsPass(i: Int): ErrorAccumulator[String, Int] = if (i % 2 == 0) Errors(List(s"$i is not odd")) else Value(i)
    ErrorAccumulator.traverse(List(1, 3, 5))(oddsPass) shouldBe Value(List(1, 3, 5))
    ErrorAccumulator.traverse(List(1, 2, 5, 6, 7))(oddsPass) shouldBe Errors(List("2 is not odd", "6 is not odd"))
  }
}
