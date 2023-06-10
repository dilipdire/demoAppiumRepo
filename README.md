Prerequisites :

Make sure Java, Android SDK, Appium Server, Allure for reporting is installed in system.

Build steps :

Locate pom.xml and run command "mvn clean install -DskipTests"


Important files for test management :

1. project.properties -- to manage test fw properties and based on true false.

2. testng/suite.xml -- to set test classes for  execution.

3. src/test/org/mis/pom -- place page specific page models here

4. src/test/org/mis/tests -- place for testng test classes


Tools used in the project :

Appium -- http://appium.io/docs/en/about-appium/intro/

Ng Driver -- https://github.com/paul-hammant/ngWebDriver

Allure Report -- https://docs.qameta.io/allure/


