lazy val commonSettings = Seq(
  scalaVersion := "2.12.1",
  resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

lazy val root = project.in(file("."))
    .settings(commonSettings)
      .aggregate(chapterCode, exercises, answers)
lazy val chapterCode = project.in(file("chaptercode"))
  .settings(commonSettings)
lazy val exercises = project.in(file("exercises"))
  .settings(commonSettings)
lazy val answers = project.in(file("answers"))
  .settings(commonSettings)
