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

package connectors

import models.UpdateContactPrefResponse
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import utils.TestUtil

class UpdateContactPrefConnectorSpec extends TestUtil {

  val connector = new UpdateContactPrefConnector

  "The .updateContactPref function" should {

    "return a static UpdateContactPrefResponse model" in {
      await(connector.updateContactPref()) shouldBe UpdateContactPrefResponse("2020-01-01T09:00:00Z", "OK")
    }
  }
}
