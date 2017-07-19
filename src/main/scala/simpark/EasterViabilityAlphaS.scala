package simpark

import viabilitree.export._
import viabilitree.viability._
import viabilitree.viability.kernel._
/**
  * Created by francois_lavallee on 12/07/17.
  */
object EasterViabilityAlphaS extends App {

  //In this part, alpha and s are controls
  val eI = easterIslandAlphaSControls()
  val rng = new util.Random(42)
  //In this part, only alpha  is a control
  val scaleA = 0.000001
  val minControlA = 0.75 * scaleA
  val maxControlA = 1 * scaleA
  val stepA = (maxControlA - minControlA) * 1.0 / 3.0
  val scaleS = 0.004
  val minControls = 0.75 * scaleS
  val maxControls = 1 * scaleS

  val stepS = (maxControls - minControls) * 1.0 / 2.0
  // number of steps for the set of controls is the smallest : valueMax - valueMin

  println(maxControls to maxControls by 0.1)
  val vk1c = KernelComputation(
    dynamic = eI.dynamic,
    depth = 18,
    zone = Vector((450.0, 32800.0), (1200.0, 12000.0), (0.0, 5000.0)), //set of constraints
    //controls, first on alpha, second on s with a singleton for s
    controls = Vector((minControlA to maxControlA by stepA), (maxControls to maxControls by 0.1))
  )

  val vk2c = KernelComputation(
    dynamic = eI.dynamic,
    depth = 18,
    zone = Vector((450.0, 32800.0), (1200.0, 12000.0), (0.0, 5000.0)), //set of constraints
    //controls, first on alpha, second on s
    controls = Vector((minControlA to maxControlA by stepA), (minControls to maxControls by stepS))
  )

  val keyWord1c = "AlphaAndSminA"+"-"+minControlA+"-"+minControlA+"Sequals"+maxControls
  val keyWord2c = "AlphaAndSminAS"+"-"+minControlA+"-"+minControls+"-"+"maxAS"+maxControlA+"-"+maxControls
  val (ak1c, steps1c) = approximate(vk1c, rng)
  val (ak2c, steps2c) = approximate(vk2c, rng)
  println(steps1c)
  saveVTK3D(ak1c, "/tmp/EI"+vk1c.depth+keyWord1c+".vtk")
  saveHyperRectangles(vk1c)(ak1c,s"/tmp/EI${keyWord1c}WithControlD${vk1c.depth}.txt")

  println(steps2c)
  saveVTK3D(ak2c, "/tmp/EI"+vk2c.depth+keyWord2c+".vtk")
  saveHyperRectangles(vk2c)(ak2c,s"/tmp/EI${keyWord2c}WithControlD${vk2c.depth}.txt")
}
