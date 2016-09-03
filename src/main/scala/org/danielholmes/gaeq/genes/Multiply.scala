package org.danielholmes.gaeq.genes

case class Multiply(left: Gene, right: Gene) extends OperatorGene {
  val sign = "*"
  lazy val toDouble = left.toDouble * right.toDouble
}
