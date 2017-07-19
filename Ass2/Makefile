# 201568425 204687842
# Adiel Cahana Ori Engelberg
compile: bin
	javac -d bin -cp biuoop-1.4.jar src/*.java
run2:
	java -cp biuoop-1.4.jar:bin AbstractArtDrawing
run3.2:
	java -cp biuoop-1.4.jar:bin BouncingBallAnimation
run3.3:
	java -cp biuoop-1.4.jar:bin MultipleBouncingBallsAnimation 40 35 30 25 20 15 10 9 8 7 6 5 4 3 2 1
run3.4:
	java -cp biuoop-1.4.jar:bin MultipleFramesBouncingBallsAnimation 40 35 30 25 20 15 10 9 8 7 6 5 4 3 2 1
check:
	java -jar checkstyle-5.7-all.jar -c biuoop.xml src/*.java
bin:
	mkdir bin