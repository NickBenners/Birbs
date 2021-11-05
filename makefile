.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc

default:
	javac src/*.java -d bin/

clean:
	rm -r $(BINDIR)

run:
	java -cp $(BINDIR) MainApp