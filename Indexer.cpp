#include "Indexer.h"
#include "FileAnalyzer.h"
#include <cstddef>
#include <filesystem>
#include <iostream>
Indexer::Indexer() {};

void Indexer::index(const std::string &root_path) {
  try {
    // Iterate recursively through all files and directories
    for (const auto &entry :
         std::filesystem::recursive_directory_iterator(root_path)) {
      if (std::filesystem::is_regular_file(entry.path())) { // only print files

        if (FileAnalyzer::isMicrosoftCompressedXML(entry.path())) {
          std::cout << entry.path() << "\n";

          // Test get size of file
          size_t file_size_bytes = FileAnalyzer::getSize(entry.path());
          std::cout << "FILE SIZE: " << file_size_bytes << "\n";

          // Test get name of file
          std::cout << "FILE NAME: " << FileAnalyzer::getName(entry.path())
                    << "\n";

          // Test get extension of file
          std::cout << "FILE EXTENSION: " << FileAnalyzer::getExt(entry.path())
                    << "\n";

          // Test get keywords
          // std::cout << "KEYWORDS: " <<
          // FileAnalyzer::getKeywords(entry.path()) << " \n";
          // FileAnalyzer::getKeywords(entry.path());
        }
      }
    }
  } catch (const std::filesystem::filesystem_error &e) {
    std::cerr << "Filesystem error: " << e.what() << "\n";
  } catch (const std::exception &e) {
    std::cerr << "Error: " << e.what() << "\n";
  }
}

Indexer::~Indexer() {};