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

import sbt._

object AppDependencies {

  val bootstrapPlayVersion = "7.15.0"

  val compile = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-28"  % bootstrapPlayVersion
  )

  val testScope = "test,it"

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-28"     % bootstrapPlayVersion  % testScope,
    "com.github.tomakehurst"  %  "wiremock-jre8"              % "2.26.3"              % testScope,
    "org.scalatestplus"       %% "mockito-3-4"                % "3.2.3.0"             % testScope
  )
}
