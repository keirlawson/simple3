import fs2.Stream

trait S3Client[F[_]] {
  def getObject(bucket: String, key: String): F[Stream[F, Byte]]
}
