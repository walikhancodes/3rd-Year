 #include <stdio.h>
int main(int argc, char* argv[] )
{
   int i;
   short s;
   double d;

   i = 9827563;
   s = i;	/* Unsafe conversion, allowed in C !!! */              

   printf( "i = %d , s = %d \n", i, s );

   d = 9827563.444;
   i = d;	/* Unsafe conversion, allowed in C !!! */

   printf( "d = %lf , i = %d \n", d, i );

   d = 9827563.444;
   s = d;	/* Unsafe conversion, allowed in C !!! */

   printf( "d = %lf , s = %d \n", d, s );
}