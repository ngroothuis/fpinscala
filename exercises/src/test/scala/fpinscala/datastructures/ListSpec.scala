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

  "dropWhile" should "drop the first two elements of a simple list" in {
    dropWhile(List(2, 4, 5), (n: Int) => n % 2 == 0) shouldBe List(5)
  }

  it should "drop all elements of a list when all match the predicate" in {
    dropWhile(List(2, 4, 6), (n: Int) => n % 2 == 0) shouldBe Nil
  }

  "init" should "drop the last element of a simple list" in {
    init(List(1, 2, 3)) shouldBe List(1, 2)
  }

  it should "throw an exception on Nil" in {
    a[NotImplementedError] shouldBe thrownBy(init(Nil))
  }

  "foldRight" should "recreate a list when the base is Nil and f is Cons" in {
    foldRight(List(1,2,3), Nil:List[Int])(Cons(_,_)) shouldBe List(1, 2, 3)
  }

  "length" should "compute the length of a list" in {
    List.length(List(1, 2, 3)) shouldBe 3
  }

  "sumLeft" should "sum a list" in {
    sumLeft(List(1, 2, 3, 4)) shouldBe 10
  }

  "productLeft" should "multiply a list" in {
    productLeft(List(1, 2, 3, 4)) shouldBe 24
  }

  "lengthLeft" should "compute the length of a list" in {
    List.lengthLeft(List(1, 2, 3, 4)) shouldBe 4
  }

  "reverseFold" should "reverse a list" in {
    reverseFold(List(1, 2, 3, 4)) shouldBe List(4, 3, 2, 1)
  }
}
