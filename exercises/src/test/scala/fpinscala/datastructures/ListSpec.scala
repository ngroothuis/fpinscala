package fpinscala.datastructures

import org.scalatest.{FlatSpec, Matchers, _}

import List._

/**
  * Test specifications for the List class.
  */
class ListSpec extends FlatSpec with Matchers {
/*
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


  "foldLeftUsingFoldRight" should "fold left" in {
    foldLeftUsingFoldRight(List(4, 3, 2, 1), 10)(_ - _) shouldBe 0
  }

  "foldRightUsingFoldLeft" should "fold right" in {
    foldRightUsingFoldLeft(List(4, 3, 2, 1), 10)(_ - _) shouldBe 12
  }

  "filter" should "filter out odd numbers" in {
    filter(List(1, 2, 3, 4))((n:Int) => n % 2 != 1) shouldBe List(2, 4)
  }

  "flatMap(List(1,2,3))(i => List(i,i))" should "result in List(1,1,2,2,3,3)" in {
    flatMap(List(1,2,3))(i => List(i,i)) shouldBe List(1, 1, 2, 2, 3, 3)
  }

  "filterUsingFlatmap" should "filter out odd numbers" in {
    filterUsingFlatmap(List(1, 2, 3, 4))((n:Int) => n % 2 != 1) shouldBe List(2, 4)
  }

  "addCorrespondingElements" should "add two lists" in {
    addCorrespondingElements(List(1, 2, 3), List(4, 5, 6)) shouldBe List(5, 7, 9)
  }

  "zipWith" should "concatenate two lists pairwise" in {
    zipWith(List("foo", "bar", "baz"), List("bar", "baz", "xyzzy"))(_+_) shouldBe List("foobar", "barbaz", "bazxyzzy")
  }

  "List(1, 2, 3, 4)" should "have List(1, 2) as a sublist" in {
    hasSubsequence(List(1, 2, 3, 4), List(1, 2)) shouldBe true
  }

  "List(1, 2, 3, 4)" should "have List(3, 4) as a sublist" in {
    hasSubsequence(List(1, 2, 3, 4), List(3, 4)) shouldBe true
  }

  "List(1, 2, 3, 4)" should "have List(4) as a sublist" in {
    hasSubsequence(List(1, 2, 3, 4), List(4)) shouldBe true
  }
  */

  "List(1, 2, 3, 4)" should "not have List(3, 2) as a sublist" in {
    hasSubsequence(List(1, 2, 3, 4), List(3, 2)) shouldBe false
  }

  "List(1, 2, 3, 4)" should "not have List(5) as a sublist" in {
    hasSubsequence(List(1, 2, 3, 4), List(5)) shouldBe false
  }
}
