a.out: test.o html_writer.o
	g++ -o a.out test.o html_writer.o

test.o: html_writer.hpp test.cpp
	g++ -c -o test.o test.cpp

html_writer.o: html_writer.hpp html_writer.cpp
	g++ -c -o html_writer.o html_writer.cpp