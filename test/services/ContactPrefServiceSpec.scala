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

package services

import common.BouncedEmailConstants._
import mocks.connectors.MockUpdateContactPrefConnector
import models.UpdateContactPrefResponse
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.verify
import org.mockito.internal.verification.VerificationModeFactory.times
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import utils.TestUtil


class ContactPrefServiceSpec extends TestUtil with MockUpdateContactPrefConnector{

  val mockAuditService: AuditService = mock[AuditService]
  val testCorrelation: Option[String] = Some("test")
  "The .updateContactPref method" when {

    val service = new ContactPrefService(mockUpdateContactPrefConnector, mockAuditService)

    "the connector returns a success response" when {

      "a permanent bounce event is processed" should {

        "return the same response" in {
          val successResponse : Option[UpdateContactPrefResponse] = Some(UpdateContactPrefResponse("2020-01-01T09:00:00Z","OK"))
          setupUpdateContactPref(updateContactPrefRequestMaxModel)(successResponse)
          val actual = await(service.updateContactPref(bouncedEmailPermanentBounceModel, testCorrelation))

          val testAuditDetailRaw = Map[String, String](elems =
            "retrievedEventId" -> "some-event-id",
            "retrievedGroupId" -> "some-group-id",
            "retrievedTimestamp" -> "2021-04-07T09:46:29+00:00",
            "retrievedEmailAddress" -> "123@abc.com",
            "retrievedEnrolment" -> "HMRC-MTD-VAT~VRN~GB123456789",
            "retrievedEvent" -> "PermanentBounce",
            "attemptedIdentifier" -> "123456789",
            "attemptedEmail" -> "123@abc.com"
          )

          actual shouldBe successResponse
          verify(mockAuditService, times(1))
            .sendAuditEvent(ArgumentMatchers.eq("BouncedEmailData"), ArgumentMatchers.eq(testAuditDetailRaw))(any(), any(), any())

        }
      }

      "a temporary bounce event is processed" should {

        "return the same response" in {
          val successResponse : Option[UpdateContactPrefResponse] = Some(UpdateContactPrefResponse("2020-01-01T09:00:00Z","OK"))
          setupUpdateContactPref(updateContactPrefRequestMaxModel)(successResponse)
          val actual = await(service.updateContactPref(bouncedEmailTemporaryBounceModel, testCorrelation))

          actual shouldBe successResponse
        }

        "return the same response when spaces are present in VRN and return the VRN with no spaces" in {
          val successResponse: Option[UpdateContactPrefResponse] = Some(UpdateContactPrefResponse("2020-01-01T09:00:00Z", "OK"))
          setupUpdateContactPref(updateContactPrefRequestMaxModel)(successResponse)
          val actual = await(service.updateContactPref(bouncedEmailTemporaryBounceModelWithSpacesVRN, testCorrelation))

          actual shouldBe successResponse
        }
      }

      "a rejected event is processed" should {

        "return the same response" in {
          val successResponse : Option[UpdateContactPrefResponse] = Some(UpdateContactPrefResponse("2020-01-01T09:00:00Z","OK"))
          setupUpdateContactPref(updateContactPrefRequestMaxModel)(successResponse)
          val actual = await(service.updateContactPref(bouncedEmailRejectedModel, testCorrelation))

          actual shouldBe successResponse
        }
      }
    }

    "The connector returns None" should {

      "return None" in {
        val errorResponse: Option[UpdateContactPrefResponse] = None
        setupUpdateContactPref(updateContactPrefRequestMaxModel)(errorResponse)
        val actual = await(service.updateContactPref(bouncedEmailPermanentBounceModel, testCorrelation))
        actual shouldBe errorResponse
      }
    }

    "an event with an invalid vrn is processed" should {

      "return None" in {
        val errorResponse: Option[UpdateContactPrefResponse] = None
        val actual = await(service.updateContactPref(bouncedEmailInvalidVRNModel, testCorrelation))
        actual shouldBe errorResponse
      }
    }
  }
}
