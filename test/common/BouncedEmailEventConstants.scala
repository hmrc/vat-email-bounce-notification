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
import play.api.libs.json.{JsValue, Json}

object BouncedEmailEventConstants {

  val bouncedEmailEventMinModel: BouncedEmailEvent = BouncedEmailEvent(
    None,
    None,
    Some("PermanentBounce")
  )

  val bouncedEmailEventMaxModel: BouncedEmailEvent = BouncedEmailEvent(
    Some("123@abc.com"),
    Some("HMRC-MTD-VAT~VRN~GB123456789"),
    Some("PermanentBounce")
  )

  val bouncedEmailEventInvalidVRNModel: BouncedEmailEvent = BouncedEmailEvent(
    Some("123@abc.com"),
    Some("HMRC-MTD-VAT~VRN~GB12345P789"),
    Some("PermanentBounce")
  )

  val bouncedEmailEventModelNoEmail: BouncedEmailEvent = BouncedEmailEvent(
    None,
    Some("HMRC-MTD-VAT~VRN~GB123456789"),
    Some("PermanentBounce")
  )

  val bouncedEmailEventInvalidEventTypeModel: BouncedEmailEvent = BouncedEmailEvent(
    Some("123@abc.com"),
    Some("HMRC-MTD-VAT~VRN~GB123456789"),
    Some("RandomBounceEvent")
  )

  val bouncedEmailEventMinJson: JsValue = Json.obj("event" -> "PermanentBounce")

  val bouncedEmailEventMaxJson: JsValue = Json.obj(
    "emailAddress" -> "123@abc.com",
    "enrolment" -> "HMRC-MTD-VAT~VRN~GB123456789",
    "event" -> "PermanentBounce"
  )

}
