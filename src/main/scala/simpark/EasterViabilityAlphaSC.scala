package simpark

import viabilitree.export.saveVTK3D
import viabilitree.viability.kernel.{KernelComputation, approximate}

/**
  * Created by francois_lavallee on 12/07/17.
  */
object EasterViabilityAlphaSC extends App {

  ////In this part, alpha, s and c are controls
  //val keyWord = "AlphaAndSAndC"
  val eI = easterIslandAlphaSCControls()
  val rng = new util.Random(42)
  //Alpha
  val scaleA = 0.000001
  val minControlA = 0.75 * scaleA
  val maxControlA = 1.0 * scaleA
  val stepA = (maxControlA - minControlA) * 1.0 / 3.0
  //s bifurcation a
  val scaleS = 0.004
  val minControlS = 0.85 * scaleS
  val maxControlS = 1.0 * scaleS
  val stepS = (maxControlS - minControlS) * 1.0 / 3.0
  //c bifurcation a 3.6  E  - 3
  val scaleC = 0.002
  val minControlC = 0.75 * scaleC
  val maxControlC = 1.0 * scaleC
  val stepC = (maxControlC - minControlC) * 1.0 / 3.0
  // number of steps for the set of controls is the smallest : valueMax - valueMin

  /*val vk = KernelComputation(
    dynamic = eI.dynamic,
    depth = 18,
    zone = Vector((450.0, 32800.0), (1200.0, 12000.0), (0.0, 5000.0)), //set of constraints
    //controls, first on alpha, second on s, third on c.
    controls = Vector((minControlA to maxControlA by stepA), (minControlS to maxControlS by stepS) ,(minControlC to maxControlC by stepC))
  )*/
  val keyWord = "AlphaAndSAndCminASC"+"-"+minControlA+"-"+minControlS+"-"+minControlC+"maxASC"+maxControlA+"-"+maxControlS+"-"+maxControlC

  //val (ak, steps) = approximate(vk, rng)
  //println(steps)
  //saveVTK3D(ak, "/tmp/EI"+vk.depth+keyWord+".vtk")
}
