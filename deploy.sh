#!/usr/bin/env bash
# Déploie l'application Medecine dans Tomcat.
# Usage : ./deploy.sh [chemin/vers/tomcat]

set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
TOMCAT_DIR="${1:-/home/aina/tomcat}"
WEBAPPS_DIR="$TOMCAT_DIR/webapps"
APP_NAME="medecine"
WAR_FILE="$PROJECT_DIR/target/$APP_NAME-1.0-SNAPSHOT.war"
FRAMEWORK_JAR="$PROJECT_DIR/lib/aina-0.0.1-SNAPSHOT.jar"

fail() {
    echo "[ERREUR] $1" >&2
    exit 1
}

[ -f "$PROJECT_DIR/pom.xml" ] || fail "pom.xml introuvable dans $PROJECT_DIR"
[ -f "$FRAMEWORK_JAR" ] || fail "JAR du framework introuvable : $FRAMEWORK_JAR"
[ -d "$WEBAPPS_DIR" ] || fail "Dossier Tomcat introuvable : $WEBAPPS_DIR"

if command -v mvn >/dev/null 2>&1; then
    MVN_CMD="mvn"
elif [ -x "$PROJECT_DIR/mvnw" ]; then
    MVN_CMD="./mvnw"
else
    fail "Maven ou le Maven Wrapper est requis."
fi

echo "[1/3] Installation locale du JAR du framework..."
(cd "$PROJECT_DIR" && "$MVN_CMD" -q install:install-file \
    -Dfile="$FRAMEWORK_JAR" \
    -DgroupId=com.mini-framework \
    -DartifactId=aina \
    -Dversion=0.0.1-SNAPSHOT \
    -Dpackaging=jar)

echo "[2/3] Construction du WAR..."
(cd "$PROJECT_DIR" && "$MVN_CMD" -q clean package -DskipTests)
[ -f "$WAR_FILE" ] || fail "WAR non généré : $WAR_FILE"

echo "[3/3] Déploiement dans Tomcat..."
# Retire uniquement cette application ; les autres applications Tomcat restent intactes.
rm -f "$WEBAPPS_DIR/$APP_NAME.war"
rm -rf "$WEBAPPS_DIR/$APP_NAME"
cp "$WAR_FILE" "$WEBAPPS_DIR/$APP_NAME.war"

echo
echo "Déploiement terminé."
echo "URL : http://localhost:8080/$APP_NAME/"
echo "Santé : http://localhost:8080/$APP_NAME/health"
echo "Logs : tail -f $TOMCAT_DIR/logs/catalina.out"
echo "Démarrage Tomcat si nécessaire : $TOMCAT_DIR/bin/startup.sh"
