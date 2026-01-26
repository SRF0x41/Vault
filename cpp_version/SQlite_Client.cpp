#include "SQlite_Client.h"
#include <cstddef>
#include <format>
#include <iostream>
#include <sqlite3.h>
#include <string>

Client::Client() {
  if (sqlite3_open("FileIndex.db", &database)) {
    std::cerr << "Error opening FileIndex database." << '\n';
    return;
  } else {
    std::cerr << "FileIndex database opened." << '\n';
  }
}
/*#include <iostream>
#include <sqlite3.h>

int main(int argc, char** argv)
{
    sqlite3* DB;
    std::string sql = "CREATE TABLE PERSON("
                      "ID INT PRIMARY KEY     NOT NULL, "
                      "NAME           TEXT    NOT NULL, "
                      "SURNAME          TEXT     NOT NULL, "
                      "AGE            INT     NOT NULL, "
                      "ADDRESS        CHAR(50), "
                      "SALARY         REAL );";
    int exit = 0;
    exit = sqlite3_open("example.db", &DB);
    char* messaggeError;
    exit = sqlite3_exec(DB, sql.c_str(), NULL, 0, &messaggeError);

    if (exit != SQLITE_OK) {
        std::cerr << "Error Create Table" << std::endl;
        sqlite3_free(messaggeError);
    }
    else
        std::cout << "Table created Successfully" << std::endl;
    sqlite3_close(DB);
    return (0);
} */

int Client::sendQuery(const std::string &query) {
  int exit_status = 0;
  char *messaggeError;
  exit_status = sqlite3_exec(database, query.c_str(), NULL, 0, &messaggeError);
  if (exit_status != SQLITE_OK) {
    std::cerr << "Error sending query " << std::endl;
    sqlite3_free(messaggeError);
    return 1;
  }

  return 0;
}

int Client::getFileIndexHead() {
  if (!Client::sendQuery("select * from file_index limit 10;")) {
    std::cerr << "Error getting file_index head." << '\n';
    return 1;
  }
  return 0;
}

/*INSERT INTO files (path, size, mode)
VALUES ('/home/user/test.txt', 1024, 420);

UPDATE table_name
SET counter = counter + 1
WHERE id = 1
RETURNING counter;

*/

int Client::incrementExtensionCount(const std::string &extension) {

  // std::string query = std::format("UPDATE file_index_metadata SET n_{0} =
  // n_{0} + 1 WHERE id = 1",extension.substr(1));

  //         if (!Client::sendQuery("INSERT INTO file_index_metadata "
  //                                ""))
  // SELECT n_txt FROM file_index_metadata WHERE id = 1;

  /*std::string q = std::format(
            "INSERT INTO file_index "
            "(file_name,file_extension,file_path,file_size_bytes,"
            "file_last_modified,file_permissions) "
            "VALUES ('{}','{}','{}',{},{},{})",
            escape(FileAnalyzer::getName(entry.path())),
            escape(FileAnalyzer::getExt(entry.path())),
            escape(entry.path().string()), FileAnalyzer::getSize(entry.path()),
            FileAnalyzer::getLastModifiedUnixTime(entry.path()),
            FileAnalyzer::getPermissions_int(entry.path()));
            UPDATE file_index_metadata
SET n_txt = n_txt + 1
WHERE id = 1;
            */
  std::string q = std::format(
      "UPDATE file_index_metadata SET n_{} = n_{} + 1 WHERE id = 1;",
      extension.substr(1));

  if (!Client::sendQuery(q)) {
    std::cerr << "Error incrementing metadata extension.\n";
    return 1;
  }
  return 0;
}

int Client::decrementExtensionCount(const std::string &extension) {}

int Client::incrementExtensionCount_getcount(const std::string &extension) {}
int Client::decrementExtensionCount_getcount(const std::string &extension) {}
int Client::getMetadata() { return 0; };

int Client::dropFileIndex() {
  if (!Client::sendQuery("DELETE FROM file_index")) {
    std::cerr << "Error deleting contents from file_index.\n";
    return 1;
  } else {
    std::cout << "file_index deleted.\n";
  }
  return 0;
};
int Client::dropMetadata() {
  if (!Client::sendQuery("DELETE FROM file_index_metadata")) {
    std::cerr << "Error deleting contents from file_index_metadata.\n";
    return 1;
  } else {
    std::cout << "file_index_metadata deleted.\n";
  }
  return 0;
};
int Client::closeConnection() {
  sqlite3_close(database);
  return 0;
};

Client::~Client() { sqlite3_close(database); }