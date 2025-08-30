

Step 1. Create.jar

CMD
gradlew jar

Step 2. Execute .jar
CMD
java -jar build/libs/Expressor-1.0-SNAPSHOT.jar --help
java -jar build/libs/Expressor-1.0-SNAPSHOT.jar -h

Step 3. Dependencies
CMD
jdeps -s build/libs/Expressor-1.0-SNAPSHOT.jar 

Step 4. create minimal runtime
CMD
jlink --add-modules java.base,java.compiler --output ../runtime --compress=2 --no-header-files --no-man-pages --strip-debug

Step 5. Create expresso.exe
CMD
jpackage --name expressor --input build/libs --main-jar Expressor-1.0-SNAPSHOT.jar --main-class cli.Expressor --type app-image --dest expressor_package --win-console --runtime-image ../runtime
