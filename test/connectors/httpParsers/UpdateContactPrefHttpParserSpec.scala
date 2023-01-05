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

package connectors.httpParsers

import models.UpdateContactPrefResponse
import play.api.http.Status
import play.api.libs.json.Json
import uk.gov.hmrc.http.HttpResponse
import utils.TestUtil

class UpdateContactPrefHttpParserSpec extends TestUtil {

  "The UpdateContactPrefHttpParser" when {

    "a valid JSON body is returned by the API" should {

      "return the response model" in {
        val response = HttpResponse(Status.OK, json = Json.obj("processingDate" -> "2022-02-02", "status" -> "OK"), Map.empty)
        val result = UpdateContactPrefHttpParser.UpdateContactPrefReads.read("POST", "/", response)
        result shouldBe Some(UpdateContactPrefResponse("2022-02-02", "OK"))
      }
    }

    "an invalid JSON body is returned by the API" should {

      "return None" in {
        val response = HttpResponse(Status.OK, json = Json.obj("dinner" -> "pizza"), Map.empty)
        val result = UpdateContactPrefHttpParser.UpdateContactPrefReads.read("POST", "/", response)
        result shouldBe None
      }
    }

    "a non-200 response is returned by the API" should {

      "return None" in {
        val response = HttpResponse(Status.INTERNAL_SERVER_ERROR, "")
        val result = UpdateContactPrefHttpParser.UpdateContactPrefReads.read("POST", "/", response)
        result shouldBe None
      }
    }
  }
}
