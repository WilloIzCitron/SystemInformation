call gradlew jar
call copy "build\libs\SystemInformationDesktop.jar" "%appdata%\Mindustry\mods"
start /wait java -jar Mindustry.jar