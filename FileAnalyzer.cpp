#include "FileAnalyzer.h"
#include <filesystem>
#include <fstream>
#include <iostream>
#include <string>
#include <unordered_set>

std::size_t FileAnalyzer::getSize(const std::string &path) {
  return std::filesystem::file_size(path);
}

std::string FileAnalyzer::getName(const std::string &path) {
  return std::filesystem::path(path).filename().string();
}

std::string FileAnalyzer::getExt(const std::string &path) {
  return std::filesystem::path(path).extension().string();
}

bool FileAnalyzer::isCompressed(const std::string &path) {
  std::ifstream file(path, std::ios::binary);
  if (!file)
    return false;

  unsigned char header[4];
  file.read(reinterpret_cast<char *>(header), 4);

  return header[0] == 0x50 && header[1] == 0x4B && header[2] == 0x03 &&
         header[3] == 0x04;
}

bool FileAnalyzer::isMicrosoftCompressedXML(const std::string &path) {
  static const std::unordered_set<std::string>
      MicrosoftCompressedXML_format_extension = {".docx", ".xlsx", ".pptx",
                                                 ".epub"};
  return MicrosoftCompressedXML_format_extension.find(getExt(path)) !=
         MicrosoftCompressedXML_format_extension.end();
}

bool FileAnalyzer::isPDF(const std::string &path) {
  std::ifstream file(path, std::ios::binary);
  if (!file)
    return false;

  char header[4];
  file.read(header, 4);
  return std::string(header, 4) == "%PDF";
}

bool FileAnalyzer::isRawText(const std::string &path) {
  static const std::unordered_set<std::string> textExts = {
      ".txt", ".md", ".log", ".csv", ".json", ".xml", ".yaml", ".ini"};

  return textExts.find(getExt(path)) != textExts.end();
}

std::vector<std::string> FileAnalyzer::getKeywords(const std::string &path) {
  std::ifstream input_file(path);
  if (!input_file.is_open()) {
    std::cerr << "Error opening stop words file." << "\n";
    return {}; // Return empty vector on fail
  }
  if (isRawText(path)) {
    std::string line;
    while (input_file >> line) {
      std::cout << line;
    }
  }
  return {};
}