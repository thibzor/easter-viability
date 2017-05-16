/*
 * Copyright (C) 10/10/13 Isabelle Alvarez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simpark

import viabilitree.export._
import viabilitree.viability._
import viabilitree.viability.kernel._

object LakeViabilityKernel extends App {

  val choice = 1
  if (choice == 0) {
    val lake = Lake()
    val rng = new util.Random(42)

    val vk = KernelComputation(
      dynamic = lake.dynamic,
      depth = 12,
      zone = Vector((0.1, 1.0), (0.0, 1.4)), //set of constraints
      //    controls = Vector((-0.09 to 0.09 by 0.01))
      /*
    controls = (x: Vector[Double]) =>
      for {
        c1 <- (0.0 to 0.9 by 0.1)
        c2 <- (-0;9 to 0.01 * x(1) by 1.0)
      } yield Control(c1, c2)
*/
      controls = Vector((-0.09 to 0.09 by 0.01), (-0.09 to 0.09 by 0.01))
    )

    val (ak, steps) = approximate(vk, rng)

    println(steps)
    //println(vk.controls(1))

    saveVTK2D(ak, "/tmp/resparc.vtk")

    //println(volume(res))
  } else {

    val eI = EasterIsland()
    val rng = new util.Random(42)
    /*
    val pointA=Vector(1500.0,1200.0,150.0)

    val u2=Vector(0.0)
    val pointB=eI.dynamic(pointA,u2)
    println(pointB)
    */

    val vk = KernelComputation(
      dynamic = eI.dynamic,
      depth = 12,
      zone = Vector((1500.0, 7500.0), (1200.0, 12000.0), (150.0, 6000.0)), //set of constraints
      //    controls = Vector((-0.09 to 0.09 by 0.01))
      //controls = Vector((-0.09 to 0.09 by 0.01), (-0.09 to 0.09 by 0.01))
      controls = Vector(-0.1 to 0.1 by 0.1)
    )

    val (ak, steps) = approximate(vk, rng)

    println(steps)
    //println(vk.controls(1))
    saveVTK3D(ak, "/tmp/EI.vtk")

    //println(volume(res))

  }
}
