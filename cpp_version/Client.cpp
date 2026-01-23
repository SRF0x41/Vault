#include <iostream>
#include "Client.h"

Client::Client() {
  conn = mysql_init(nullptr);
  if (!mysql_real_connect(conn, "localhost", user.c_str(), "", db_name.c_str(),
                          3306, nullptr, 0)) {
    std::cerr << "Connection failed: " << mysql_error(conn) << "\n";
    return;
  }
  std::cout << "Connected to MariaDB/MySQL\n";
}


void Client::executeQuery(const std::string& query) {
    if (mysql_query(conn, query.c_str())) {
        std::cerr << "Query failed: " << mysql_error(conn) << "\n";
        return;
    }
    MYSQL_RES* result = mysql_store_result(conn);
    if (result) {
        MYSQL_ROW row;
        while ((row = mysql_fetch_row(result))) {
            for (unsigned int i = 0; i < mysql_num_fields(result); i++) {
                std::cout << (row[i] ? row[i] : "NULL") << " ";
            }
            std::cout << "\n";
        }
        mysql_free_result(result);
    } else {
        std::cout << "Query executed successfully (no results)\n";
    }
}


Client::~Client() {
  if (conn)
    mysql_close(conn);
}
