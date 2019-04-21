call mvn compile 
start mvn exec:java -Dexec.args="OUT_OF_MOVES"

timeout 10

start chrome 127.0.0.1:4567 --new-window

start chrome 127.0.0.1:4567 --new-window --incognito
