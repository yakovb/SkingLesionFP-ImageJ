package operations

sealed trait Operation

sealed trait PointOperation[-A,+B] extends Operation {
  def runOn(pixel: A): B
}

case class PointOp[A,B](f: A => B) extends PointOperation[A,B] {
  override def runOn(pixel: A): B = f(pixel)
}

case class PointOpRGB[A,B](redOp: Int => A, greenOp: Int => A, blueOp: Int => A)
                        (combine: (A,A,A) => B) extends PointOperation[Int,B] {
  override def runOn(pixel: Int): B = {
    val red = redOp ((pixel >> 16) & 0xff)
    val green = greenOp ((pixel >> 8) & 0xff)
    val blue = blueOp (pixel & 0xff)
    combine (red, green, blue)
  }
}

sealed trait NeighbourhoodOperation[-A,+B] extends Operation {
  def runOn(neighbourhood: List[A]): B
}

case class NeighbourhoodOp[-A,K,+B](f: List[(A,K)] => B)(kernel: List[K]) extends NeighbourhoodOperation[A,B] {
  override def runOn(neighbourhood: List[A]): B =
    f (neighbourhood zip kernel)
}

object Operation {

  import shapeless._
  object Pipeline2 extends Poly2 {

    implicit def functions[A,B,C] = at[A => B, B => C](_ andThen _)

  }


}