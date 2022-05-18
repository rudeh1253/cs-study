#include <iostream>
#include <array>
using namespace std;

/** Incompleted! */

template <int SIZE>
int CountLongSubarrays(const array<int, SIZE>&);

template <int SIZE>
void test(const array<int, SIZE>&, int);

int main(void) {
    test<5>(array<int, 5> { 2, 2, 4, 1, 4 }, 2);
    test<8>(array<int, 8> { 7, 8, 5, 7, 7, 3, 2, 8 }, 3);
    test<11>(array<int, 11> { 7, 7, 9, 1, 2, 11, 9, 6, 2, 8, 9 }, 2);
    test<18>(array<int, 18> { 4, 18, 10, 8, 13, 16, 18, 1, 9, 6, 11, 13, 12, 5, 7, 17, 13, 3 }, 1);
    test<25>(array<int, 25> { 11, 16, 10, 19, 20, 18, 3, 19, 2, 1, 8, 17, 7, 13, 1, 11, 1, 18, 19, 9, 7, 19, 24, 2, 12 }, 4);
    return 0;
}

template <int SIZE>
int CountLongSubarrays(const array<int, SIZE>& arr) {
    int numOfArrays = 0;
    int lenOfLongest = 0;
    int lenCache = 0;
    int before = -1;

    int tmpCount = 0;

    auto iter = arr.begin();
    for (; iter != arr.end(); iter++) {
        lenCache++;
        if (*iter <= before) {
            lenCache--;
            if (lenCache == lenOfLongest) {
                numOfArrays++;
            } else if (lenCache > lenOfLongest) {
                numOfArrays = 1;
                lenOfLongest = lenCache;
            }
            lenCache = 1;
            before = -1;
        } else {
            before = *iter;
        }
    }
    if (*iter > before) {
        if (lenCache == lenOfLongest) {
            numOfArrays++;
        } else if (lenCache > lenOfLongest) {
            numOfArrays = 1;
        }
    }
    return numOfArrays;
}

template <int SIZE>
void test(const array<int, SIZE>& arr, int expected) {
    if (CountLongSubarrays<SIZE>(arr) == expected) {
        cout << "success" << endl;
    } else {
        cout << "fail" << endl;
    }
}