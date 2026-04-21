FROM eclipse-temurin:21-jdk

ENV DISPLAY=host.docker.internal:0.0
ENV LIBGL_ALWAYS_SOFTWARE=1
ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=false"

RUN apt-get update && \
    apt-get install -y wget unzip \
    libgtk-3-0 libgbm1 libx11-6 \
    libgl1-mesa-dri \
    xauth x11-apps && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

RUN wget https://download2.gluonhq.com/openjfx/21/openjfx-21_linux-x64_bin-sdk.zip \
    -O /tmp/openjfx.zip && \
    unzip /tmp/openjfx.zip -d /opt && \
    rm /tmp/openjfx.zip

WORKDIR /app

COPY target/shopping-cart.jar app.jar

CMD [
  "java",
  "--module-path","/opt/javafx-sdk-21/lib",
  "--add-modules","javafx.controls,javafx.fxml",
  "-Djava.library.path=/opt/javafx-sdk-21/lib",
  "-Dprism.order=sw",
  "-Djava.awt.headless=false",
  "-jar","app.jar"
]
