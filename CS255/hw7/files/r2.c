
#include <stdio.h>

#include "r2.h"  // Declares all functions in r2.c

int main( int argc, char *argv[])
{
   short a = 2;
   int   b;

   b = f( a );
   printf("a = %d, b = %d\n", a, b);

   b = g( a );
   printf("a = %d, b = %d\n", a, b);

   b = h( a );
   printf("a = %d, b = %d\n", a, b);
}