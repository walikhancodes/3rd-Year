#include <stdio.h>
#include <stdbool.h>


int main( int argc, char* argv[] )
{
   int Done;            // int var pretending to be a Boolean variable

   int k; 
   int Help; 
   int N = 10;
   int A[10] = {10, 5, 7, 8, 2, 1, 4, 3, 6, 9}; // Array, discussed later          

    /* ------------------------------------------------------
       Bubble Sort code from pj7
       ------------------------------------------------------ */
    Done = false;             // *** Use boolean variable
    k = 1;

    while ( ! Done )    
    {
       Done = true;          // *** Use boolean variable

       for (int i = 0; i < N-k; i++)
       {
          if (A[i] > A[i+1])
          {
             Help = A[i];
             A[i] = A[i+1];
             A[i+1] = Help;
             Done = false;          // Not sorted...
          }
       }

       k = k + 1;
    }

    for(int i = 0; i < 10; i++){
        printf("%d\n", A[i]);
    }
}