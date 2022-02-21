 #include <stdio.h>

   float square( float x )                  
   {
      float r;         // Define a local variable         

      r = x * x;       // Statement

      return ( r );    // Return statement
   }

   int main( int argc, char *argv[] )
   {
      float a, b;

      a = 4.0;

      b = square( a );  // Call function square

      printf("a = %f, b = %f\n", a, b);              
   }