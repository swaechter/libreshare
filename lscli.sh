#!/bin/bash

# This script serves as a utility tool to build, package and deploy Libreshare. It is fully automated and can be
# used from the command line.
#
# Author: Simon Wächter <waechter.simon@gmail.com>

# Enable status code handling in case of an error
set -e

# Project variables
LIBRESHARE_VERSION=0.0.1
LIBRESHARE_BUILD_NUMBER=00001
LIBRESHARE_AUTHOR_NAME="Simon Wächter"
LIBRESHARE_AUTHOR_EMAIL="waechter.simon@gmail.com"

# Java version
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/

# Define the colors
TITLE="\e[1m"
SUBTITLE="\e[1m"
COMMAND="\e[32m"
CLEAN="\e[0m"

function pushd() {
    command pushd "$@" >/dev/null
}

function popd() {
    command popd "$@" >/dev/null
}

function checkProject() {
    echo "Going to check all tools"

    # Check if Docker is installed
    if ! command -v docker &>/dev/null; then
        echo "Docker was not found on the system! Please use this guide to install it: https://docs.docker.com/engine/install/ubuntu/"
        exit 1
    fi

    # Check if Minikube is installed
    if ! command -v minikube &>/dev/null; then
        echo "Minikube was not found on the system. Please use this guide to install it: https://kubernetes.io/docs/tasks/tools/install-minikube/"
    fi

    # Check if Skaffold is installed
    if ! command -v skaffold &>/dev/null; then
        echo "Skaffold was not found on the system. Please use this guide to install it: https://skaffold.dev/docs/install/"
    fi

    # Check if Git is installed
    if ! command -v git &>/dev/null; then
        echo "Git was not found on the system! Please install it via package manager"
        exit 1
    fi

    # TODO: Check RPM build tools on RPM based systems

    echo "Done checking all tools"
}

function cleanProject() {
    echo "Going to clean the project"

    # Clean the project build
    ./gradlew clean

    echo "Done cleaning the project"
}

function buildProject() {
    echo "Going to build the project"

    # Build the project while skipping the tests
    ./gradlew build -x test

    echo "Done building the project"
}

function developProject() {
    echo "Going to run the development build"

    # Start Minikube
    minikube start

    # Make Minikube use the Docker registry of the host
    eval "$(minikube docker-env)"

    # Build the base images
    docker build -t libreshare-base packages/kubernetes

    # Delete potential secrets
    if kubectl get secrets | grep -q 'libreshare-secrets'; then
        kubectl delete secret libreshare-secrets
    fi

    # Create the secrets
    kubectl create secret generic libreshare-secrets \
        --from-literal=web-secret=i2pjlk3hs2hu3123he782sa21eh71s2haj3ej4j3jdwjljldjkl4rj2sqsw7h3s \
        --from-literal=database-hostname=libreshare-database \
        --from-literal=database-port=5432 \
        --from-literal=database-name=agent \
        --from-literal=database-username=agent \
        --from-literal=database-password=123456aA

    # Run Skaffold in development mode and redirect all ports
    skaffold dev --port-forward
}

function packageProject() {
    echo "Going to package the project"

    # Create the local RPM development tree
    pushd packages/rpm
    mkdir -p rpmbuild rpmbuild/SPECS rpmbuild/BUILD rpmbuild/BUILDROOT rpmbuild/RPMS rpmbuild/SOURCES rpmbuild/SPECS rpmbuild/SRPMS

    # Create the Libreshare source archive (Contains the build JAR files)
    rm -rf libreshare-${LIBRESHARE_VERSION}
    mkdir libreshare-${LIBRESHARE_VERSION}
    cp ../../src/web/build/libs/web-${LIBRESHARE_VERSION}-all.jar libreshare-${LIBRESHARE_VERSION}/libreshare-web.jar
    tar -czf rpmbuild/SOURCES/libreshare-${LIBRESHARE_VERSION}.tar.gz -C . libreshare-${LIBRESHARE_VERSION}

    popd

    # Build the packages
    rpmbuild --define "_topdir $(pwd)/packages/rpm/rpmbuild" -ba packages/rpm/libreshare.spec

    echo "Done packaging the project"
}

function deployProject() {
    echo "Going to deploy the project"

    echo "Done deploying the project"
}

function installLibreshare() {
    echo "Going to install Libreshare"

    echo "Done installing Libreshare"
}

function upgradeLibreshare() {
    echo "Going to upgrade Libreshare"

    echo "Done upgrading Libreshare"
}

function uninstallLibreshare() {
    echo "Going to uninstall Libreshare"

    echo "Done uninstalling Libreshare"
}

function backupLibreshare() {
    echo "Going to backup Libreshare"

    echo "Done backing up Libreshare"
}

function showAbout() {
    echo "Libreshare Command Line Interface (sgcli.sh) in version ${LIBRESHARE_VERSION} and build number ${LIBRESHARE_BUILD_NUMBER}"
    echo "Author: ${LIBRESHARE_AUTHOR_NAME} <${LIBRESHARE_AUTHOR_EMAIL}>"
}

function showHelp() {
    echo -e "---------------------------------------------------------------------"
    echo -e "${TITLE}Welcome to the Libreshare Command Line Interface (lscli.sh)${CLEAN}"
    echo -e "---------------------------------------------------------------------"
    echo -e ""
    echo -e "${SUBTITLE}Usage:${CLEAN}  ${COMMAND}lscli.sh <Command>${CLEAN}"
    echo -e ""
    echo -e "${SUBTITLE}Commands for development environment${CLEAN}"
    echo -e ""
    echo -e "        ${COMMAND}check${CLEAN}       Check if all required tools are installed"
    echo -e "        ${COMMAND}clean${CLEAN}       Clean the project"
    echo -e "        ${COMMAND}build${CLEAN}       Build the project"
    echo -e "        ${COMMAND}develop${CLEAN}     Run the development build"
    echo -e "        ${COMMAND}package${CLEAN}     Package the project as Docker containers/Helm chart"
    echo -e "        ${COMMAND}deploy${CLEAN}      Deploy the Docker images/Helm char"
    echo -e ""
    echo -e "${SUBTITLE}Commands for production environment${CLEAN}"
    echo -e ""
    echo -e "        ${COMMAND}install${CLEAN}     Install the current Libreshare version"
    echo -e "        ${COMMAND}upgrade${CLEAN}     Upgrade the current Libreshare installation to the latest version"
    echo -e "        ${COMMAND}uninstall${CLEAN}   Delete the current Libreshare installation with all it's data"
    echo -e "        ${COMMAND}backup${CLEAN}      Backup the data of the Libreshare installation"
    echo -e ""
    echo -e "${SUBTITLE}General commands${CLEAN}"
    echo -e ""
    echo -e "        ${COMMAND}about${CLEAN}       Show the general information"
    echo -e "        ${COMMAND}help${CLEAN}        Show this help"
    echo -e ""
}

# Check to not execute the script as root user (Prevent user access issues)
if [ "$EUID" -eq 0 ]; then
    echo "Please do not execute this script as root. The script will prompt you for access rights via sudo"
    exit
fi

# Change the directory
pushd "$(dirname "$0")"

command="$1"

case $command in
check)
    checkProject
    ;;
clean)
    cleanProject
    ;;
build)
    buildProject
    ;;
develop)
    developProject
    ;;
package)
    packageProject
    ;;
deploy)
    deployProject
    ;;
install)
    installLibreshare
    ;;
upgrade)
    upgradeLibreshare
    ;;
uninstall)
    uninstallLibreshare
    ;;
backup)
    backupLibreshare
    ;;
about)
    showAbout
    ;;
*)
    showHelp
    ;;
esac

# Change back to the original directory
popd
