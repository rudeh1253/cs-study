#include <iostream>
#include <random>
#include <cstdlib>
using namespace std;

enum CHECK_FLAGS { ERROR, CONTINUE, TERMINATE };

typedef int Flag;

Flag guess(int guess, int desiredNumber, bool err);
bool isError(int checked, bool inp);

int main(void) {
    random_device randGenerator;
    int desiredNumber = randGenerator() % 100;

    Flag f;
    do {
        int g;
        cin >> g;
        f = guess(g, desiredNumber, isError(g, cin.fail()));

        if (f == CHECK_FLAGS::ERROR) {

            return EXIT_FAILURE;
        }
    } while (f != CHECK_FLAGS::TERMINATE);

    return EXIT_SUCCESS;
}

Flag guess(int guess, int desiredNumber, bool err) {
    if (err) {

        return CHECK_FLAGS::ERROR;
    }

    if (guess < desiredNumber) {
        cout << "larger\n";

        return CHECK_FLAGS::CONTINUE;
    } else if (guess > desiredNumber) {
        cout << "smaller\n";

        return CHECK_FLAGS::CONTINUE;
    }

    return CHECK_FLAGS::TERMINATE;
}

bool isError(int checked, bool inp) {
    if (inp) {
        cerr << "Error encountered, exiting...\n";

        return true;
    }

    if (checked < 0 || checked >= 100) {
        cerr << "[WARNING] : Number must be between 0 and 99\n";

        return true;
    }
}