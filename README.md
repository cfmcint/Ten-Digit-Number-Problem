# Ten-Digit-Number-Problem
A Java algorithm and a UI created with the JavaFX graphics library which both verifies a user imputed ten-digit number and finds a unique ten-digit number where each sub number is divisible by its length (ex. 38165/5 = 7633). 
The algorithm to create a valid ten-digit-number uses recursion and backtracking to start at number nine and cycle through, add, and remove digits until a valid number is created.
The user interface displays the calculated ten-digit-numbers and the user input field. The calculated ten-digit-number section of the UI also displays the proof that the number/s found buy the computer is/are valid. If the user inputed ten-digit-number is not a vaild ten-digit-number it will be displayed where the sub-number was not divisible by its length.
