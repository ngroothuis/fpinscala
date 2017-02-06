package fpinscala.datastructures

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]


object Tree {
  /*
  def size[A](tree: Tree[A]) : Int = {
    tree match {
      case Leaf(_) => 1
      case Branch(l, r) => 1 + size(l) + size(r)
    }
  }

  def maximum(tree: Tree[Int]): Int = {
    tree match {
      case Leaf(v) => v
      case Branch(l,r) => maximum(l) max maximum(r)
    }
  }

  def depth[A](tree: Tree[A]): Int = {
    tree match {
      case Leaf(_) => 0
      case Branch(l, r) => (1 + depth(l)) max (1 + depth(r))
    }
  }

  def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = {
    tree match {
      case Leaf(v) => Leaf(f(v))
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
    }
  }
  */

  /* This is akin to a right list fold */
  def fold[A, B](tree: Tree[A])(lf: A => B)(bf: (B, B) => B): B = {
    tree match {
      case Leaf(v) => lf(v)
      case Branch(l, r) => bf(fold(l)(lf)(bf), fold(r)(lf)(bf))
    }
  }

  def size[A](tree: Tree[A]): Int = {
    fold(tree)(_ => 1)((l, r) => 1 + l + r)
  }

  def maximum(tree: Tree[Int]): Int = {
    fold(tree)((n: Int) => n)((l: Int, r: Int) => l max r)
  }

  def depth[A](tree: Tree[A]): Int = {
    fold(tree)(_ => 0)((l, r) => (1 + l) max (1 + r))
  }

  def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = {
    fold[A, Tree[B]](tree)(v => Leaf(f(v)))((l,r) => Branch(l, r))
  }
}