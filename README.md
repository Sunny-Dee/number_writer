# Number Writer
---
## Description
This application opens a socket and restricts input to at most 5 concurrent clients. 
Clients connecting to the application will be prompted to enter 1-9 digit number. 

This program is harsh. If the client does not enter a numeric value up to 9 digits, 
the program will exit without comments. 

The program will create a log file called `log.txt` in the project's root directory.
It will only log unique values. 

## How to run this program
To run the binaries navigate to:

`out/production/NumberWriter`

and run the server:

    `java com.deliana.codingchallenge.Server`

in another window run the client:

    `java com.deliana.codingchallenge.Client` 

