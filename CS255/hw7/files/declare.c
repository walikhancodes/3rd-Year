#include <stdio.h>

int main(int argc, char *argv[])
{
   short x = 2;   // *** Short !!
   int y = 0;

   y = f(x);    // C compiler will assume:  int f(int x) !!! 
                // Compiler will convert short x --> int and pass to func !!     

   printf("x = %d, y = %d\n", x, y);
}


/* ---------------------------------------------------
   Function f( ) is defined AFTER main's use of f( )
   --------------------------------------------------- */    
int f(int x)
{
   return(x*x);                
}