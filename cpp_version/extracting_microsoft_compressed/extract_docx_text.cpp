#include <filesystem>
#include <fstream>
#include <iostream>
#include <regex>
#include <string>
#include <zip.h>
#include <zipconf.h>
// g++ test_microsoft.cpp -lzip -o microslop

int extractDOCX_text(const std::string &path);
int main() {
  std::string path =
      "/home/user1/Desktop/MacBackup3_25_2025/PhysicsAstronomy/orion.docx";
  extractDOCX_text(path);
}
int extractDOCX_text(const std::string &path) {
  int err = 0; // Store error codes

  // open the zip in read only
  zip *opened_zip = zip_open(path.c_str(), ZIP_RDONLY, &err);
  if (!opened_zip) {
    std::cerr << "Failed to open zip" << '\n';
    return 1;
  }

  zip_int64_t document_xml_index =
      zip_name_locate(opened_zip, "word/document.xml", 0);
  if (document_xml_index < 0) {
    return 1;
  }

  zip_stat_t st;
  zip_stat_init(&st);
  zip_stat_index(opened_zip, document_xml_index, 0, &st);

  std::cout << st.name << '\n';

  // open file inside zip
  zip_file *zf = zip_fopen_index(opened_zip, document_xml_index, 0);
  if (!zf)
    return 1;
  // create a buffer for storing data chunks, lzip can do 8192 bytes
  char buffer[8192];

  // number of bytes read per iteration
  zip_int64_t n;
  // write to disk 'out'
  while ((n = zip_fread(zf, buffer, sizeof(buffer))) > 0) {

    bool inside_tag = false;
    for (zip_int64_t i = 0; i < n; ++i) {
      char c = buffer[i];
      if (c == '<')
        inside_tag = true;
      else if (c == '>')
        inside_tag = false;
      else if (!inside_tag)
        std::cout << c;
    }
  }

  // close zf
  zip_fclose(zf);

  // close za
  zip_close(opened_zip);

  return 0;
}