package io.github.keirlawson.simple3

import scalajs.js.annotation._
import scalajs.js
import fs2.io.Readable

object S3ClientFacade {
  trait S3ClientConfig extends js.Object {
    val region: js.UndefOr[String]
  }

  trait GetObjectCommandInput extends js.Object {
    val Bucket: String
    val Key: String
  }

  @js.native
  trait GetObjectCommandOutput extends js.Object {
    def Body: Readable = js.native
  }

  @js.native
  @JSImport("@aws-sdk/client-s3", "S3Client")
  class S3Client(configuration: S3ClientConfig) extends js.Object {
    def send(command: GetObjectCommand): js.Promise[GetObjectCommandOutput] =
      js.native
    def destroy: Unit = js.native
  }

  @js.native
  @JSImport("@aws-sdk/client-s3", "GetObjectCommand")
  class GetObjectCommand(input: GetObjectCommandInput) extends js.Object
}
