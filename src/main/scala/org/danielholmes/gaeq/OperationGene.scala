package org.danielholmes.gaeq

trait OperationGene extends Gene {
  val left: Gene
  val right: Gene
  val sign: String

  override lazy val toString: String = s"($left $sign $right)"
}
