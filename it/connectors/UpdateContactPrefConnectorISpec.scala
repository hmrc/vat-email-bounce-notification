/*
 * Copyright 2024 HM Revenue & Customs
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

package connectors

import helpers.IntegrationBaseSpec
import models.UpdateContactPrefResponse
import play.api.http.Status.{BAD_REQUEST, OK}
import play.api.libs.json.Json
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import testData.UpdateContactPrefConstants._

class UpdateContactPrefConnectorISpec extends IntegrationBaseSpec {

  val connector: UpdateContactPrefConnector = new UpdateContactPrefConnector(httpClient, appConfig)
  val url = "/income-tax/customer/VATC/contact-preference"

  val testCorrelationId: Option[String] = Some("test-id")

  "updateContactPref" should {

    "return an UpdateContactPrefResponse model" when {

      s"an $OK response is received and an UpdateContactPrefResponse model can be parsed" in {

        stubPutRequest(url, OK, updateContactPrefResponseJson.toString())

        val expectedValue: Option[UpdateContactPrefResponse] = Some(updateContactPrefResponse)

        val result: Option[UpdateContactPrefResponse] =
          await(connector.updateContactPref(updateContactPrefRequestMaxModel, testCorrelationId))
        result shouldBe expectedValue
      }

    }
    "return a None" when {

      s"an $OK response is received but the response cannot be parsed" in {

        stubPutRequest(url, OK, """{"foo":"bar"}""")

        val expectedValue = None

        val result: Option[UpdateContactPrefResponse] =
          await(connector.updateContactPref(updateContactPrefRequestMaxModel, testCorrelationId))
        result shouldBe expectedValue
      }
      "an unexpected responses is received from EIS" in {

        stubPutRequest(url, BAD_REQUEST, Json.obj("code" -> 400,
          "reason" -> "BAD REQUEST").toString())

        val expectedValue = None

        val result: Option[UpdateContactPrefResponse] =
          await(connector.updateContactPref(updateContactPrefRequestMaxModel, testCorrelationId))
        result shouldBe expectedValue
      }
    }
  }
}
