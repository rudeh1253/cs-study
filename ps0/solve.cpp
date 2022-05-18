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
    int num_of_subarrays = 0;
    int len_of_longest = 1;
    int len_cache = 0;

    int tmp = 0;

    for (auto iter = arr.begin(); iter != arr.end() - 1; iter++) {
        len_cache++;

        if (*iter >= *(iter + 1)) {
            if (len_cache == len_of_longest) {
                num_of_subarrays++;
            } else if (len_cache > len_of_longest) {
                len_of_longest = len_cache;
                num_of_subarrays = 1;
            }
            len_cache = 0;
        }
    }
    if (*(arr.end() - 1) > *(arr.end() - 2)) {
        len_cache++;
    } else {
        len_cache = 1;
    }
    if (len_cache == len_of_longest) {
        num_of_subarrays++;
    } else if (len_cache > len_of_longest) {
        num_of_subarrays = 1;
    }
    return num_of_subarrays;
}

template <int SIZE>
void test(const array<int, SIZE>& arr, int expected) {
    if (CountLongSubarrays<SIZE>(arr) == expected) {
        cout << "success" << endl;
    } else {
        cout << "fail" << endl;
    }
}