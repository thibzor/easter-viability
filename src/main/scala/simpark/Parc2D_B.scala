package simpark

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

import viabilitree.model._

case class Parc2D_B(
                     integrationStep: Double = 0.0001,
                     timeStep: Double = 0.001,
                     g: Double = 1.0,
                     M: Double = 5000.0,
                     a: Double = 100.0,
                     c: Double = 0.01,
                     eta: Double = 0.0005,
                     d: Double = 1.0,
                     phi: Double =1.0,
                     p: Double =0.2,
                     l: Double = 0.01
                     // valeurs de controle: eps control(1) et zeta control(0)
                   ) {

  def dynamic(state: Vector[Double], control: Vector[Double]) = {
    // A: state(0), T: state(1), E: state(2)
    // def ADot(state: Vector[Double], t: Double) =state(0)*g*(1-state(0)/( 1+M/(1+eta)))
    def ADot (state: Vector[Double], t: Double)= state(0)*g*(1-state(0)/( 1+M/(1+eta*state(1)/(control(1)+1)) ) )-control(0)*l*state(1)*state(0)-p*state(0)
    def TDot(state: Vector[Double], t: Double) = state(1) * (  -  c * state(1)/(state(1)+phi)-d)+ a*control(0)*state(0)
    val dynamic = Dynamic(ADot, TDot)
    dynamic.integrate(state.toArray, integrationStep, timeStep)
  }

}
