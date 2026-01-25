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

        // ====================
        // Get basic metadata on the file
        // ====================

        // Test get name of file
        std::cout << "FILE NAME: " << FileAnalyzer::getName(entry.path())
                  << "\n";

        // Test get extension of file
        std::cout << "FILE EXTENSION: " << FileAnalyzer::getExt(entry.path())
                  << "\n";

        std::cout << "FILE PATH: " << entry.path() << "\n";

        // Test get size of file
        size_t file_size_bytes = FileAnalyzer::getSize(entry.path());
        std::cout << "FILE SIZE: " << file_size_bytes << "\n";

        std::cout << "LAST WRITE TIME ISO: "
                  << FileAnalyzer::getLastModifiedISO(entry.path()) << '\n';

        std::cout << "LAST WRITE TIME UNIX: "
                  << FileAnalyzer::getLastModifiedUnixTime(entry.path())
                  << '\n';

        std::cout << "FILE PERMISSIONS: "
                  << FileAnalyzer::getPermissions(entry.path()) << '\n';

        // ====================
        // Parsers
        // ====================

        if (FileAnalyzer::isDOCX(entry.path())) {
          // FileAnalyzer::extractDOCX_text(entry.path());
        }

        if (FileAnalyzer::isRawText(entry.path())) {
          // FileAnalyzer::extractRaw_text(entry.path());
        }

        if (FileAnalyzer::isPDF(entry.path())) {
        }

        // Test get keywords
        // std::cout << "KEYWORDS: " <<
        // FileAnalyzer::getKeywords(entry.path()) << " \n";
        // FileAnalyzer::getKeywords(entry.path());
      }
    }
  } catch (const std::filesystem::filesystem_error &e) {
    std::cerr << "Filesystem error: " << e.what() << "\n";
  } catch (const std::exception &e) {
    std::cerr << "Error: " << e.what() << "\n";
  }
}

Indexer::~Indexer() {};