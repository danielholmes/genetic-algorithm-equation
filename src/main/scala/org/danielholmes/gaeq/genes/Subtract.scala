package org.danielholmes.gaeq.genes

case class Subtract() extends OperatorGene {
  override lazy val toString = "-"
  def operate(left: Double, right: Double): Double = left - right
  val toBinaryString = Subtract.BINARY_REPRESENTATION
}

object Subtract {
  val BINARY_REPRESENTATION = "1011"
}
