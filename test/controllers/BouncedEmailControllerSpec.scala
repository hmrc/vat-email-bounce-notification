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

import common.BouncedEmailConstants.{bouncedEmailInvalidVRNJson, bouncedEmailInvalidVRNModel, bouncedEmailPermanentBounceJson, bouncedEmailPermanentBounceModel}
import mocks.services.MockUpdateContactPrefService
import models.UpdateContactPrefResponse
import play.api.Play.materializer
import play.api.http.Status
import play.api.libs.json.Json
import play.api.mvc.Headers
import play.api.test.Helpers._
import play.api.test.FakeRequest
import utils.TestUtil

class BouncedEmailControllerSpec extends TestUtil with MockUpdateContactPrefService {

  private val controller = new BouncedEmailController(controllerComponents, mockUpdateContactPrefService)(mockAppConfig,ec)
  "The .process action" when {

    "allowEventHubRequest feature switch is on and a valid JSON body is received" should {

      "return 200" in {
        mockAppConfig.features.allowEventHubRequest(true)
        val request = FakeRequest("POST", "/", Headers((CONTENT_TYPE, JSON)), bouncedEmailPermanentBounceJson)
        setupUpdateContactPrefService(bouncedEmailPermanentBounceModel)(Some(UpdateContactPrefResponse("2020-01-01T09:00:00Z", "OK")))
        lazy val result = controller.process(request)
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
    "a valid json body is received but fails to update" should {

      "return a 304" in {
        mockAppConfig.features.allowEventHubRequest(true)
        val request = FakeRequest("POST", "/", Headers((CONTENT_TYPE, JSON)), bouncedEmailInvalidVRNJson)
        setupUpdateContactPrefService(bouncedEmailInvalidVRNModel)(None)
        lazy val result = controller.process(request)
        status(result) shouldBe Status.NOT_MODIFIED
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
      val request = FakeRequest("POST", "/", Headers((CONTENT_TYPE, JSON)), bouncedEmailPermanentBounceJson)
      val result = controller.process(request)
      status(result) shouldBe Status.SERVICE_UNAVAILABLE
    }
  }
}
