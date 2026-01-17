#include "FileAnalyzer.h"
#include <filesystem>
#include <string>

// ---------------- Static initializer ----------------
// get keywords stored into a hashmap
struct FileAnalyzer::StaticInitializer {
  StaticInitializer() {
    std::cout << "Writing stop words to hashmap\n";
    // You can put any code here that should run at program start
    // Example: initialize some global keywords, log info, etc.
  }
};
FileAnalyzer::StaticInitializer FileAnalyzer::initializer;

/*
describe file_index
    -> ;
+----------------+---------------+------+-----+---------+-------+
| Field          | Type          | Null | Key | Default | Extra |
+----------------+---------------+------+-----+---------+-------+
| file_id        | int(11)       | NO   | PRI | NULL    |       |
| file_size      | bigint(20)    | YES  |     | NULL    |       |
| file_name      | varchar(255)  | YES  |     | NULL    |       |
| file_extension | varchar(50)   | YES  |     | NULL    |       |
| file_path      | varchar(1024) | YES  |     | NULL    |       |
| file_keywords  | varchar(5000) | YES  |     | NULL    |       |
+----------------+---------------+------+-----+---------+-------+
6 rows in set (0.005 sec)

*/

std::size_t FileAnalyzer::getSize(const std::string &path) {
  return static_cast<std::size_t>(std::filesystem::file_size(path));
}

std::string FileAnalyzer::getName(const std::string &path) {
  return std::filesystem::path(path).filename();
}

std::string FileAnalyzer::getExt(const std::string &path) {
  return std::filesystem::path(path).extension();
}

std::vector<std::string> getKeywords(const std::string &path) {}

// Constructor
FileAnalyzer::FileAnalyzer() {};

// Desctructor
FileAnalyzer::~FileAnalyzer() {};