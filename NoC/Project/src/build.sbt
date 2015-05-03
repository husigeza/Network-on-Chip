scalaVersion := "2.10.4"

resolvers ++= Seq(
"scct-github-repository" at
"http://mtkopone.github.com/scct/maven-repo"
)

libraryDependencies +=
"edu.berkeley.cs" %% "chisel" % "latest.release"

mainClass in (Compile, run) := Some("Network_on_Chip")
