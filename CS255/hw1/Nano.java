
import java.util.Arrays;

public class Nano
{
   public static char[] digit 
            = {'#', '!', '%', '@', '(', ')', '[', ']', '$'};


   /* ==========================================================
      Return the 2's complement binary representation for the
      Nano number given in String s
      ========================================================== */
   public static int power(int k){
      int r = 1;
      for(int i = 0; i < k; i++){
         r *= 9;
      }
      return r;
   }
   public static int parseNano(String s)
   {
      /* ------------------------------------------------------------------
         This loop checks if the input contains an illegal (non-Nano) digit
         ------------------------------------------------------------------ */
      for (int i = 0 ; i < s.length(); i++)
      {
         int j = 0;
         while ( j < digit.length )
         {
            if ( s.charAt(i) == digit[j] || s.charAt(i) == '-' )
            {
               break;
            }

            j++;
         }

         if ( j >= digit.length )
         {
            System.out.println("Illegal nano digit found in input: " 
					+ s.charAt(i) );
            System.out.println("A Nano digit must be one of these: " 
				+ Arrays.toString (digit) );
            System.exit(1);
         }
      }

      // Write the parseNano() code here
      int [] digit = new int[50];
      int value;
      int sign;
      int pos;
      int len;
      if(s.charAt(0) == '-'){
         sign = -1;
         for(int i = 1; i < s.length(); i++){
            if(s.charAt(i) == '#' || s.charAt(i) == '%'){
               digit[i-1] = s.charAt(i) - 35;
            }
            else if(s.charAt(i) == '(' || s.charAt(i) == ')'){
               digit[i-1] = s.charAt(i) - 36;

            }
            else if (s.charAt(i) == '!'){
               digit[i-1] = s.charAt(i) - 32;

            }
            else if(s.charAt(i) == '@'){
               digit[i-1] = s.charAt(i) - 61;

            }
            else if(s.charAt(i) == '['){
               digit[i-1] = s.charAt(i) - 85;
            }
            else if(s.charAt(i) == ']'){
               digit[i-1] = s.charAt(i) - 86;
            }
            else if (s.charAt(i) == '$'){
               digit[i-1] = s.charAt(i) - 28;
            }
            else{}

         }
         len = s.length() - 1;
      }
      else{
         sign = 1;
         for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '#' || s.charAt(i) == '%'){
               digit[i] = s.charAt(i) - 35;
            }
            else if(s.charAt(i) == '(' || s.charAt(i) == ')'){
               digit[i] = s.charAt(i) - 36;

            }
            else if (s.charAt(i) == '!'){
               digit[i] = s.charAt(i) - 32;

            }
            else if(s.charAt(i) == '@'){
               digit[i] = s.charAt(i) - 61;

            }
            else if(s.charAt(i) == '['){
               digit[i] = s.charAt(i) - 85;
            }
            else if(s.charAt(i) == ']'){
               digit[i] = s.charAt(i) - 86;
            }
            else if (s.charAt(i) == '$'){
               digit[i] = s.charAt(i) - 28;
            }
            else{}

         }
         len = s.length();

      }
      value = 0;
      for(int k = 0; k <len; k++){
         pos = (len - 1) - k;
         value = value + digit[k]*power(pos);

      }
      if(sign == -1){
         value = -value;
      }
      return value;
   }

   
      
   

    // ==========================================================
    //   Return the String of Nano digit that represent the value
    //   of the 2's complement binary number given in 
    //   the input parameter 'value'
    //   ========================================================== 
   public static String toString(int value)
   {
      // Write the toString() code here
      boolean valueIsNeg;
      int remainder [] = new int[100];
      char digit[] = new char[100];
      String result;
      int nDigits;
      if(value < 0){
         valueIsNeg = true;
         value = -value;
      }
      else{
         valueIsNeg = false;
      }

      nDigits = 0;
      if(value == 0){
         return "#";
      }
      while (value > 0){
         remainder[nDigits] = value % 9;
         nDigits++;
         value = value / 9;
      }
      for(int i = 0; i < nDigits; i++){
         if(remainder[i] == 0 || remainder[i] == 2){
            digit[i] = (char)(remainder[i] + 35);
         }
         else if(remainder[i] == 4|| remainder[i] == 5){
            digit[i] = (char)(remainder[i] + 36);
         }
         else if(remainder[i] == 1){
            digit[i] = (char)(remainder[i] + 32);
         }
         else if(remainder[i] == 3){
            digit[i] = (char)(remainder[i] + 61);
         }
         else if(remainder[i] == 6){
            digit[i] = (char)(remainder[i] + 85);
         }
         else if(remainder[i] == 7){
            digit[i] = (char)(remainder[i] + 86);
         }
         else if(remainder[i] == 8){
            digit[i] = (char)(remainder[i] + 28);
         }
         else{}
      }

      result = "";
      for(int i = nDigits - 1; i >=0; i--){
         result = result + digit[i]; 
      }
      if(valueIsNeg == true){
         result = "-" + result;
      }
      else{
         result = result;
      }
      return result;
   }

}

