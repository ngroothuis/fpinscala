package fpinscala.datastructures

sealed trait List[+A]

// `List` data type, parameterized on a type, `A`
case object Nil extends List[Nothing]

// A `List` data constructor representing the empty list
/* Another data constructor, representing nonempty lists. Note that `tail` is another `List[A]`,
which may be `Nil` or another `Cons`.
 */
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  // `List` companion object. Contains functions for creating and working with lists.
  def sum(ints: List[Int]): Int = ints match {
    // A function that uses pattern matching to add up a list of integers
    case Nil => 0 // The sum of the empty list is 0.
    case Cons(x, xs) => x + sum(xs) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] = // Variadic function syntax
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  val x = List(1, 2, 3, 4, 5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101
  }

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil => a2
      case Cons(h, t) => Cons(h, append(t, a2))
    }

  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = // Utility functions
    as match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }

  def sum2(ns: List[Int]) =
    foldRight(ns, 0)((x, y) => x + y)

  def product2(ns: List[Double]) =
    foldRight(ns, 1.0)(_ * _) // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar


  def tail[A](l: List[A]): List[A] =
    l match {
      case Nil => ???
      case Cons(_, t) => t
    }

  def setHead[A](l: List[A], h: A): List[A] = {
    l match {
      case Nil => ???
      case Cons(_, t) => Cons(h, t)
    }
  }

  @annotation.tailrec
  def drop[A](l: List[A], n: Int): List[A] = {
    if (n == 0)
      l
    else if (n > 0) drop(tail(l), n - 1)
    else ???
  }

  @annotation.tailrec
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = {
    l match {
      case Nil => Nil
      case Cons(h, t) => if (f(h)) dropWhile(t, f) else l
    }
  }

  def init[A](l: List[A]): List[A] = {
    /*
    Non-tail-recursive implementation.

    l match {
      case Nil => ???
      case Cons(_, Nil) => Nil
      case Cons(h, t) => Cons(h, init(t))
    }
    */
    @annotation.tailrec
    def go(l: List[A], acc: List[A]): List[A] = {
      l match {
        case Nil => ???
        case Cons(_, Nil) => acc
        case Cons(h, t) => go(t, Cons(h, acc))
      }
    }

    @annotation.tailrec
    def reverse(l: List[A], acc: List[A]): List[A] = {
      l match {
        case Nil => acc
        case Cons(h, t) => reverse(t, Cons(h, acc))
      }
    }

    reverse(go(l, Nil), Nil)
  }

  /* Exercise 3.7
  *
  * product() implemented using foldRight() cannot short-circuit when it hits a zero.  The call stack is
  * completely built up before f() is ever evaluated.
  *
  * Tamas' note: we can if we can use lazy evaluation.
  * */


  def length[A](l: List[A]): Int = {
    foldRight(l, 0)((_, n) => n + 1)
  }

  @annotation.tailrec
  def foldLeft[A, B](l: List[A], z: B)(f: (B, A) => B): B = {
    l match {
      case Nil => z
      case Cons(h, t) => foldLeft(t, f(z, h))(f)
    }
  }

  def sumLeft(ns: List[Int]) =
    foldLeft(ns, 0)((x, y) => x + y)

  def productLeft(ns: List[Double]) =
    foldLeft(ns, 1.0)(_ * _) // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar

  def lengthLeft[A](l: List[A]): Int = {
    foldLeft(l, 0)((n, _) => n + 1)
  }

  def reverseFold[A](l: List[A]): List[A] = {
    foldLeft(l, Nil:List[A])((acc, a)=>Cons(a, acc))
  }

  def foldRightUsingFoldLeft[A, B](as: List[A], z: B)(f: (A, B) => B): B = {
    foldLeft(
      foldLeft(as, Nil:List[A])((t, h) => Cons(h,t)), // reverse
      z)((b, a) => f(a, b))
  }

  def foldLeftUsingFoldRight[A, B](as: List[A], z: B)(f: (B, A) => B): B = {
    foldRight(
      foldRight(as, Nil:List[A])((h, t) =>
        foldRight(t, Cons(h, Nil:List[A]))(Cons(_, _))), // append h to t
      z)((a, b) => f(b, a))
  }

  def append[A](as: List[A], a: A): List[A] = {
      foldRight(as, Cons(a, Nil:List[A]))(Cons(_, _))
  }

  def flatten[A](ll: List[List[A]]): List[A] = {
    foldRight(ll, Nil:List[A])((l, acc) => concat(l, acc))
  }

  def concat[A](l1: List[A], l2: List[A]): List[A] = {
    foldRight(l1, l2)((a, b) => Cons(a, b))
  }

  // Can I get this in linear time with foldLeft?

  def flattenLeft[A](ll: List[List[A]]): List[A] = {
    foldLeft(ll, Nil:List[A])((l, acc) => concat(l, acc))
  }

  def concatLeft[A](l1: List[A], l2: List[A]): List[A] = {
    foldLeft(l2, l1)((b, a) => Cons(a, b))
  }

  def map[A, B](l: List[A])(f: A => B): List[B] = {
    foldRight(l, Nil: List[B])((a, bs) => Cons(f(a), bs))
  }

  def filter[A](as: List[A])(f: A => Boolean): List[A] = {
    as match {
      case Nil => Nil
      case Cons(h, t) => if (f(h)) Cons(h, filter(t)(f)) else filter(t)(f)
    }
  }

  def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] = {
    foldRight(as, Nil:List[B])((a: A, bs: List[B]) =>
    foldRight(f(a), bs)((fa: B, acc: List[B]) => Cons(fa, acc)))
  }

  def filterUsingFlatmap[A](as: List[A])(f: A => Boolean): List[A] = {
    flatMap(as)((a: A) => if (f(a)) Cons(a, Nil) else Nil)
  }

  def addCorrespondingElements(as: List[Int], bs: List[Int]): List[Int] = {
    (as, bs) match {
      case (Nil, Nil) => Nil
      case (_, Nil) => ???
      case (Nil, _) => ???
      case (Cons(ha, ta), Cons(hb, tb)) => Cons(ha + hb, addCorrespondingElements(ta, tb))
    }
  }

  def zipWith[A, B, C](as: List[A], bs: List[B])(f: (A, B) => C): List[C] = {
    (as, bs) match {
      case (Nil, Nil) => Nil
      case (_, Nil) => ???
      case (Nil, _) => ???
      case (Cons(ha, ta), Cons(hb, tb)) => Cons(f(ha, hb), zipWith(ta, tb)(f))
    }
  }

  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = {
    sub match {
      case Nil => true
      case Cons(subh, subt) =>
        sup match {
          case Nil => false
          case Cons(suph, supt) => ((suph == subh) && hasSubsequence(supt, subt)) || hasSubsequence(supt, sub)
        }
    }
  }
}
