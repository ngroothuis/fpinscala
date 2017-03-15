package fpinscala.errorhandling

sealed trait ErrorAccumulator[+E,+A] {
  def orElse[EE >: E, B >: A](b: => ErrorAccumulator[EE, B]): ErrorAccumulator[EE, B] = this match {
    case Errors(es) => b match {
      case Errors(bes) => Errors(es ++ bes)
      case Value(bv) => Value(bv)
    }
    case Value(v) => Value(v)
  }

  def map2[EE >: E, B, C](b: ErrorAccumulator[EE, B])(f: (A, B) => C): ErrorAccumulator[EE, C] =
    this match {
      case Errors(es) => b match {
        case Errors(bes) => Errors(es ++ bes)
        case Value(_) => Errors(es)
      }
      case Value(v) => b match {
        case Errors(bes) => Errors(bes)
        case Value(bv) => Value(f(v, bv))
      }
    }
}
case class Errors[+E](get: List[E]) extends ErrorAccumulator[E,Nothing]
case class Value[+A](get: A) extends ErrorAccumulator[Nothing,A]

object ErrorAccumulator {
  def traverse[E,A,B](es: List[A])(f: A => ErrorAccumulator[E, B]): ErrorAccumulator[E, List[B]] = es match {
    case Nil => Value(Nil)
    case h :: t => f(h) match {
      case Errors(hes) => traverse(t)(f) match {
        case Errors(tes) => Errors(hes ++ tes)
        case Value(_) => Errors(hes)
      }
      case Value(hv) => traverse(t)(f) match {
        case Errors(tes) => Errors(tes)
        case Value(tvs) => Value(hv :: tvs)
      }
    }
  }

  def sequence[E,A](es: List[ErrorAccumulator[E,A]]): ErrorAccumulator[E,List[A]] = traverse(es)(identity)
}
