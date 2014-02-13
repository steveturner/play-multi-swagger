import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

/*
  val gitbranch = "git rev-parse --abbrev-ref HEAD".!!.trim
  val branch = if (gitbranch == "master") {
    "RELEASE"
  } else {
    "SNAPSHOT"
  }
  val computedVersion = if (branch == "RELEASE") {
    "git describe --tags git rev-list --tags --max-count=1".!!.trim
  } else {
    "0.0.1"
  }
  
  val appVersion: String = { f"$computedVersion%s-$branch%s"}
*/
  val appVersion: String = "0.0.1-SNAPSHOT"
  val commonDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    jdbc,
    anorm,
    cache,
    "com.kenshoo" %% "metrics-play" % "0.1.3",
    "com.wordnik" %% "swagger-play2" % "1.3.1",
    ("com.wordnik" %% "swagger-play2-utils" % "1.3.1").excludeAll(ExclusionRule(organization = "org.slf4j"))
  )
  val adminDependencies = Seq() // You can have service specific dependencies
  val webDependencies = Seq()

  val scalaBuildOptions = Seq("-unchecked", "-deprecation", "-feature", "-language:reflectiveCalls",
    "-language:implicitConversions", "-language:postfixOps", "-language:dynamics","-language:higherKinds",
    "-language:existentials", "-language:experimental.macros", "-Xmax-classfile-name", "140")


  val common = play.Project("service-common", appVersion, commonDependencies, path = file("modules/common")).settings(
    // Add common settings here
    scalacOptions ++= scalaBuildOptions,
    sources in doc in Compile := List(),
    javaOptions in Test += "-Dconfig.resource=application.conf"
  )

  val admin = play.Project("service-admin", appVersion, commonDependencies ++ adminDependencies, path = file("modules/admin")).settings(
    // Add admin settings here      
    scalacOptions ++= scalaBuildOptions,
    sources in doc in Compile := List(),
    javaOptions in Test += "-Dconfig.resource=application.conf"
  ).dependsOn(common % "test->test;compile->compile").aggregate(common)

  val web = play.Project("service-web", appVersion, commonDependencies ++ webDependencies, path = file("modules/web")).settings(
    // Add web settings here
    scalacOptions ++= scalaBuildOptions,
    sources in doc in Compile := List(),
    javaOptions in Test += "-Dconfig.resource=application.conf"
  ).dependsOn(common % "test->test;compile->compile").aggregate(common)


  // The default SBT project is based on the first project alphabetically. To force sbt to use this one,
  // we prefit it with 'aaa'
  val aaaMultiProject = play.Project("service-server", appVersion, commonDependencies ++ adminDependencies ++ webDependencies).settings(
    // This project runs both services together, which is mostly useful in development mode.
    scalacOptions ++= scalaBuildOptions,
    sources in doc in Compile := List()
    


  ).dependsOn(common % "test->test;compile->compile", admin % "test->test;compile->compile", web % "test->test;compile->compile").aggregate(common, admin, web)


}
