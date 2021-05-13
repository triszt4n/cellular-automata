# WireWorld

## Development

Best to be imported into IntelliJ IDEA.

## Input language

You might want to load already defined maps into the program instead of clicking through the UI's field.

The language has 3 character types:
* DOT - `.`: conductor
* ASTERISK - `*`: electron head
* DASH - `-`: electron tail (not necessary)
* SPACE - ` `: blank

You'll need to specify in the first line the number of rows and columns you'll want to use in the field.
Also, properly fill in the field with every character needed (spaces).

See configurations/example1.txt:

```
5 11
-*.........
.   .      
   ...     
.   .      
*-.. ......
```
