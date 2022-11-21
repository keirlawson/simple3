import cats.effect.Resource
import software.amazon.awssdk.services.s3
import cats.effect.Sync
import fs2.Stream
import fs2.io.readInputStream
import cats.Applicative

object JvmS3Client {
  def resource[F[_]: Sync]: Resource[F, S3Client[F]] = {
    Resource.fromAutoCloseable(Sync[F].delay(s3.S3Client.create())).map {
      client =>
        new S3Client[F] {

          def getObject(bucket: String, key: String): F[Stream[F, Byte]] = {
            val request = s3.model.GetObjectRequest
              .builder()
              .bucket(bucket)
              .key(key)
              .build()
            val stream = readInputStream[F](
              Sync[F].delay(client.getObject(request)),
              64 * 1024 //match the default chunk size on Node
            )
            Applicative[F].pure(stream)
          }

        }
    }

  }
}
