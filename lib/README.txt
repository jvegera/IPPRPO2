Каталог lib/ — JAR для самостоятельного Ant-сценария (скачать с Maven Central, https://repo.maven.apache.org/maven2/ ).
Нужен совместимый набор JUnit 5 (версии платформы и Jupiter должны соответствовать друг другу, по методичке — 5.14.x):

  org.junit.jupiter:junit-jupiter-api
  org.junit.jupiter:junit-jupiter-engine
  org.junit.platform:junit-platform-commons
  org.junit.platform:junit-platform-engine
  org.junit.platform:junit-platform-launcher
  org.opentest4j:opentest4j
  org.apiguardian:apiguardian-api

Удобный вариант: скачать junit-platform-console-standalone-<версия>.jar одним файлом
(в нём уже есть всё перечисленное). ant-junitlauncher.jar должен лежать в ANT_HOME/lib.
