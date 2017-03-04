package fpinscala.errorhandling

import org.scalatest.{FlatSpec, Matchers}

/**
  * Test specifications for the Either class.
  */
class EitherSpec extends FlatSpec with Matchers {

  "map" should "propagate Left" in {
    Left("error").map((i: Int) => i + 1) shouldBe Left("error")
  }

  it should "transform values that are present" in {
    Right(3).map(_ + 1) shouldBe Right(4)
  }

  "flatMap" should "propagate Left" in {
    Left("error").flatMap((i: Int) => Right(i + 1)) shouldBe Left("error")
  }

  it should "transform values into other values" in {
    Right(3).flatMap((i: Int) => Right(i + 1)) shouldBe Right(4)
  }

  it should "transform values into errors" in {
    Right(3).flatMap(_ => Left("error")) shouldBe Left("error")
  }

  "orElse" should "propagate values" in {
    Right(3).orElse(Right(4)) shouldBe Right(3)
    Right(3).orElse(Left("error")) shouldBe Right(3)
  }

  it should "pass along the else Option when value is absent" in {
    Left("error").orElse(Right(4)) shouldBe Right(4)
    Left("error").orElse(Left("error2")) shouldBe Left("error2")
  }

  "map2" should "only return a value when both parameters are present" in {
    val f = (a: Int, b: Int) => a + b
    Right(1).map2(Right(2))(f) shouldBe Right(3)
    Right(1).map2(Left("error"))(f) shouldBe Left("error")
    Left("error").map2(Right(2))(f) shouldBe Left("error")
    Left("error").map2(Left("error"))(f) shouldBe Left("error")
  }

  "sequence" should "return a list of options iff all values passed in are present" in {
    Either.sequence(List(Right(1), Right(2), Right(3))) shouldBe Right(List(1, 2, 3))
    Either.sequence(List()) shouldBe Right(List())
    Either.sequence(List(Right(1), Left("error"), Right(3))) shouldBe Left("error")
    Either.sequence(List(Left("error"), Right(2), Left("error2"))) shouldBe Left("error")
    Either.sequence(List(Right(1), Right(2), Left("error"))) shouldBe Left("error")
  }

  "traverse" should "return a value only if all computations succeed" in {
    def oddsPass(i: Int): Either[String, Int] = if (i % 2 == 0) Left("even") else Right(i)
    Either.traverse(List(1, 3, 5))(oddsPass) shouldBe Right(List(1, 3, 5))
    Either.traverse(List(1, 2, 5))(oddsPass) shouldBe Left("even")
  }
}
