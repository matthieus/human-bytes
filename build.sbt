organization := "human-bytes"

name := "human-bytes"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.2.3" % "test"
)

jacoco.settings

//
// Documentation site generation.
//

site.settings

site.includeScaladoc(".")

ghpages.settings

git.remoteRepo := "git@github.com:matthieus/human-bytes.git"
