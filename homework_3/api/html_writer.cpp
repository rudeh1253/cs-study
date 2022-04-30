#include <iomanip>
#include <iostream>
#include <fstream>
#include <sstream>
#include "html_writer.hpp"

#define STYLE_CSS_FILE_NAME    "style.css"
#define PNG    "png"
#define JPG    "jpg"

void html_writer::OpenDocument() {
    std::cout << "<!DOCTYPE html>\n<html>\n";
}

void html_writer::CloseDocument() {
    std::cout << "</html>\n";
}

void html_writer::AddCSSStyle(const std::string& stylesheet) {
    std::ofstream css_writer;
    css_writer.open(STYLE_CSS_FILE_NAME);
    css_writer << stylesheet;
    css_writer.close();
}

void html_writer::AddTitle(const std::string& title) {
    std::cout << "  <title>" << title << "</title>\n";
    std::cout << "  <head>\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"";
    std::cout << STYLE_CSS_FILE_NAME;
    std::cout << "\" />\n  </head>\n";
}

void html_writer::OpenBody() {
    std::cout << "  <body>\n";
}

void html_writer::CloseBody() {
    std::cout << "  </body>\n";
}

void html_writer::OpenRow() {
    std::cout << "    <div class=\"row\">\n";
}

void html_writer::CloseRow() {
    std::cout << "    </div>";
}

std::string ParseImageFileName(const std::string& img_path) {
    int from = img_path.find_last_of('/');

    return img_path.substr(from + 1);
}

bool CheckExtension(const std::string& img_file_name) {
    std::string ext = img_file_name.substr(img_file_name.find_last_of('.') + 1);
    
    return ext.compare(PNG) == 0 || ext.compare(JPG) == 0;
}

void html_writer::AddImage(const std::string& img_path, float score, bool highlight = false) {
    std::cout << "      <div class=\"column\"";
    if (highlight) {
        std::cout << " style=\"border: 5px solid green;\"";
    }
    std::cout << ">\n";

    std::string file_name = ParseImageFileName(img_path);
    if (CheckExtension) {
        std::cout << "        <h2>" << file_name << "</h2>\n";
        std::cout << "        <img src=\"" << img_path << "\" />";
        std::cout << "        <p>score = " << std::setprecision(2) << std::fixed << score << "</p>\n";
    } else {
        std::cerr << "        <h2>not supported extension: you need to use png or jpg</h2>\n";
    }
    std::cout << "      </div>\n";
}