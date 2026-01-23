#include <iostream>
#include <string>
#include "Client.h"
#include "Indexer.h"

int main() {

    std::string root_path = "/home/user1/Desktop/MacBackup3_25_2025";
    std::string input_line;

    Client db_client;
    Indexer indexer;

    while(input_line != "exit"){
        std::getline(std::cin, input_line);
        if(input_line == "index"){
            indexer.index(root_path);
        }
    }
}
