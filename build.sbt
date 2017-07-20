
name := "easter-viability"

scalaVersion := "2.12.2"

def viabilitreeVersion = "2.0-alpha5"
//def viabilitreeVersion = "2.0-SNAPSHOT"

resolvers += Resolver.sonatypeRepo("public")
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "fr.iscpif.viabilitree" %% "export" % viabilitreeVersion
libraryDependencies += "fr.iscpif.viabilitree" %% "viability" % viabilitreeVersion
libraryDependencies += "fr.iscpif.viabilitree" %% "model" % viabilitreeVersion


