package in.pbehre.scala

import in.pbehre.proto.HelloWorldProto._
import io.grpc.stub.StreamObserver

import scala.concurrent.Future

class HelloService extends HelloWorldGrpc .HelloWorld {
  override def sayHello(request: HelloRequest): Future[HelloResponse] = {
    val name : String = request.name
    val reply = HelloResponse(welcomeMessage = "Welcome, " + name)
    Future.successful(reply)
  }

  override def streamHello(responseObserver: StreamObserver[HelloResponse]): StreamObserver[HelloRequest] = {
    val requestObserver: StreamObserver[HelloRequest] = new StreamObserver[HelloRequest] {
      override def onNext(value: HelloRequest): Unit = {
        println("Received a Request: " + value.name)
        responseObserver.onNext(HelloResponse(welcomeMessage = "Hello World, " + value.name))
      }


      override def onError(t: Throwable): Unit = {
        println(t)
      }

      override def onCompleted(): Unit = {
        println("Service completed")
      }
    }
    requestObserver
  }

  override def clientStream(responseObserver: StreamObserver[HelloResponse]): StreamObserver[HelloRequest] = {
    var count: Int = 0
    var names: String = ""
    val requestObserver: StreamObserver[HelloRequest] = new StreamObserver[HelloRequest] {
      override def onNext(value: HelloRequest): Unit = {
        println("Received request: " + value.name)
        count = count + 1
        names+= value.name + ", "
      }

      override def onError(t: Throwable): Unit = {
        println(t)
      }

      override def onCompleted(): Unit = {
        responseObserver.onNext(HelloResponse("Welcome, " + names + "\nTotal Count: " + count))
        responseObserver.onCompleted()
        println("clientStream::onCompleted()")
      }
    }

    requestObserver
  }

  override def serverStream(request: HelloRequest, responseObserver: StreamObserver[HelloResponse]): Unit = {
    val values : List[String] = List(
      "First Stream packet",
      "Second stream packet",
      "Third Stream packet",
      "Fourth Stream packet"
    )

    values.foreach(s => responseObserver.onNext(HelloResponse(welcomeMessage = s)))
    responseObserver.onCompleted()
  }
}
