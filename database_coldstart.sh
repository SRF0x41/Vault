#!/bin/bash

# Assumes mysql has already been installed

# Check if mysql has been installed

if command -v mysql; then
    echo "mysql is installed";
else
    echo "install mysql exiting cold start...";
    exit 0;
fi

# Creating database
# stranger danger, create the db and table
command mysql -u root -e "CREATE DATABASE FileIndex; USE FileIndex; CREATE TABLE file_index(
    file_id INT PRIMARY KEY,
    file_size BIGINT,
    file_name VARCHAR(255),
    file_extension VARCHAR(50),
    file_path VARCHAR(1024),
    file_keywords VARCHAR(5000)
);
exit;"

exit 0;

