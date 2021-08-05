set VERSION=%1
set BRANCH="xomad-%VERSION%"

CALL mvn "-Pall,docs" versions:set -DgenerateBackupPoms=false "-DnewVersion=%VERSION%"
CALL mvn -Pxomad clean package "-Denforcer.skip=true" -DskipTests "-Dcheckstyle.skip=true"

CALL git checkout -b %BRANCH%
CALL git add .
CALL git commit -m "Custom version %VERSION%"
