package org.danielholmes.gaeq.genes

trait Gene {
  val toBinaryString: String
  // TODO: Use this, more efficient than using strings/chars
  // val toByteSeq: Seq[Byte]
}

object Gene {
  val BINARY_REPRESENTATION_SIZE = 4
}
