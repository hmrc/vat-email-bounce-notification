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

import common.BouncedEmailConstants._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import play.api.libs.json.Json

class UpdateContactPrefRequestSpec extends AnyWordSpecLike with Matchers {

  "parse the json correctly when all optional fields are populated" in {
    updateContactPrefRequestMaxJson.as[UpdateContactPrefRequest] shouldBe updateContactPrefRequestMaxModel
  }

  "parse the json correctly when no optional fields are populated" in {
    updateContactPrefRequestMinJson.as[UpdateContactPrefRequest] shouldBe updateContactPrefRequestMinModel
  }

  "parse the model correctly to the json when all the optional fields are populated" in {
    Json.toJson(updateContactPrefRequestMaxModel) shouldBe updateContactPrefRequestMaxJson
  }

  "parse the model correctly to the json when no optional fields are populated" in {
    Json.toJson(updateContactPrefRequestMinModel) shouldBe updateContactPrefRequestMinJson
  }

}