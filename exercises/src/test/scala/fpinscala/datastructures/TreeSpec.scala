package fpinscala.datastructures

import fpinscala.datastructures.Tree._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test specifications for the Tree class.
  */
class TreeSpec extends FlatSpec with Matchers {

  val oneNodeTree = Leaf(1)
  val fiveNodeTree = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))

  "oneNodeTree" should "have a depth of 0" in {
    depth(oneNodeTree) shouldBe 1
  }

  "fiveNodeTree" should "have five nodes" in {
    Tree.size(fiveNodeTree) shouldBe 5
  }

  it should "have a max value of 3" in {
    maximum(fiveNodeTree) shouldBe 3
  }

  it should "have a depth of 2" in {
    depth(fiveNodeTree) shouldBe 2
  }

  it should "be mappable" in {
    map(fiveNodeTree)(_ * 2) shouldBe Branch(Branch(Leaf(2), Leaf(4)), Leaf(6))
  }
}
