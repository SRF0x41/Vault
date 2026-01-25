#pragma once
#include <sqlite3.h>
#include <string>


class Client {
    public:
    // ====================
    // Constructors Desctructors
    // ====================
    Client();
    ~Client();

    int sendQuery(const std::string &query);
    int closeConnection();

    int getFileIndexHead();
    int getMetadata();

    int dropFileIndex();
    int dropMetadata();

    private:
    sqlite3* database = nullptr;
};