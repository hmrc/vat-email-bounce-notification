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

package componentInterface

import connectors.UpdateContactPrefConnector
import helpers.IntegrationBaseSpec
import play.api.http.Status
import play.api.http.Status.OK
import play.api.libs.json.Json
import testData.UpdateContactPrefConstants.updateContactPrefResponseJson

class BouncedEmailIntegrationSpec extends IntegrationBaseSpec {

  val path = "/process-bounce"
  val connector: UpdateContactPrefConnector = new UpdateContactPrefConnector(httpClient, appConfig)
  val url = "/income-tax/customer/VATC/contact-preference"

  s"Sending a valid JSON request to $path" should {

    "return a 200 response" in {

      val json = Json.obj(
        "eventId" -> "some-event-id",
        "groupId" -> "some-group-id",
        "timestamp" -> "2021-04-07T09:46:29+00:00",
        "event" -> Json.obj(
          "emailAddress" -> "123@abc.com",
          "tags" -> Json.obj(
            "enrolment" -> "HMRC-MTD-VAT~VRN~GB123456789"
          ),
          "event" -> "PermanentBounce"
        )
      )

      stubPutRequest(url, OK, updateContactPrefResponseJson.toString())

      val result = post(path)(json)

      result.status shouldBe Status.OK
    }
  }
}
