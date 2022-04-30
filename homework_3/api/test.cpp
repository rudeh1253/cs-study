#include <iostream>
#include "html_writer.hpp"

void PrintTestMesssage(const std::string& msg) {
    std::cout << "desired:\n" << msg << std::endl;
    std::cout << "\ntest result:\n";
}

// @Test
void AddTitle() {
    std::string expected = "  <title>Image Browser</title>\n  <head>\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />\n  </head>";
    

    std::cout << "desired:\n" << expected << std::endl;
    std::cout << "\ntest result:\n";
    html_writer::AddTitle("IMAGE LOADER");
    std::cout << std::endl;
}

// @Test
void OpenBody() {
    std::string expected = "  <body>\n";
    PrintTestMesssage(expected);
    html_writer::OpenBody();
    std::cout << std::endl;
}

// @Test
void CloseBody() {
    std::string expected = "  </body>\n";
    PrintTestMesssage(expected);
    html_writer::CloseBody();
    std::cout << std::endl;
}

int main(void) {
    OpenBody();
    CloseBody();

    return 0;
}