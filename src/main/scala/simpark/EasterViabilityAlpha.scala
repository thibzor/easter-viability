package simpark

import viabilitree.export._
import viabilitree.viability._
import viabilitree.viability.kernel._

/**
  * Created by francois_lavallee on 11/07/17.
  */
object EasterViabilityAlpha extends App {


  val eI = easterIslandAlphaControl()
  val rng = new util.Random(42)

  //In this part, only alpha  is a control
  val scale = 0.000001
  val minControl = 0.99 * scale
  val maxControl = 1 * scale
  val step = (maxControl - minControl) * 1.0 / 3.0

  // number of steps for the set of controls is small: valueMax - valueMin
  var vk: KernelComputation = KernelComputation(
    dynamic = eI.dynamic,
    depth = 18,
    zone = Vector((450.0, 32800.0), (1200.0, 12000.0), (0.0, 5000.0)), //set of constraints
    //control : only on alpha
    controls = Vector((minControl to maxControl by step))
  )
  val keyWord = "AlphaminA"+minControl+"maxA"+maxControl+"40ts"

  val (ak, steps) = approximate(vk, rng)
  println(steps)
  saveVTK3D(ak, "/tmp/EI"+vk.depth+keyWord+".vtk")

}