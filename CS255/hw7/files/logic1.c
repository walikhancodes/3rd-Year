
#include <stdio.h>
#include <stdbool.h>


int main( int argc, char* argv[] )
{
   printf("true  = %d\n",   true);
   printf("false = %d\n\n", false);

   if ( 1 )
      printf("value 1 is true\n");
   else
      printf("value 1 is false\n");

   if ( 0 )
      printf("value 0 is true\n");
   else
      printf("value 0 is false\n");

   if ( 0.1 )
      printf("value 0.1 is true\n");
   else
      printf("value 0.1 is false\n");

   if ( 0.0 )
      printf("value 0.0 is true\n");
   else
      printf("value 0.0 is false\n");                  
}