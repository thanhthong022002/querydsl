set VERSION=%1
set BRANCH="xomad-%VERSION%"

mvn "-Pall,docs" versions:set -DgenerateBackupPoms=false "-DnewVersion=%VERSION%" && ^
mvn -Pjpa clean deploy -am "-Denforcer.skip=true" -DskipTests "-Dcheckstyle.skip=true" && ^
git checkout -b %BRANCH% && ^
git add . && ^
git commit -m "Custom version %VERSION%"
