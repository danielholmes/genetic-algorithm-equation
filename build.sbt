name := "genetic-algorithm-equation"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies ++= {
  Seq(
    "org.scalatest"       % "scalatest_2.11" % "3.0.0" % "test"
  )
}

lazy val testScalastyle = taskKey[Unit]("testScalastyle")
testScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Test).toTask("").value
(test in Test) <<= (test in Test) dependsOn testScalastyle

lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")
compileScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Compile).toTask("").value
(compile in Compile) <<= (compile in Compile) dependsOn compileScalastyle
