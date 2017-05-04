package simpark

import viabilitree.export._
import viabilitree.viability._
import viabilitree.viability.kernel._

object Parc3DViabilityKernel extends App {

  val parc = Parc3D()
  val rng = new util.Random(42)

  val vk = KernelComputation(
    dynamic = parc.dynamic,
    depth = 21,
    zone = Vector((0.0, 25000.0), (2000.0, 5000.0), (1500.0, 2500.0)),
    controls = (x: Vector[Double]) =>
      for {
        zeta <- (0.01 to 0.02 by 0.01 )
        eps <- (0.0 to 100.0 by 10.0)
      } yield Control(zeta, eps)

  )

  val (ak, steps) = approximate(vk, rng)

  println(steps)

  saveVTK3D(ak, s"/tmp/resparcCATDepth${vk.depth}2controls.vtk")
  saveHyperRectangles(vk)(ak,s"/tmp/resparcCATWithControlD${vk.depth}.txt")

  //println(volume(res))

}