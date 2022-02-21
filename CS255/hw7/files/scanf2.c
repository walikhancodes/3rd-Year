   #include <stdio.h>

int main( int argc, char* argv[] )
   {
      int i;

      printf( "Enter an integer value: ");

      while ( scanf( "%d", &i ) != EOF )
      {
         printf( "i = %d\n", i);
         printf( "Enter another integer value (type ^D to end): ");      
      }

      printf( "\nDONE !\n" );
   }