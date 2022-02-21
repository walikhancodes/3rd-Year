
        .global BubbleSort

	
	.global While1, While1end, For, ForEnd, If, IfEnd, While2, While2end


BubbleSort:

//*****************************************************
// Write your bubble sort assembler subroutine here
//
//    BubbleSort: input  r0 = address of int array
//                       r1 = # elements in the array
//*****************************************************



        // Write your bubble sort function here.....
	// Make sure you RETURN to the caller !!!
	
	
	mov	r2, #0
	mov	r3, #1
While1:
	cmp	r2, #0
	bne	While1End
	mov	r2, #1

	mov	r4, #0
	
While2:
	
	add	r4,r4,r4
	
	add	r4,r4,r4
	mov	r9, #1
	add 	r9, r9, #1
	
	
	

	sub	r1,r1,r3
	//ldr	r1, [r1]
	cmp	r9, r1
	bge	While2End
	
	
If:
	ldr	r6, [r0,r4]
	add	r4, r4, #1
	add	r5, r4, r4
	add	r5, r5, r5
	//ldr	r5, [r5]
	ldr	r7, [r0,r5]
	cmp	r6, r7
	ble	IfEnd
	mov	r8, r6
	ldr	r6, [r8]
	
	mov	r6, r7
	ldr	r7, [r6]
	mov	r7, r8
	ldr	r8, [r7]
	str	r7, [r0,r5]
	str	r6, [r0,r4]
	mov	r2, #0
	b	While1
IfEnd:

While2End:
	add	r3, r3, #1
	//ldr	r3, [r3]
	
While1End:
	
	
	


	mov	pc, lr


	.data
// *************************************************************************
// If you need local variables, you can define them below this line
// *************************************************************************




        .end