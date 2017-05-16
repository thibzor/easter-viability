package simpark

import viabilitree.model.Dynamic

/**
  * Created by thibaut on 09/05/17.
  */
case class EasterIsland( integrationStep: Double = 1.0,
                         timeStep: Double = 20.0,
                         b: Double = 0.002,
                         d: Double = 0.012,
                         r: Double = 0.004,
                         K: Double = 12000,
                         s: Double = 0.004,
                         rho: Double = 0.1,
                         c: Double = 0,
                         m: Double = 1.0
                        // with alpha = 2 * alphaStar, it is probable that the kernel viability is non empty
                         )     {
  val alpha: Double = 3.6 * (s*(1-b/d)+rho*c*math.log(d/b))*1.0/K
  //TODO here, without control
  def dynamic(state: Vector[Double], control: Vector[Double]) = {
    def xDot(state: Vector[Double], t: Double) = (b - d * math.exp(-state(2) / (rho * state(0)))) * state(0)
    // def yDot(state: Array[Double], t: Double) = b*state(1)-r*math.pow(state(1),8)/(pow(m,8)+pow(state(1),8))
    def yDot(state: Vector[Double], t: Double) = r*state(1)*(1-state(1)/K)-alpha*state(0)*state(1)
    def zDot(state: Vector[Double], t: Double) = alpha * state(0) * state(1) - s * state(0) * (1 - math.exp(-state(2) / (rho * state(0)))) - c * state(2)
    //def alphaDot(state: Vector[Double], t: Double) = 0
    val dynamic = Dynamic(xDot, yDot, zDot)
    dynamic.integrate(state.toArray, integrationStep, timeStep)
  }


}
