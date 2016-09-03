package org.danielholmes.gaeq.genes

case class Divide() extends OperatorGene {
  override lazy val toString = "/"
  def operate(left: Double, right: Double): Double = left / right
  val toBinaryString = Divide.BINARY_REPRESENTATION
}

object Divide {
  val BINARY_REPRESENTATION = "1101"
}
