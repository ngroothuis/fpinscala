package fpinscala.errorhandling

import org.scalatest.{FlatSpec, Matchers}

/**
  * Test specifications for the Option class.
  */
class OptionSpec extends FlatSpec with Matchers {

  "map" should "propagate None" in {
    None.map((i: Int) => i + 1) shouldBe None
  }

  it should "transform values that are present" in {
    Some(3).map(_ + 1) shouldBe Some(4)
  }

  "getOrElse" should "get values that are present" in {
    Some(3).getOrElse(4) shouldBe 3
  }

  it should "get default when value is not present" in {
    None.getOrElse(4) shouldBe 4
  }

  "flatMap" should "propagate None" in {
    None.flatMap((i: Int) => Some(i + 1)) shouldBe None
  }

  it should "transform values into other values" in {
    Some(3).flatMap((i: Int) => Some(i + 1)) shouldBe Some(4)
  }

  it should "transform values into None" in {
    Some(3).flatMap(_ => None) shouldBe None
  }

  "orElse" should "propagate values" in {
    Some(3).orElse(Some(4)) shouldBe Some(3)
    Some(3).orElse(None) shouldBe Some(3)
  }

  it should "pass along the else Option when value is absent" in {
    None.orElse(Some(4)) shouldBe Some(4)
    None.orElse(None) shouldBe None
  }

  "variance" should "calculate the variance of a collection of numbers" in {
    Option.variance(List(1.0, 2.0, 3.0)) shouldBe Some(2.0 / 3.0)
  }

  it should "return None for an empty collection" in {
    Option.variance(List()) shouldBe None
  }

  "map2" should "only return a value when both parameters are present" in {
    val f = (a: Int, b: Int) => a + b
    Option.map2(Some(1), Some(2))(f) shouldBe Some(3)
    Option.map2(Some(1), None)(f) shouldBe None
    Option.map2(None, Some(2))(f) shouldBe None
    Option.map2(None, None)(f) shouldBe None
  }

  "sequence" should "return a list of options iff all values passed in are present" in {
    Option.sequence(List(Some(1), Some(2), Some(3))) shouldBe Some(List(1, 2, 3))
    Option.sequence(List()) shouldBe Some(List())
    Option.sequence(List(Some(1), None, Some(3))) shouldBe None
    Option.sequence(List(None, Some(2), Some(3))) shouldBe None
    Option.sequence(List(Some(1), Some(2), None)) shouldBe None
  }
}
