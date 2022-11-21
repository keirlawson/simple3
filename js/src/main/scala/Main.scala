import cats.effect._

import fs2.io.file.Files
import fs2.io.file.Path

object Main extends IOApp.Simple {

  val run = JsS3Client.resource[IO]("eu-west-1").use { client =>
    client
      .getObject("kaluza-kpi-config", "config-artefact-v1.0.0.zip")
      .flatMap { bytes =>
        Files[IO].writeAll(Path("test2.zip"))(bytes).compile.drain
      } >> IO.println(s"Download complete")
  }

}
