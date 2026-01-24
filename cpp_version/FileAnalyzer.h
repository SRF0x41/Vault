#pragma once
#include <string>
#include <unordered_set>
#include <vector>

class FileAnalyzer {
public:
  static std::size_t getSize(const std::string &path);
  static std::string getName(const std::string &path);
  static std::string getExt(const std::string &path);

  static bool isPDF(const std::string &path);
  static bool isCompressed(const std::string &path);
  static bool isRawText(const std::string &path);
  static bool isMicrosoftCompressedXML(const std::string &path);
  static bool isStopWord(const std::string &word);

  static std::vector<std::string> getKeywords(const std::string &path);

private:
  static const std::unordered_set<std::string_view> stopWords;

  FileAnalyzer() = delete; // 🔒 prevent instantiation
};
