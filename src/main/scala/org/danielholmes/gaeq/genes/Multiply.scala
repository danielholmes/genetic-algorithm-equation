package org.danielholmes.gaeq.genes

case class Multiply() extends OperatorGene {
  override lazy val toString = "*"
  def operate(left: Double, right: Double): Double = left * right
  val toBinaryString = Multiply.BINARY_REPRESENTATION
}

object Multiply {
  val BINARY_REPRESENTATION = "1100"
}
