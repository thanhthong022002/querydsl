#!/usr/bin/env bash
set -e

# Modules to exclude from deployment
EXCLUDED_MODULES="querydsl-kotlin-codegen,querydsl-lucene3,querydsl-lucene4,querydsl-lucene5,querydsl-hibernate-search,querydsl-mongodb,querydsl-scala,querydsl-kotlin,querydsl-collections,querydsl-guava,querydsl-sql,querydsl-sql-spatial,querydsl-sql-codegen,querydsl-sql-spring,querydsl-jdo,querydsl-spatial,querydsl-maven-plugin"

# Modules to include in deployment (core, codegen, apt, jpa, bom)
INCLUDED_MODULES="querydsl-core,querydsl-codegen,querydsl-codegen-utils,querydsl-apt,querydsl-jpa,querydsl-jpa-codegen,querydsl-bom"

echo "Deploying Querydsl modules (excluding: $EXCLUDED_MODULES)"
echo "Including modules: $INCLUDED_MODULES"
echo ""

# Deploy with module selection
# Using -pl to specify project list and -am to also make dependencies
mvn clean deploy \
  -DskipTests \
  -Pall \
  -Denforcer.skip=true \
  -pl "$INCLUDED_MODULES" \
  -am

echo ""
echo "Deployment completed successfully!"
echo "Excluded modules: $EXCLUDED_MODULES"

