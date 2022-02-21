#include <stdio.h>
int main( int argc, char *argv[])
{
   int A[5] = {1, 2, 4, 5, 6}; // Array of 5 elements
    int B[3] = {33, 5, 9}; // Array of 3 elements
    int sum;

    sum = SumArray( A, 5); // Call SumArray with A and 5 (= size of array A)
    printf("sum: %d\n", sum);
    sum = SumArray( B, 3); // Call SumArray with B and 3 (= size of array B)
    printf("sum: %d\n", sum);
 
}

int SumArray(int A[], int b){ 
    int final = 0;  
    for(int i = 0; i < b; i++){
          final += A[i]; 
    }
    return final;
}