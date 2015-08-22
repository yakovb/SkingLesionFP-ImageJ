import ij.ImagePlus
import ij.plugin.filter.PlugInFilter
import ij.plugin.filter.PlugInFilter._
import ij.process.ImageProcessor
import images.ParImage
import operations.{Funcs, PointTraverse, TransformSimple}

class Inverter_ extends PlugInFilter {
  override def setup(arg: String, imp: ImagePlus): Int =
    DOES_8G + DOES_STACKS + SUPPORTS_MASKING

  override def run(ip: ImageProcessor): Unit = {
    val pixels = ip.getPixels.asInstanceOf[Array[Byte]]
    val image = ParImage[Byte](pixels.par, ip.getWidth, ip.getHeight)

    val transformedImage = TransformSimple[Byte,Byte](image, PointTraverse(), Funcs.invert) transform

    ip.setPixels(transformedImage.matrix.toArray)
  }
}
