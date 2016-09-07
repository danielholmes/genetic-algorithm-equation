package org.danielholmes.gaeq.genes

import org.scalatest._

class NumericGeneSpec extends FlatSpec with Matchers {
  "NumericGeneSpec" should "return correct boolean seq for 0" in {
    NumericGene(0).toBooleanSeq should be (Seq(false, false, false, false))
  }

  it should "return correct boolean seq for 1" in {
    NumericGene(1).toBooleanSeq should be (Seq(false, false, false, true))
  }
}
