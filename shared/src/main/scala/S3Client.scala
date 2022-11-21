package io.github.keirlawson.simple3

import fs2.Stream

trait S3Client[F[_]] {
  def getObject(bucket: String, key: String): F[Stream[F, Byte]]
}

object S3Client extends S3ClientPlatform
