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

package controllers

import common.BouncedEmailConstants.bouncedEmailMaxJson
import play.api.Play.materializer
import play.api.http.Status
import play.api.libs.json.Json
import play.api.mvc.Headers
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import utils.TestUtil

class BouncedEmailControllerSpec extends TestUtil {

  private val controller = new BouncedEmailController(Helpers.stubControllerComponents())

  "The .process action" when {

    "allowEventHubRequest feature switch is on" should {

      "a valid JSON body is received" should {

        "return 200" in {
          mockAppConfig.features.allowEventHubRequest(true)
          val request = FakeRequest("POST", "/", Headers((CONTENT_TYPE, JSON)), bouncedEmailMaxJson)
          val result = controller.process(request)
          status(result) shouldBe Status.OK
        }
      }

      "an invalid JSON body is received" should {

        "return 400" in {
          val request = FakeRequest("POST", "/", Headers((CONTENT_TYPE, JSON)), Json.obj("currency" -> "GBP"))
          val result = controller.process(request)
          status(result) shouldBe Status.BAD_REQUEST
        }
      }

      "a request without JSON content is received" should {

        "return 415" in {
          val request = FakeRequest()
          val result = controller.process(request)
          status(result) shouldBe Status.UNSUPPORTED_MEDIA_TYPE
        }
      }
    }

    "allowEventHubRequest feature switch is off" should {

      "return 503" in {
        mockAppConfig.features.allowEventHubRequest(false)
        val request = FakeRequest("POST", "/", Headers((CONTENT_TYPE, JSON)), bouncedEmailMaxJson)
        val result = controller.process(request)
        status(result) shouldBe Status.SERVICE_UNAVAILABLE
      }
    }
  }
}
