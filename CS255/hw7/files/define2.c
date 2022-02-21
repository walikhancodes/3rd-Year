#define square(x)   (x*x)       // Becareful !!!

int main( int argc, char* argv[] )
{  
   double a, b, z;
   
   z = square( a+b );    //   a+b*a+b  =  a + (a*b) + b  !=  (a+b)^2 !!      
}  