import cats.effect.Resource
import cats.effect.IO
import fs2.Stream
import S3ClientFacade.{
  S3Client => UnderlyingS3Client,
  S3ClientConfig,
  GetObjectCommand,
  GetObjectCommandInput
}
import cats.effect.Async
import cats.effect.Sync
import cats.syntax.all._
import fs2.io.suspendReadableAndRead
import fs2.io.Readable

object JsS3Client {
  // FIXME do we need region? create our own enum for it?
  def resource[F[_]: Async](awsRegion: String): Resource[F, S3Client[F]] = {
    val res =
      Resource.make(Sync[F].delay(new UnderlyingS3Client(new S3ClientConfig {
        val region = awsRegion
      })))(c => Sync[F].delay(c.destroy))
    res.map { underlying =>
      new S3Client[F] {

        def getObject(bucket: String, key: String): F[Stream[F, Byte]] = {
          val command = new GetObjectCommand(new GetObjectCommandInput {
            val Bucket = bucket
            val Key = key
          })
          val send =
            Async[F].fromPromise(Sync[F].delay(underlying.send(command)))
          send.map { output =>
            val res = suspendReadableAndRead[F, Readable]()(output.Body)
            Stream.resource(res).flatMap(_._2)
          }
        }

      }

    }

  }
}
