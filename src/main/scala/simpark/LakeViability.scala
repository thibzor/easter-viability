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
    var keyWord = ""
    val dynamicChoice = 2
    if (dynamicChoice == 1) {
      val eI = easterIslandAlphaControl()
      val rng = new util.Random(42)
      //In this part, only alpha  is a control
      val scale = 0.000001
      val minControl = 0.99 * scale
      val maxControl = 1 * scale
      val step = (maxControl - minControl) * 1.0 / 3.0
      // number of steps for the set of controls is the smallest : valueMax - valueMin
      var vk: KernelComputation = KernelComputation(
        dynamic = eI.dynamic,
        depth = 18,
        zone = Vector((450.0, 32800.0), (1200.0, 12000.0), (0.0, 5000.0)), //set of constraints
        //controls, first on alpha, second on s, third on c.
        controls = Vector((minControl to maxControl by step))
      )
      keyWord = "AlphaminA"+minControl+"maxA"+maxControl+"ts40"

      val (ak, steps) = approximate(vk, rng)
      println(steps)
      saveVTK3D(ak, "/tmp/EI"+vk.depth+keyWord+".vtk")

    } else {
      if ( dynamicChoice == 2 ){
        ////In this part, alpha and s are controls

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
        val stepS = (maxControls - minControls) * 1.0 / 3.0
        // number of steps for the set of controls is the smallest : valueMax - valueMin
        val vk = KernelComputation(
          dynamic = eI.dynamic,
          depth = 18,
          zone = Vector((450.0, 32800.0), (1200.0, 12000.0), (0.0, 5000.0)), //set of constraints
          //controls, first on alpha, second on s, third on c.
          controls = Vector((minControlA to maxControlA by stepA), (minControls to maxControls by stepS))
        )
        keyWord = "AlphaAndSminAS"+"-"+minControlA+"-"+minControls+"-"+"maxAS"+maxControlA+"-"+maxControls

        val (ak, steps) = approximate(vk, rng)
        println(steps)
        //saveVTK3D(ak, "/tmp/EI"+vk.depth+keyWord+".vtk")
        saveHyperRectangles(vk)(ak,s"/tmp/EI${keyWord}WithControlD${vk.depth}.txt")

      } else {
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
        val vk = KernelComputation(
          dynamic = eI.dynamic,
          depth = 18,
          zone = Vector((450.0, 32800.0), (1200.0, 12000.0), (0.0, 5000.0)), //set of constraints
          //controls, first on alpha, second on s, third on c.
          controls = Vector((minControlA to maxControlA by stepA), (minControlS to maxControlS by stepS) ,(minControlC to maxControlC by stepC))
        )
        keyWord = "AlphaAndSAndCminASC"+"-"+minControlA+"-"+minControlS+"-"+minControlC+"maxASC"+maxControlA+"-"+maxControlS+"-"+maxControlC

        val (ak, steps) = approximate(vk, rng)
        println(steps)
        saveVTK3D(ak, "/tmp/EI"+vk.depth+keyWord+".vtk")
      }
    }

    //println("Vol= "+volume(res))

  }
}
