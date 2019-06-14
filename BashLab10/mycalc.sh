#!/bin/bash
#Van Nam Doan
#040943291
#CST8102
#310
#This script performs the following operations: '+' and '-' (addition and subtraction)
add () {
	echo "The sum of "$1" plus "$2" equals" $(($1+$2))
}
subtract () {
	echo "The sum of "$1" minus "$2" equals" $(($1-$2))
}
if [ $# -ne 0 ] && [ $# -ne 3 ]
then
	echo "illegal number of parameters"
else
	if [ $# -eq 3 ]
	then
		if [ $2 = "+" ]
		then
			add $1 $3
		elif [ $2 = "-" ]
		then
			subtract $1 $3
		fi
	else
		while [ "$choiceMenu1" != "x" ]&&[ "$choiceMenu1" != "X" ]
		do
			echo "Menu 1"
			echo "C) Calculation"
			echo "X) Exit"
			read choiceMenu1
			if [ "$choiceMenu1" = "c" ]||[ "$choiceMenu1" = "C" ]
			then
				echo "Menu 2" 
				read -p "Please enter an integer number or X to exit: " number
				if [ "$number" != "x" ]&&[ "$number" != "X" ]
				then
					clear
					echo "Menu 3"
					echo "+) Add"
					echo "-) Subtract"
					echo "X) Exit"
					read choiceMenu3
					if [ "$choiceMenu3" = "+" ]||[ "$choiceMenu3" = "-" ]
					then
						clear
						read -p "Enter second number: " number2
						if [ "$choiceMenu3" = "+" ]
						then
							add $number $number2
						else
							subtract $number $number2
						fi
						sleep 3
						clear
					fi
				fi
			elif [ "$choiceMenu1" != "x" ]&&[ "$choiceMenu1" != "X" ]
			then
				echo "Invalid input"
			fi
		done
	fi
fi