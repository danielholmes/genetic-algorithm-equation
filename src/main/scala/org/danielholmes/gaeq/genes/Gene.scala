package org.danielholmes.gaeq.genes

trait Gene {
  val toBooleanSeq: Seq[Boolean]
}

object Gene {
  val BIT_SIZE = 4
}
