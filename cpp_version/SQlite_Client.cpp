#include "SQlite_Client.h"
#include <cstddef>
#include <iostream>
#include <sqlite3.h>

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

int Client::getFileIndexHead(){}
int getMetadata();

int dropFileIndex();
int dropMetadata();
int closeConnection();

Client::~Client() { sqlite3_close(database); }