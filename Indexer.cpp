#include <iostream>
#include <filesystem>
#include "Indexer.h"
Indexer::Indexer(){};

void Indexer::index(const std::string &root_path){
    try {
        // Iterate recursively through all files and directories
        for (const auto& entry : std::filesystem::recursive_directory_iterator(root_path)) {
            if (std::filesystem::is_regular_file(entry.path())) { // only print files
                std::cout << entry.path() << "\n";
            }
        }
    } catch (const std::filesystem::filesystem_error& e) {
        std::cerr << "Filesystem error: " << e.what() << "\n";
    } catch (const std::exception& e) {
        std::cerr << "Error: " << e.what() << "\n";
    }
}


Indexer::~Indexer(){};