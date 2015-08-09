package images

import scala.collection.parallel.mutable.ParArray

sealed trait Image {
  val width: Int
  val height: Int
  val matrix: ParArray[Int]
}

case class ImageRGB(pixels: Array[Int], w: Int, h: Int) extends Image {
  override val width: Int = w
  override val height: Int = h
  override val matrix: ParArray[Int] = pixels.par
}

case class ImageGrey(pixels: Array[Int], w: Int, h: Int) extends Image {
  override val width: Int = w
  override val height: Int = h
  override val matrix: ParArray[Int] = pixels.par
}

case class ImageBinary(pixels: Array[Int], w: Int, h: Int) extends Image {
  override val width: Int = w
  override val height: Int = h
  override val matrix: ParArray[Int] = pixels.par
}