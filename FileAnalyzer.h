#pragma once
#include <iostream>
#include <string>
#include <vector>

class FileAnalyzer {
public:
  FileAnalyzer();
  ~FileAnalyzer();

  static std::size_t getSize(const std::string &path);
  static std::string getName(const std::string &path);
  static std::string getExt(const std::string &path);
  static std::vector<std::string> getKeywords(const std::string &path);

private:
  struct StaticInitializer;
  static StaticInitializer initializer;
};