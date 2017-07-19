package simpark

import viabilitree.model.Dynamic

/**
  * Created by thibaut on 09/05/17.
  */
case class easterIslandAlphaControl(integrationStep: Double = 1,
                                    timeStep: Double = 300,
                                    b: Double = 0.002,
                                    d: Double = 0.012,
                                    r: Double = 0.004,
                                    K: Double = 12000,
                                    s: Double = 0.004,
                                    rho: Double = 0.1,
                                    c: Double = 0//,
                                    //m: Double = 1.0
                         )     {
  def dynamic(state: Vector[Double], control: Vector[Double]) = {
    def xDot(state: Vector[Double], t: Double) = (b - d * math.exp(-state(2) / (rho * state(0)))) * state(0)
    def yDot(state: Vector[Double], t: Double) = r*state(1)*(1-state(1)/K)-control(0)*state(0)*state(1)
    def zDot(state: Vector[Double], t: Double) = control(0) * state(0) * state(1) - s * state(0) * (1 - math.exp(-state(2) / (rho * state(0)))) - c * state(2)
    val dynamic = Dynamic(xDot, yDot, zDot)
    dynamic.integrate(state.toArray, integrationStep, timeStep)
  }


}
