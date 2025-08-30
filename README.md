From the project's root path

Step 1. Create.jar

CMD
gradlew jar

Step 2. Execute .jar
CMD
java -jar build/libs/Expressor-1.0-SNAPSHOT.jar --help
java -jar build/libs/Expressor-1.0-SNAPSHOT.jar -h

Step 3. Dependencies
CMD
jdeps build/libs/Expressor-1.0-SNAPSHOT.jar

Step 4. create minimal runtime
CMD
jlink --output build/runtime --add-modules java.base,java.compiler,jdk.compiler --compress=2

Step 5. Create expresso.exe
CMD
jpackage --input build/libs --name expressor --main-jar Expressor-1.0-SNAPSHOT-all.jar --main-class cli.Expressor --type app-image --dest expresor_package --win-console --runtime-image build/runtime
