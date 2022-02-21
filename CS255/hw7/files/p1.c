#include <stdio.h>

int f( short x );       // Declare f !!  

int main( int argc, char *argv[])
{
   short a = 2;
   int   b;

   b = f( a );    // **** Use f( )

   printf("a = %d, b = %d\n", a, b);
}