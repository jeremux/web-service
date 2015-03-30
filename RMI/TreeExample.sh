#!/bin/sh

xterm -geometry "60x20+38+10"    -title "Noeud 1 - port 10001" -e java -jar ServeurTree.jar 10001 "1" & sleep 0.1;
xterm -geometry "60x20+438+10"  -title "Noeud 2 - port 10002" -e java -jar ServeurTree.jar 10002 "2" localhost:10001 & sleep 0.1;
xterm -geometry "60x20+838+10" -title "Noeud 3 - port 10003" -e java -jar ServeurTree.jar 10003 "3" localhost:10002 & sleep 0.1;
xterm -geometry "60x20+38+400" -title "Noeud 4 - port 10004" -e java -jar ServeurTree.jar 10004 "4" localhost:10002 & sleep 0.1;
xterm -geometry "60x20+438+400" -title "Noeud 5 - port 10005" -e java -jar ServeurTree.jar 10005 "5" localhost:10001 & sleep 0.1;
xterm -geometry "60x20+838+400" -title "Noeud 6 - port 10006" -e java -jar ServeurTree.jar 10006 "6" localhost:10005 & sleep 0.1;

