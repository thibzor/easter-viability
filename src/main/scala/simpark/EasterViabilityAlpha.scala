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
  /*
  val pointA=Vector(1500.0,1200.0,150.0)

  val u2=Vector(0.0)
  val pointB=eI.dynamic(pointA,u2)
  println(pointB)
  */

  val vk = KernelComputation(
    dynamic = eI.dynamic,
    depth = 18,
    zone = Vector((1100.0, 32800.0), (2000.0, 12000.0)), //set of constraints
    //controls, first on alpha, second on s, third on c.
    controls = Vector((0.0000005 to 0.000001 by 0.0000005))
  )
  val keyWord = "Alpha"
  //keyWord = "AlphaAndS"
  //keyWord = "AlphaAndSAndC"

  val (ak, steps) = approximate(vk, rng)

  println(steps)
  //println(vk.controls(1))
  saveVTK3D(ak, "/tmp/EI"+vk.depth+keyWord+".vtk")

  //println(volume(res))

}