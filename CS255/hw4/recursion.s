
// ====================================================================
// Do not edit the ".global F" line
// ====================================================================
        .global F

	.global if, else1, else2, while, whileEnd 


// **************************************************************************
// You can add more xdef directives above if you need more external labels
//
// - Remember that you can only add a label as a break point (stop location) 
//   in EGTAPI if the label has been xdef'ed !!!
//
// - And I am pretty sure you will need to use break point for debugging 
// - in this project... So add more xdef above to export your breakpoints
// **************************************************************************


/* ************************************************************
   This label marks the address of the recursive function F
   ************************************************************ */
F:

// ********************************************************************
// Put your (recursive) function F here below
//
// F receives the parameters i, j, k on the stack
// F returns the function value in register d0
//
// Make sure you use the "pop {pc}" instruction to return to the caller
//
// Also: Make sure you DE-ALLOCATE the local variables and restore fp
//       BEFORE you return to the caller !!!
// ********************************************************************
           // Write your recursive function here
	//**Prelude code*/
	push {lr}
	push {fp}
	mov fp, sp
	sub sp, sp, #8	// allocating space for ints s and t.

	//** Tests (i<= 0 || j <=0) if true returns -1 */ 
	ldr r0, [fp,#8]	//tests if i is less than or equal to 0
	cmp r0, #0
	ble if		
	
	
	ldr r0, [fp, #12]	//tests if j is less than of equal to 0
	cmp r0, #0
	ble if
	b else1
	
if:
	mov r0, #-1 
	mov sp, fp
	pop {fp}
	pop {pc}
	
	//**Tests whether i + j < k */
	
	
else1:	
	ldr r0, [fp, #8]	//load i in to r0
	ldr r1,	[fp, #12]	//load j in to r1
	ldr r2, [fp, #16]	//load k in to r2
	add r3, r0, r1		//add i and j and put in to r3
	cmp r3, r2		//see is i + j is less than k
	bge else2		//if so go to else2
	
	//in this case i + j is less than k 
	ldr r0, [fp, #8]	//load i in to r0
	ldr r1,	[fp, #12]	//load j in to r1
	add r2, r0, r1		//add i and j and put in r2
	mov r0, r2 		//store value in r2 in r0 
	mov sp, fp		//return i+ j
	pop {fp}
	pop {pc}

	//**Else portion 
	
	//** body for else * /
	
else2:
	// set s = 0.
	mov r0, #0
	str r0, [fp, #-4]
	
	//for loop 
	//(t=1; t < k; t++) the recursion is here
	//get t in to r0
	mov r0, #1
	str r0, [fp, #-8]
while:	
	//t = 1
	ldr r0, [fp, #-8]
	//get k in to r1
	ldr r1, [fp, #16]
	//checks t < k
	cmp r0, r1
	bge whileEnd
	
	//update s = s + F(i-t, j-t, k-1) +1

	
	
	ldr r1, [fp, #8] // load i
	ldr r2, [fp, #12] // load j
	ldr r3, [fp, #16] //load k
	ldr r4, [fp, #-8] // load t
	sub r3, r3, #1 // k-1
	//ldr r3, [fp, #16]
	push {r3} // push k
	sub r2, r2, r4 // j-t
	//ldr r2, [fp, #12]
	push {r2} // push j 
	sub r1, r1, r4 //i-t
	//ldr r1, [fp, #8]
	push {r1}// push i

	bl F
	add sp, sp, #12
	push {r0}
	
	ldr r1, [fp, #-4]
	
	pop {r0}
	add r1, r0, r1
	add r1, r1, #1
	str r1, [fp, #-4]
	
	
	ldr r0, [fp, #-8]
	add r0, r0, #1
	str r0, [fp, #-8]
	
	bl while
	
 
whileEnd:
	
	//return s;
	

	ldr r0, [fp, #-4] 
	//**Post code*/
	mov sp, fp
	pop {fp}
	pop {pc}
	
//====================================================================
// Don't add anything below this line...
// ====================================================================
        .end





