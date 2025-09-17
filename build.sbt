/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import play.sbt.routes.RoutesKeys
import uk.gov.hmrc.DefaultBuildSettings.integrationTestSettings

val appName = "vat-email-bounce-notification"
ThisBuild / scalaVersion := "2.13.16"
ThisBuild / majorVersion := 0

lazy val coverageSettings: Seq[Setting[?]] = {
  import scoverage.ScoverageKeys

  val excludedPackages = Seq(
    "<empty>",
    ".*Routes.*",
    "app.*",
    "config.*",
    "prod.*",
    "testOnlyDoNotUseInAppConf.*"
  )

  Seq(
    ScoverageKeys.coverageExcludedPackages := excludedPackages.mkString(";"),
    ScoverageKeys.coverageMinimumStmtTotal := 95,
    ScoverageKeys.coverageFailOnMinimum    := true,
    ScoverageKeys.coverageHighlighting     := true
  )
}

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(PlayKeys.playDefaultPort := 9168)
  .settings(
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    RoutesKeys.routesImport := Seq.empty
  )
  .settings(coverageSettings *)
  .configs(IntegrationTest)
  .settings(integrationTestSettings() *)

scalacOptions ++= Seq(
  "-Wconf:cat=unused-imports&src=routes/.*:s",
  "-Wconf:cat=unused&src=routes/.*:s"
)
