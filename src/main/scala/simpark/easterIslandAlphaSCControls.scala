package simpark

import viabilitree.model.Dynamic

/**
  * Created by francois_lavallee on 30/06/17.
  */
case class easterIslandAlphaSCControls(integrationStep: Double = 0.25,
                                       timeStep: Double = 1,
                                       b: Double = 0.002,
                                       d: Double = 0.012,
                                       r: Double = 0.004,
                                       K: Double = 12000,
                                       rho: Double = 0.1,
                                       m: Double = 1.0
                                      ) {
  def dynamic(state: Vector[Double], control: Vector[Double]) = {
    def xDot(state: Vector[Double], t: Double) = (b - d * math.exp(-state(2) / (rho * state(0)))) * state(0)
    def yDot(state: Vector[Double], t: Double) = r*state(1)*(1-state(1)/K)-control(0)*state(0)*state(1)
    def zDot(state: Vector[Double], t: Double) = control(0) * state(0) * state(1) - control(1) * state(0) * (1 - math.exp(-state(2) / (rho * state(0)))) - control(2) * state(2)
    val dynamic = Dynamic(xDot, yDot, zDot)
    dynamic.integrate(state.toArray, integrationStep, timeStep)
  }
}
