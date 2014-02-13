// Settings file for all the modules.
import sbt._
import Keys._
import Defaults._
import NativePackagerKeys._

// Blank lines are required between settings.

organization in ThisBuild := "org.steveturner.multiswagger"

version in ThisBuild := ApplicationBuild.appVersion

crossPaths in ThisBuild := false

organizationName in ThisBuild := "Your Company, Inc."

organizationHomepage in ThisBuild := Some(url("http://your.domain.com/"))

packageSummary in Linux := "The name you want displayed in package summaries"

packageSummary in Windows := "Services"

packageDescription := "Services"

maintainer in Windows := "Your Company, Inc."

maintainer in Debian := "Your Name <someguy@@gmail.com>"

wixProductId := "3935c190-9029-11e3-bac8-0800200c9a66"

wixProductUpgradeId := "44c8dc40-9029-11e3-bac8-0800200c9a66"

scalacOptions in ThisBuild ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked")	

publishMavenStyle in ThisBuild := true

publishArtifact in ThisBuild in Test := false

pomIncludeRepository in ThisBuild := { x => false }


publishTo  in ThisBuild <<=version  {(v:String) =>
			val nexus = "http://your.domain.com/nexus/"
			if (v.trim.endsWith("SNAPSHOT"))
				Some("snapshots" at nexus + "content/repositories/snapshots")
			else
				Some("releases"  at nexus + "content/repositories/releases")
		}

credentials in ThisBuild += Credentials (Path.userHome / ".ivy2" / ".credentials")

resolvers in ThisBuild ++= Seq(
	"Local Maven Repository" at Path.userHome.asFile.toURI.toURL + "/.m2/repository",
	Resolver.url("Local Ivy Repository", url(Path.userHome.asFile.toURI.toURL+"/.ivy2/local"))(Resolver.ivyStylePatterns),
	//Uncomment this line and add your own domain
	//"YourDomain Public" at "http://your.domain.com/nexus//content/groups/public/",
	"Typesafe Releases Repository" at "http://repo.typesafe.com/typesafe/releases/",
  	"Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
  	"Maven Central" at "http://repo1.maven.org/maven2",
   	"sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases")

