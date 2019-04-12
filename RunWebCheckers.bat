call mvn compile 
start mvn exec:java

timeout 20

start chrome 127.0.0.1:4567 --new-window

start chrome 127.0.0.1:4567 --new-window --incognito
