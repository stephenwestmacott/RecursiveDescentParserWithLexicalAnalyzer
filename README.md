*********************************************************EBNF rules*******************************************************************

<program> -> program begin <statement_list> end
<statement_list> -> <statement> {;<statement>}
<statement> -> <assignment_statement> | <if_statement> | <loop_statement>
<assignment_statement> -> <variable> = <expression>
<variable> -> identifier (An identifier is a string that begins with a letter followed by 0 or more letters and/or digits)
<expression> -> <term> { (+|-) <term>}
<term> -> <factor> {(* | /) <factor> }
<factor> -> identifier | int_constant | (<expr>)
<if_statement> -> if (<logic_expression>) then <statement>
<logic_expression> -> <variable> (< | >) <variable> (Assume that logic expressions have only less than or greater than operators)
<loop_statement> -> loop (<logic_expression>) <statement>

********************************************Test Program without syntax errors********************************************************

program
begin

sum1 = var1 + var2;
sum2 = var3 + var2 * 90; 
sum3 = (var2 + var1) * var3;

if (sum1 < sum2) then
     if ( var1 > var2) then
	 var4 = sum2 - sum1;

loop (var1 < var2)
      var5 = var4/45

end

***********************************Test Program with error (The keyword begin is missing):********************************************

program

var3 = 140;

if (sum1 < sum2) then
     var4 = sum2 - sum1

end

***************************Test Program with error (The last statement should not end with a semicolon)******************************

program
begin

var3 = var1 + var2;

if (sum1 < sum2) then
         var4 = sum2 - sum1;

end

************************Test Program with error (The semicolon at the end of the first statement is missing)*************************

program
begin 

var2 = var1 

if (var1 < var2) then
     if ( var3 > var4) then
	       var2 = var2 - var1

end

*************************************Test Program with error (The keyword then is missing)*******************************************

program
begin 

loop ( var1 < var2)
     if ( var3 > var4)
	       var2 = var2 - var1

end

************************************Test Program with error (The keyword if is misspelled)*******************************************

program
begin 

total = var1 + var2; 

fi (var1 < var2) then
     if ( var3 > var4) then
	       var2 = var2 - var1

end

*************************************************************************************************************************************
