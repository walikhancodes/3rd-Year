  
   #include <stdio.h>
  int main( int argc, char* argv[] )
   {
      int i;      
      float x;

      printf( "Enter an integer value:");
      scanf( "%d", &i );
      printf( "i = %d\n", i);

      printf( "Enter a floating point value:");
      scanf( "%f", &x );
      printf( "x = %f\n", x);
   }