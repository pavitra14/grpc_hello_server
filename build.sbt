name := "grpc_hello"

version := "0.1"

scalaVersion := "2.13.5"


val projectlibDependencies = Seq(
  libraryDependencies ++= Seq(
    "io.grpc"              % "grpc-stub"                             % "1.28.0",
    "io.grpc"              % "grpc-netty"                            % scalapb.compiler.Version.grpcJavaVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime-grpc"                 % scalapb.compiler.Version.scalapbVersion,
    "io.grpc"              % "grpc-protobuf"                         % "1.28.0",
  ) )

libraryDependencies ++= Seq(
  "io.grpc"              % "grpc-stub"                             % "1.28.0",
  "io.grpc"              % "grpc-netty"                            % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc"                 % scalapb.compiler.Version.scalapbVersion,
  "io.grpc"              % "grpc-protobuf"                         % "1.28.0",
  "com.thesamet.scalapb" %% "scalapb-json4s"                       %"0.10.1",
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"


)

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)

PB.protocVersion := "-v3.10.0"
