
.global InsertList 
.global else, else1
InsertList:

// ********************************************************************
// Put your InsertList function here 
// ********************************************************************
	//** Prelude code */ 
	push	{lr}
	push	{fp}
	mov	fp, sp
	sub	sp,sp, #0

	ldr r0, [fp, #12]	// loads e 
	ldr r1, [fp, #8]	//loads h
	
	//check if h is null
	mov r2, #0
	cmp r1, r2
	bne else	// h is not null 

	//e.next = null
	ldr r0, [fp, #12]
	mov r1, #0
	str r1, [r0, #4]

	//return e 	//since h is null
	ldr r0, [fp, #12] 
	mov sp, fp
        pop {fp}
        pop {pc}

	
else:
	// this is where the program will go if h is not null 
	
	ldr r0, [fp, #12]	// loads e 
	ldr r1, [fp, #8]	//loads h
	
	//check if e.value < h.value
	ldr r2, [r0, #0]	// e.value
	ldr r3, [r1, #0]	//h.value
	cmp r2, r3		
	bge else1
	
	//e.next = h
	str r1, [r0, #4]
	//h = e
	str r0, [fp, #8]
	
	//return h
	ldr r0, [fp,#8]
	mov sp, fp
	pop {fp}
	pop {pc}
	
	
else1:
	//this is where the program will go if h is not null and if e is greater than h
	ldr r0, [fp, #12] 	//load e
	push {r0} 		//first parameter pushed on to stack

	ldr r0, [fp,#8]		//load h
	ldr r0, [r0, #4] 	//load h.next
	push {r0}		//push 2nd parameter on to stack

	bl InsertList		//recursive call
	
	add sp, sp, #8		//clean up parameters on stack

	ldr r1, [fp, #8]	//load h
	str r0, [r1, #4]	//store return value from recursive call above in to h.next
	
	ldr r0,[fp,#8]		//return h
	mov sp, fp
	pop {fp}
	pop {pc}
	
	


	.end
	


