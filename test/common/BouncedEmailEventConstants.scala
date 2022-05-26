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

package common

import models.BouncedEmailEvent
import models.ValidEventTypeEnum.{permanentBounce, rejected, temporaryBounce}
import play.api.libs.json.{JsValue, Json}

object BouncedEmailEventConstants {

  val bouncedEmailEventPermanentBounceModel: BouncedEmailEvent = BouncedEmailEvent(
    "123@abc.com",
    "HMRC-MTD-VAT~VRN~GB123456789",
    permanentBounce
  )

  val bouncedEmailEventTemporaryBounceModel: BouncedEmailEvent = BouncedEmailEvent(
    "123@abc.com",
    "HMRC-MTD-VAT~VRN~GB123456789",
    temporaryBounce
  )

  val bouncedEmailEventRejectedModel: BouncedEmailEvent = BouncedEmailEvent(
    "123@abc.com",
    "HMRC-MTD-VAT~VRN~GB123456789",
    rejected
  )

  val bouncedEmailEventInvalidVRNModel: BouncedEmailEvent = BouncedEmailEvent(
    "123@abc.com",
    "HMRC-MTD-VAT~VRN~GB12345P789",
    permanentBounce
  )

  val bouncedEmailEventPermanentBounceJson: JsValue = Json.obj(
    "emailAddress" -> "123@abc.com",
    "enrolment" -> "HMRC-MTD-VAT~VRN~GB123456789",
    "event" -> "PermanentBounce"
  )

}
