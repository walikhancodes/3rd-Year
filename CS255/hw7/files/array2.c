
   #include <stdio.h>

   int main( int argc, char* argv[] )
   {
      double a[10];
      int    i;
    
      for ( i = 0; i < 10; i++ )
   	 a[i] = i;
    
      for ( i = 0; i < 30; i++ )
   	 printf( "a[%d] = %lf\n", i, a[i] );               
   }