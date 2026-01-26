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

    int incrementExtensionCount(const std::string &extension);
    int decrementExtensionCount(const std::string &extension);

    int incrementExtensionCount_getcount(const std::string &extension);
    int decrementExtensionCount_getcount(const std::string &extension);


    private:
    sqlite3* database = nullptr;
};