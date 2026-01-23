# Compiler
CXX = g++
CXXFLAGS = -std=c++17 -Wall -O2
LIBS = $(shell pkg-config --cflags --libs mariadb)

TARGET = vault_ver2
SRC = main.cpp Client.cpp Indexer.cpp FileAnalyzer.cpp
OBJ = $(SRC:.cpp=.o)

all: $(TARGET)

$(TARGET): $(OBJ)
	$(CXX) $(CXXFLAGS) $(OBJ) -o $(TARGET) $(LIBS)

%.o: %.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

clean:
	rm -f $(OBJ) $(TARGET)
