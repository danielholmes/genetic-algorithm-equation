package org.danielholmes.gaeq.genes

trait OperatorGene extends Gene {
  def operate(left: Double, right: Double): Double
}
