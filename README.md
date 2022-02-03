
# VAT Email Bounce Notification

## Summary

This is the repository for VAT Email Bounce Notification

This service receives invalid email address then processes the information to ETMP, which then updates the customer information to show it is an invalid email address. 

## Requirements

This service is written in [Scala](http://www.scala-lang.org/) and [Play](http://playframework.com/), so needs at least a [JRE](https://www.java.com/en/download/) to run.

## Running the application

In order to run this microservice, you must have SBT installed. You should be able to start the application using:

`sbt run`

## Testing

Use the following command to run unit and integration tests:

`sbt test it:test`

## License

This code is open source software licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html)
