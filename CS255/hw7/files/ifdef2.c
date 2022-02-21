  #define NODEBUG   (This defines DEBUG to be the empty string !)

   int main( )
   {
      int x, y;

      x = 3;
      y = 4;


      #ifdef  DEBUG
      printf("x = %d\n", x);  // Print variable x for debugging    
      printf("y = %d\n", y);  // Print variable x for debugging
      #endif
   }