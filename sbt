#!/bin/bash -e

SBT_OPTS="-Xms256M -Xmx512M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256M"
java $SBT_OPTS -jar `dirname $0`/sbt-launch.jar "$@"
