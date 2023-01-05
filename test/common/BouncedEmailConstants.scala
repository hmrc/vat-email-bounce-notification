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

package common

import common.BouncedEmailEventConstants._
import models.{BouncedEmail, UpdateContactPrefRequest}
import play.api.libs.json.{JsValue, Json}

object BouncedEmailConstants {

  val bouncedEmailPermanentBounceModel: BouncedEmail = BouncedEmail(
    "some-event-id",
    Some("some-group-id"),
    "2021-04-07T09:46:29+00:00",
    bouncedEmailEventPermanentBounceModel
  )

  val bouncedEmailTemporaryBounceModel: BouncedEmail = BouncedEmail(
    "some-event-id",
    Some("some-group-id"),
    "2021-04-07T09:46:29+00:00",
    bouncedEmailEventTemporaryBounceModel
  )

  val bouncedEmailRejectedModel: BouncedEmail = BouncedEmail(
    "some-event-id",
    Some("some-group-id"),
    "2021-04-07T09:46:29+00:00",
    bouncedEmailEventRejectedModel
  )

  val bouncedEmailInvalidVRNModel: BouncedEmail = BouncedEmail(
    "some-event-id",
    Some("some-group-id"),
    "2021-04-07T09:46:29+00:00",
    bouncedEmailEventInvalidVRNModel
  )

  val bouncedEmailPermanentBounceJson: JsValue = Json.obj(
    "eventId" -> "some-event-id",
    "groupId" -> "some-group-id",
    "timestamp" -> "2021-04-07T09:46:29+00:00",
    "event" -> Json.obj(
      "emailAddress" -> "123@abc.com",
      "tags" -> Json.obj("enrolment" -> "HMRC-MTD-VAT~VRN~GB123456789"),
      "event" -> "PermanentBounce"
    )
  )

  val bouncedEmailInvalidVRNJson: JsValue = Json.obj(
    "eventId" -> "some-event-id",
    "groupId" -> "some-group-id",
    "timestamp" -> "2021-04-07T09:46:29+00:00",
    "event" -> Json.obj(
      "emailAddress" -> "123@abc.com",
      "tags" -> Json.obj("enrolment" -> "HMRC-MTD-VAT~VRN~GB12345P789"),
      "event" -> "PermanentBounce"
    )
  )

  val updateContactPrefRequestMaxJson: JsValue = Json.obj(
    "identifierType" -> "VATC",
    "identifier" -> "123456789",
    "emailaddress" -> "test@hrmc.gov.uk",
    "unusableStatus" -> true
  )

  val updateContactPrefRequestMaxModel: UpdateContactPrefRequest = UpdateContactPrefRequest(
    identifierType = "VATC",
    identifier = "123456789",
    emailaddress = "test@hrmc.gov.uk",
    unusableStatus = true
  )
}
