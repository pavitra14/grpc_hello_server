package in.pbehre.scala


import in.pbehre.proto.HelloWorldProto._

import io.grpc.{Server, ServerBuilder}

import java.util.logging.{LogManager, Logger}
import scala.concurrent.ExecutionContext

object App {
  val logger: Logger = Logger.getLogger(classOf[App].getName)
  val port = 50051

  def main(args: Array[String]): Unit = {
    val server = new HelloWorldServer(ExecutionContext.global)
    server.start
    server.blockUntilShutdown
  }
}
class HelloWorldServer(executionContext: ExecutionContext) { self =>
  private[this] var server: Server = null

  def start(): Unit = {
    server = ServerBuilder
      .forPort(App.port)
      .addService(HelloWorldGrpc.bindService(new HelloService, executionContext))
      .build()
      .start()
    App.logger.info("Starting server on port: " + App.port)
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }
  def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }
}