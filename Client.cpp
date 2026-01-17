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

Client::~Client() {
  if (conn)
    mysql_close(conn);
}
