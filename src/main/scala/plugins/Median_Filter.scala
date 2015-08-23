import ij.ImagePlus
import ij.plugin.filter.PlugInFilter
import ij.plugin.filter.PlugInFilter._
import ij.process.{ByteProcessor, ImageProcessor}
import images.ParImage
import operations.{BorderAction, Funcs, NeighbourTraverse, TransformNeighbourhood}

class Median_Filter extends PlugInFilter {
  override def setup(arg: String, imp: ImagePlus): Int =
  DOES_8G + DOES_STACKS + SUPPORTS_MASKING

  override def run(ip: ImageProcessor): Unit = {
    val pixels = ip.getPixels.asInstanceOf[Array[Byte]]
    val image = ParImage[Byte](pixels.par, ip.getWidth, ip.getHeight)

    val transformedImage = TransformNeighbourhood[Byte,Byte](image, NeighbourTraverse(), Funcs.medianFilter)(BorderAction.Crop) transform

    val result = new ByteProcessor(transformedImage.width, transformedImage.height, transformedImage.matrix.toArray)
    new ImagePlus("grey pic", result) show()
  }
}