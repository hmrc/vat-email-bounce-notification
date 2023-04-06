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

package connectors

import common.BouncedEmailConstants.updateContactPrefRequestMaxModel
import mocks.helpers.MockHttpClient
import models.{UpdateContactPrefRequest, UpdateContactPrefResponse}
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import uk.gov.hmrc.http.RequestTimeoutException
import utils.TestUtil

import scala.concurrent.Future

class UpdateContactPrefConnectorSpec extends TestUtil with MockHttpClient {

  object TestConnector extends UpdateContactPrefConnector(mockHttpClient, mockAppConfig)

  "UpdateContactPrefConnector url()" should {

    "correctly format the url" in {
      TestConnector.updateContactPrefUrl() shouldEqual "http://localhost:9156/income-tax/customer/VATC/contact-preference"
    }
  }

  "UpdateContactPrefConnector .updateContactPref()" when {

    "there is a HTTP exception" should {

      "return None" in {
        val exception = new RequestTimeoutException("The request timed out!")
        mockHttpPut[UpdateContactPrefRequest, UpdateContactPrefResponse](Future.failed(exception))
        val result: Future[Option[UpdateContactPrefResponse]] = TestConnector.updateContactPref(updateContactPrefRequestMaxModel)

        await(result) shouldBe None
      }
    }
  }
}