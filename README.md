# Simple3

A very simple S3 client for use with the Cats Effect Scala ecosystem. Works on both JVM and ScalaJS.

## Usage

### Coordinates

JVM:

`"io.github.keirlawson" %% "simple3" % "0.3"`

JS:

`"io.github.keirlawson" %%% "simple3" % "0.3"`

### Example

```scala
import cats.effect._
import io.github.keirlawson.simple3.S3Client
import fs2.io.file.{Files, Path}

object Main extends IOApp.Simple {

  val run = S3Client.resource[IO].use { client =>
    client
      .getObject("bucketname", "key")
      .flatMap { bytes =>
        Files[IO].writeAll(Path("filename"))(bytes).compile.drain
      } >> IO.println(s"Download complete")
  }

}
```
