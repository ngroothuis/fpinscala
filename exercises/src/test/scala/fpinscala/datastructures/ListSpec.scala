package fpinscala.datastructures

import org.scalatest.{FlatSpec, Matchers, _}

import List._

/**
  * Test specifications for the List class.
  */
class ListSpec extends FlatSpec with Matchers {

  "Exercise 3.1 match expression" should "be 3" in {
   x shouldBe 3
  }

  "tail" should "take the tail of a simple list" in {
    tail(List(1, 2, 3)) shouldBe List(2, 3)
  }

  it should "throw an exception on Nil" in {
    a[NotImplementedError] shouldBe thrownBy(tail(Nil))
  }

  "setHead" should "replace the head of a simple list" in {
    setHead(List(1, 2, 3), 4) shouldBe List(4, 2, 3)
  }

  it should "throw an exception on Nil" in {
    a[NotImplementedError] shouldBe thrownBy(setHead(Nil, 3))
  }

  "drop" should "drop the first two elements of a simple list" in {
    drop(List(1, 2, 3), 2) shouldBe List(3)
  }

  it should "throw an exception on negative n" in {
    a[NotImplementedError] shouldBe thrownBy(drop(List(1, 2), -1))
  }

  it should "throw an exception when dropping more elements than exist" in {
    a[NotImplementedError] shouldBe thrownBy(drop(List(1, 2), 4))
  }
}
