#!/bin/bash

# Script to count and identify Java services running on the system

echo "=== Java Services Summary ==="
echo ""

# Count total Java processes
total=$(ps aux | grep "[j]ava" | wc -l | tr -d ' ')
echo "Total Java processes: $total"
echo ""

# Group by application
ps aux | grep "[j]ava" | awk '
{
  app = "Unknown"
  mainClass = ""
  pid = $2
  
  # Find main class or application identifier
  for(i=11; i<=NF; i++) {
    if ($i ~ /SpringBootEmailServiceApplication/) {
      app = "email-service"
      mainClass = "SpringBootEmailServiceApplication"
      break
    }
    else if ($i ~ /com\.rslakra\.aopservice\.AopService$/) {
      app = "spring-aop-service"
      mainClass = "AopService"
      break
    }
    else if ($i ~ /surefirebooter/) {
      app = "maven-test"
      break
    }
    else if ($i ~ /Launcher.*email-service/) {
      app = "maven-email-service"
      break
    }
    else if ($i ~ /Launcher.*aop-service/) {
      app = "maven-aop-service"
      break
    }
    else if ($i ~ /Launcher/) {
      app = "maven-build"
      break
    }
  }
  
  # Store information
  count[app]++
  if (mainClass != "") {
    apps[app] = apps[app] "\n  PID " pid " - " mainClass
  } else {
    apps[app] = apps[app] "\n  PID " pid
  }
}
END {
  # Print summary grouped by application
  for (app in count) {
    if (app == "email-service" || app == "spring-aop-service") {
      print "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
      printf "Application: %s (%d process%s)\n", app, count[app], (count[app] > 1 ? "es" : "")
      print apps[app]
      print ""
    }
  }
  
  # Print other processes summary
  other_count = 0
  for (app in count) {
    if (app != "email-service" && app != "spring-aop-service") {
      other_count += count[app]
    }
  }
  
  if (other_count > 0) {
    print "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    print "Other processes (Maven builds/tests): " other_count
    for (app in count) {
      if (app != "email-service" && app != "spring-aop-service") {
        printf "  %s: %d process(es)\n", app, count[app]
      }
    }
  }
}'

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "Quick Commands:"
echo "  Kill all email-service:     pkill -f SpringBootEmailServiceApplication"
echo "  Kill all aop-service:        pkill -f 'com.rslakra.aopservice.AopService'"
echo "  Kill all Maven processes:    pkill -f 'maven.*Launcher'"
echo "  Kill all test processes:     pkill -f surefire"

