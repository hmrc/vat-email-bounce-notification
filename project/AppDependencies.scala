/*
 * Copyright 2022 HM Revenue & Customs
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

  val compile = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-28"  % "5.20.0"
  )

  val testScope = "test,it"

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-28"     % "5.20.0"             % testScope,
    "com.vladsch.flexmark"    %  "flexmark-all"               % "0.36.8"             % testScope,
    "org.mockito"             % "mockito-core"                % "3.2.0"              % testScope,
    "com.github.tomakehurst"  % "wiremock-jre8"               % "2.26.3"             % testScope,
    "org.scalatestplus"       %% "scalatestplus-mockito"      % "1.0.0-M2"           % testScope
  )
}
