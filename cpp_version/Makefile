# Compiler
CXX = g++
CXXFLAGS = -std=c++17 -Wall -O2
LIBS = $(shell pkg-config --cflags --libs mariadb libzip sqlite3)

TARGET = vault_ver2
SRC = main.cpp Indexer.cpp FileAnalyzer.cpp SQlite_Client.cpp
OBJ = $(SRC:.cpp=.o)

all: $(TARGET)

$(TARGET): $(OBJ)
	$(CXX) $(CXXFLAGS) $(OBJ) -o $(TARGET) $(LIBS)

%.o: %.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

clean:
	rm -f $(OBJ) $(TARGET)
