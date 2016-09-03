package org.danielholmes.gaeq

case class Subtract(left: Gene, right: Gene) extends OperationGene {
  val sign = "-"
  lazy val toDouble = left.toDouble - right.toDouble
}
