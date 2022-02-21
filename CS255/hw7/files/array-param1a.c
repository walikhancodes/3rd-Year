    #include <stdio.h>

   void func( double*x )
   {
      int i;
    
      for ( i = 0; i < 30; i++ )
   	 printf( "x[%d] = %lf\n", i, x[i] );          
   }
   int main( int argc, char* argv[] )             
   {
      double a[10];
      int    i;
    
      for ( i = 0; i < 10; i++ ){
   	    a[i] = i;
      }
    
      func(a);
   }
