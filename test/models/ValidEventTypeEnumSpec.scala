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

package models

import play.api.libs.json.{JsString, Json}
import utils.TestUtil

class ValidEventTypeEnumSpec extends TestUtil {

  "The enum" should {

    "be writable to JSON for PermanentBounce" in {
      val result = Json.toJson(ValidEventTypeEnum.permanentBounce)
      result shouldBe JsString("PERMANENTBOUNCE")
    }

    "be writable to JSON for TemporaryBounce" in {
      val result = Json.toJson(ValidEventTypeEnum.temporaryBounce)
      result shouldBe JsString("TEMPORARYBOUNCE")
    }

    "be writable to JSON for Rejected" in {
      val result = Json.toJson(ValidEventTypeEnum.rejected)
      result shouldBe JsString("REJECTED")
    }

    "be readable for JSON for PermanentBounce" in {
      val result = Json.fromJson(JsString("PERMANENTBOUNCE"))(ValidEventTypeEnum.format)
      result.get shouldBe ValidEventTypeEnum.permanentBounce
    }

    "be readable for JSON for TemporaryBounce" in {
      val result = Json.fromJson(JsString("TEMPORARYBOUNCE"))(ValidEventTypeEnum.format)
      result.get shouldBe ValidEventTypeEnum.temporaryBounce
    }

    "be readable for JSON for Rejected" in {
      val result = Json.fromJson(JsString("REJECTED"))(ValidEventTypeEnum.format)
      result.get shouldBe ValidEventTypeEnum.rejected
    }

    "return JsError when the enum is not readable" in {
      val result = Json.fromJson(JsString("unknown"))(ValidEventTypeEnum.format)
      result.isError shouldBe true
    }
  }
}
