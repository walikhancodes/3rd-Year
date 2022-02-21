

	.global main, Stop, CodeEnd
	.global DataStart, DataEnd
	.global Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10
	.global ans_b, ans_s, ans_i, x, y, z

//******************************************************************
// DO NOT make any changes to the next 8 lines below
//******************************************************************
main:

//******************************************************************
// DO NOT make any changes to the line ABOVE
//
// Write the assembler instruction to each question after the 
// CORRESPONDING LABEL:
//                       Q1, Q2, Q3, ..... Q10
//
// I have defined the necessary variable at the v1, v2,.. labels 
// which are located at the end of the file (if you want to see them)
//******************************************************************

//******************************************************************
// Put your assembler instructions AFTER this line
//******************************************************************

//*************************************************************************
// Write the assembler instruction at label Q1 that executes this
// high level programming language statement: 
//
//      ans_b = 44;
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q1:
	mov r0, #44
	movw r1, #:lower16:ans_b
	movt r1, #:upper16:ans_b
	strb r0, [r1] 
	
	




//*************************************************************************
// Write the assembler instruction at label Q2 that executes this
// high level programming language statement:
//
//      ans_s = -99;
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q2:

	mov r0, #-99
	movw r1, #:lower16:ans_s
	movt r1, #:upper16:ans_s
	strh r0, [r1]






//*************************************************************************
// Write the assembler instruction at label Q3 that executes this
// high level programming language statement:
//
//      ans_i = 444;
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q3:

	mov r0, #444
	movw r1, #:lower16:ans_i
	movt r1, #:upper16:ans_i
	str r0, [r1]




//*************************************************************************
// Write the assembler instruction at label Q4 that executes this
// high level programming language statement:
//
//      ans_b = x;
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q4:


	movw r0, #:lower16:ans_b
	movt r0, #:upper16:ans_b
	movw r1, #:lower16:x
	movt r1, #:upper16:x
	ldrsb r2, [r1]
	strb r2, [r0]




//*************************************************************************
// Write the assembler instruction at label Q5 that executes this
// high level programming language statement:
//
//      ans_s = x;
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q5:


	movw r0, #:lower16:ans_s
	movt r0, #:upper16:ans_s
	movw r1, #:lower16:x
	movt r1, #:upper16:x
	ldrsb r2, [r1]
	strh r2, [r0]




//*************************************************************************
// Write the assembler instruction at label Q6 that executes this
// high level programming language statement:
//
//      ans_i = x;
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q6:

	movw r0, #:lower16:ans_i
	movt r0, #:upper16:ans_i
	movw r1, #:lower16:x
	movt r1, #:upper16:x
	ldrsb r2, [r1]
	str r2, [r0]






//*************************************************************************
// Write the assembler instruction at label Q7 that executes this
// high level programming language statement:
//
//      ans_b = y;  (can you explain the difference in the stored value ?)
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q7:

	movw r0, #:lower16:ans_b
	movt r0, #:upper16:ans_b
	movw r1, #:lower16:y
	movt r1, #:upper16:y
	ldrsh r2, [r1]
	strb r2, [r0]


//because y consists of 2 bytes and ans_b can only store one therefore only one byte from y is stored in ans_b



//*************************************************************************
// Write the assembler instruction at label Q8 that executes this
// high level programming language statement:
//
//      ans_b = z;  (can you explain the difference in the stored value ?)
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q8:

	movw r0, #:lower16:ans_b
	movt r0, #:upper16:ans_b
	movw r1, #:lower16:z
	movt r1, #:upper16:z
	ldr r2, [r1]
	strb r2, [r0]

//since z is four bytes and ans_b can only contain one byte, ans_b only stores one byte from z



//*************************************************************************
// Write the assembler instruction at label Q9 that executes this
// high level programming language statement:
//
//      ans_s = z;  (can you explain the difference in the stored value ?)
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q9:


	movw r0, #:lower16:ans_s
	movt r0, #:upper16:ans_s
	movw r1, #:lower16:z
	movt r1, #:upper16:z
	ldr r2, [r1]
	strh r2, [r0] 




// only 2 bytes can be stored in ans_S but z has 4 bytes 

//*************************************************************************
// Write the assembler instruction at label Q10 that executes this
// high level programming language statement:
//
//      ans_i = y;
//
// Do NOT forget to SKIP some space (or tab) before writing assembler code !!!
//*************************************************************************
Q10:



	movw r0, #:lower16:ans_i
	movt r0, #:upper16:ans_i
	movw r1, #:lower16:y
	movt r1, #:upper16:y
	ldrh r2, [r1]
	str r2, [r0]




//*************************************************************************
// DO NOT change anything below this line
//*************************************************************************
Stop:	
CodeEnd:
	nop







	.data
DataStart:

	.align  
ans_b:  .skip 1

	.align  
ans_s:  .skip 2

	.align  
ans_i:  .skip 4


	.align
x:	.byte  -13


	.align
y:	.2byte  2057		// 2^11 + 9

	.align
z:	.4byte  147463		// 2^17 + 2^14 + 7


DataEnd:
	.end

