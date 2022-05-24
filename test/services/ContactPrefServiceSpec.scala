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

package services

import common.BouncedEmailConstants.{bouncedEmailInvalidEventTypeModel, bouncedEmailInvalidVRNModel, bouncedEmailMaxModel, bouncedEmailMaxModelNoEmail, updateContactPrefRequestMaxModel}
import models.UpdateContactPrefResponse
import utils.TestUtil
import mocks.connectors.MockUpdateContactPrefConnector
import play.api.test.Helpers.{await, defaultAwaitTimeout}


class ContactPrefServiceSpec extends TestUtil with MockUpdateContactPrefConnector{

  "The .updateContactPref method" when {

    val service = new ContactPrefService(mockUpdateContactPrefConnector)

    "the connector returns a success response" should {

      "return the same response" in {
        val successResponse : Option[UpdateContactPrefResponse] = Some(UpdateContactPrefResponse("2020-01-01T09:00:00Z","OK"))
        setupUpdateContactPref(updateContactPrefRequestMaxModel)(successResponse)
        val actual = await(service.updateContactPref(bouncedEmailMaxModel))

        actual shouldBe successResponse
      }

    }
    "the connector returns an error when no email is provided" should {

      "return the same response" in {
        val successResponse: Option[UpdateContactPrefResponse] = None
        setupUpdateContactPref(updateContactPrefRequestMaxModel)(successResponse)
        val actual = await(service.updateContactPref(bouncedEmailMaxModelNoEmail))
        actual shouldBe successResponse
      }
    }
    "The connector returns an error when an incorrect vrn is matched" should {

      "return the same response" in {
        val successResponse: Option[UpdateContactPrefResponse] = None
        setupUpdateContactPref(updateContactPrefRequestMaxModel)(successResponse)
        val actual = await(service.updateContactPref(bouncedEmailInvalidVRNModel))
        actual shouldBe successResponse
      }

    }

    "an invalid event type is provided" should {

      "return None" in {
        val errorResponse: Option[UpdateContactPrefResponse] = None
        setupUpdateContactPref(updateContactPrefRequestMaxModel)(errorResponse)
        val actual = await(service.updateContactPref(bouncedEmailInvalidEventTypeModel))
        actual shouldBe errorResponse
      }
    }

  }

}
