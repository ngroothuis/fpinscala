package fpinscala.laziness

import Stream._
trait Stream[+A] {

  def toList: List[A] = {
    this match {
      case Empty => Nil
      case Cons(h, t) => h() :: t().toList
    }
  }

  def foldRight[B](z: => B)(f: (A, => B) => B): B = // The arrow `=>` in front of the argument type `B` means that the function `f` takes its second argument by name and may choose not to evaluate it.
    this match {
      case Cons(h,t) => f(h(), t().foldRight(z)(f)) // If `f` doesn't evaluate its second argument, the recursion never occurs.
      case _ => z
    }

  def exists(p: A => Boolean): Boolean = 
    foldRight(false)((a, b) => p(a) || b) // Here `b` is the unevaluated recursive step that folds the tail of the stream. If `p(a)` returns `true`, `b` will never be evaluated and the computation terminates early.

  @annotation.tailrec
  final def find(f: A => Boolean): Option[A] = this match {
    case Empty => None
    case Cons(h, t) => if (f(h())) Some(h()) else t().find(f)
  }
  def take(n: Int): Stream[A] = (this, n) match {
    case (_, 0) => Empty
    case (Cons(h, t), n) if n > 0 => Cons(h, () => t().take(n - 1))
  }

  def drop(n: Int): Stream[A] = (this, n) match {
    case (_, 0) => this
    case (Cons(_, t), n) if n > 0 => t().drop(n - 1)
  }

  def takeWhilePatternMatch(p: A => Boolean): Stream[A] = this match {
    case Empty => Empty
    case Cons(h, t) if p(h()) => Cons(h, () => takeWhile(p))
    case Cons(_, _) => Empty
  }

  def takeWhile(p: A => Boolean): Stream[A] = {
    foldRight(empty[A])((a, b) => if (p(a)) cons(a, b) else empty)
  }

  def forAll(p: A => Boolean): Boolean = {
    foldRight(true)((a, b)=> p(a) && b)
  }

  def headOption: Option[A] = foldRight[Option[A]](None)((a, _) => Some(a))

  def map[B](f: A => B): Stream[B] = {
    foldRight(empty[B])((a, b) => cons(f(a), b))
  }

  def filter(f: A => Boolean): Stream[A] = {
    foldRight(empty[A])((a, b) => if (f(a)) cons(a, b) else b)
  }

  def append[B >: A](a2: => Stream[B]): Stream[B] = {
    foldRight(a2)((a, b) => cons(a, b))
  }

  def flatMap[B](f: A => Stream[B]): Stream[B] = {
    foldRight(empty[B])((a, b) => f(a).append(b))
  }

  def startsWith[B](s: Stream[B]): Boolean = zipAll(s).foldRight(true)((ab, c) => ab match {
    case (None, _) => false
    case (Some(_), None) => true
    case (Some(a), Some(b)) => (a == b) && c
  })

  def mapUnfold[B](f: A => B): Stream[B] = unfold(this) {
    case Empty => None
    case Cons(h, t) => Some((f(h()), t()))
  }

  def takeUnfold(n: Int): Stream[A] = unfold((n, this)) {
    case (0, _) => None
    case (nr, Cons(h, t)) if nr > 0 => Some((h(), (nr - 1, t())))
  }

  def takeWhileUnfold(p: A => Boolean): Stream[A] = unfold(this) {
    case Empty => None
    case Cons(h, t) if p(h()) => Some((h(), t()))
    case Cons(_, _) => None
  }

  def zipWith[B, C](bs: Stream[B])(f: (A, B) => C): Stream[C] = unfold((this, bs)) {
    case (Empty, _) | (_, Empty) => None
    case (Cons(ha, ta), Cons(hb, tb)) => Some(f(ha(), hb()), (ta(), tb()))
  }

  def zipAll[B](bs: Stream[B]): Stream[(Option[A], Option[B])] = unfold((this, bs)) {
    case (Empty, Empty) => None
    case (Empty, Cons(hb, tb)) => Some(((None, Some(hb())), (Empty, tb())))
    case (Cons(ha, ta), Empty) => Some(((Some(ha()), None), (ta(), Empty)))
    case (Cons(ha, ta), Cons(hb, tb)) => Some(((Some(ha()), Some(hb())), (ta(), tb())))
  }

  def tails: Stream[Stream[A]] = unfold(this) {
    case Empty => None
    case s @ Cons(h, t) => Some((s, t()))
  }

  def scanRight[B](z: => B)(f: (A, => B) => B): Stream[B] =
  foldRight(cons(z, Empty))((a, b) => b.headOption match {
    case Some(bh) => cons(f(a, bh), b)
  })
}

case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty 
    else cons(as.head, apply(as.tail: _*))

  val ones: Stream[Int] = Stream.cons(1, ones)

  def constant[A](a: A): Stream[A] = cons(a, constant(a))

  def from(n: Int): Stream[Int] = cons(n, from(n + 1))

  def fibs: Stream[Int] = {
    def fibNext(a: Int, b: Int): Stream[Int] = {
      val c = a + b
      cons(c, fibNext(b, c))
    }
    Stream(0, 1).append(fibNext(0, 1))
  }

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = {
    f(z).fold(empty[A])({case (a, s) => cons(a, unfold(s)(f))})
  }

  val onesUnfold: Stream[Int] = unfold(None)(_ => Some((1, None)))

  def constantUnfold[A](a: A): Stream[A] = unfold(None)(_ => Some((a, None)))

  def fromUnfold(n: Int): Stream[Int] = unfold(n)(n => Some(n, n + 1))

  def fibsUnfold: Stream[Int] = unfold((0, 1))({case (a: Int, b: Int) => Some(a, (b, a + b))})
}