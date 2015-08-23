import ij.ImagePlus
import ij.plugin.filter.PlugInFilter
import ij.plugin.filter.PlugInFilter._
import ij.process.{ByteProcessor, ImageProcessor}
import images.ParImage
import operations.{BorderAction, Funcs, NeighbourTraverse, TransformNeighbourhood}

class Identity_Filter extends PlugInFilter {
  override def setup(arg: String, imp: ImagePlus): Int =
    DOES_8G + DOES_STACKS + SUPPORTS_MASKING

  override def run(ip: ImageProcessor): Unit = {
    val pixels = ip.getPixels.asInstanceOf[Array[Byte]]
    val image = ParImage[Byte](pixels.par, ip.getWidth, ip.getHeight)

    val transformedImage = TransformNeighbourhood[Byte,Byte](image, NeighbourTraverse(), Funcs.id_filter)(BorderAction.Crop) transform

    println("w: "+ transformedImage.width + "; h: " + transformedImage.height + "; length: "+
    transformedImage.width * transformedImage.height+ "; matrix size: "+ transformedImage.matrix.size)
    val result = new ByteProcessor(transformedImage.width - 1, transformedImage.height - 1, transformedImage.matrix.toArray)
    new ImagePlus("grey pic", result) show()
  }
}
