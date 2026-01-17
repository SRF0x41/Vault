#pragma once
#include <mysql/mysql.h>
#include <string>
#include <iostream>

class Client {
private:
    MYSQL* conn;
    std::string user = "vault_test_user";
    std::string db_name = "FileIndex";

public:
    Client();   // Constructor
    ~Client();  // Destructor
};
