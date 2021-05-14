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

## Gameplay

* 4 buttons:
    * Play: plays the generation
    * Pause: pauses the generation 
    * Stop: Clears electron heads from the field
* Changes since specification: you can interact with the field even while it's generating.
* First you must load or create a new field
* You can create electron heads with one right click, electron tails with one more right click
* You can create conductors with left clicks

## Extras

This project is - not so easily but - extensible with new rule sets. Right now only the WireWorld and Conway's Game of Life implementations are included,
the WireWorld view is used in the MyApp. If you want to try out Conway's Game of life as well, change the MyApp's view to GameOfLifeView.

Also, there are 6 example files already created for the wireworld rule set (configurations/ww_example_*.txt), and 2 other ones for the game of life (configurations/gol_example_*.txt).

_Piller Trisztán 2021. 05. 14._
