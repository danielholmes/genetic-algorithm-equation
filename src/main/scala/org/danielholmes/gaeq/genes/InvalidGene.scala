package org.danielholmes.gaeq.genes

case class InvalidGene(toBooleanSeq: Seq[Boolean]) extends Gene {
  override lazy val toString = "?"
}
