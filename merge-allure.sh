#!/bin/bash

# Bersihkan build dari semua modul
echo "Cleaning builds..."
./gradlew :DEMOBLAZETEST:clean :APITEST:clean

# Jalankan test untuk kedua modul
echo "Running tests for DEMOBLAZETEST..."
./gradlew :DEMOBLAZETEST:test

echo "Running tests for APITEST..."
./gradlew :APITEST:test

# Buat folder gabungan
echo "Creating merged-allure-results folder..."
rm -rf merged-allure-results
mkdir merged-allure-results

# Copy hasil test
cp -r DEMOBLAZETEST/build/allure-results/* merged-allure-results/
cp -r APITEST/build/allure-results/* merged-allure-results/

# Generate dan buka report
echo "Generating Allure Report..."
allure generate merged-allure-results --clean -o merged-allure-report
allure open merged-allure-report