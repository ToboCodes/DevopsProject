FROM jenkins/jenkins
USER root

RUN apt-get update && \
    apt-get install -y \
    ca-certificates \
    curl \
    gnupg

RUN install -m 0755 -d /etc/apt/keyrings && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg && \
    chmod a+r /etc/apt/keyrings/docker.gpg

RUN echo \
  "deb [arch=\"$(dpkg --print-architecture)\" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo \"$VERSION_CODENAME\") stable" | \
  tee /etc/apt/sources.list.d/docker.list > /dev/null

RUN apt-get update && \
    apt-get install -y \
    docker-ce \
    docker-ce-cli \
    containerd.io \
    docker-compose-plugin

# Agregar JDK17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk

# Agregar Maven
RUN rm -rf /opt/maven && \
    curl -O https://downloads.apache.org/maven/maven-3/3.9.2/binaries/apache-maven-3.9.2-bin.tar.gz && \
    tar xzvf apache-maven-3.9.2-bin.tar.gz && \
    mv apache-maven-3.9.2 /opt/maven

# Agregar SOAP UI
RUN curl -O https://dl.eviware.com/soapuios/5.7.0/SoapUI-5.7.0-linux-bin.tar.gz && \
    tar xzvf SoapUI-5.7.0-linux-bin.tar.gz -C /opt && \
    rm SoapUI-5.7.0-linux-bin.tar.gz

# Agregar JMeter
RUN curl -O https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.5.tgz && \
    tar xzvf apache-jmeter-5.5.tgz -C /opt && \
    rm apache-jmeter-5.5.tgz

# Agregar Selenium
RUN curl -O https://selenium-release.storage.googleapis.com/3.141/selenium-server-standalone-3.141.59.jar && \
    mv selenium-server-standalone-3.141.59.jar /opt

# Agregar Google Chrome
RUN curl -sS -o - https://dl-ssl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google.gpg && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/google.gpg] http://dl.google.com/linux/chrome/deb/ stable main" | tee /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable

# Agregar ChromeDriver
RUN CHROME_VERSION=$(google-chrome-stable --version | awk '{ print $3 }' | cut -d '.' -f 1) && \
    CHROMEDRIVER_VERSION=$(curl -s "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_$CHROME_VERSION") && \
    curl -O https://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip && \
    unzip chromedriver_linux64.zip -d /opt && \
    rm chromedriver_linux64.zip

# Agregar herramientas al PATH
ENV PATH="/opt/SoapUI-5.7.0/bin:${PATH}"
ENV PATH="/opt/apache-jmeter-5.5/bin:${PATH}"
ENV PATH="/opt:${PATH}"
ENV PATH="/opt/gradle-8.1.1/bin:${PATH}"

ENV M2_HOME=/opt/maven
ENV PATH=${M2_HOME}/bin:${PATH}

RUN usermod -aG docker jenkins

RUN echo '{\
  "log-driver": "json-file",\
  "log-opts": {\
    "max-size": "10m",\
    "max-file": "3" \
  }\
}' > /etc/docker/daemon.json

USER jenkins
