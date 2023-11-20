# FilmQueryProject

## Description
The Film Query Project is a project designed to test someone's understanding of merging Java and SQL. In this project we were given a collection of tables that described a video rental chain (think Blockbuster). This collection of tables had information ranging from customers and staff to films, actors and rental information. We were tasked with designing a program to collect information about movies and show that information to the user using Java to implement SQL's ability to read tables.

The program starts off by displaying a menu giving the user three options: to find a film using it's ID, to find films using keywords,
or to exit the program. 

When the first option is selected, the user is prompted for an input of the film's ID, if a valid option is chosen, the user is shown the film's title, the year it was made, it's rental rate, the language of the film, it's description, and a list of all of the actors in the film. If no film exists with the ID given, the user is shown that, and if an invalid input is given an error message pops up.

When the second option is chosen the user is then prompted for a keyword, if the keyword is contained in either the title or description of a film, that film and it's information is displayed, a table is shown with all of the information for all tables that have that keyword. If no film has that key word the user is told. 

If the user doesn't give an acceptible response in the main menu, an error is displayed. When the user selects the third option, an exit message is shown and the program shuts down.

## Technology used
Java, OOP, Eclipse, SQL, MySQL, Terminal, Maven. 

## Lessons Learned
One area of creating this program that posed a challenge to me was in creating the method findFilmByKeywords, where I kept getting the same film printing multiple times. I spent a while tying to troubleshoot what was going wrong, I had edited the prepared statement multiple times (after checking in the terminal that the problem was with the SQL code), and tried a variety of inputs. I had even tried using DISTINCT, but still had the problem. I eventually figured out the problem was that I was using the wild-card '*' without specifying that I only wanted all of the information from film (and not film_actor). This caused a problem because the program would display all of the information, including the actor ID's, making the program print out a new row for every actor in the film. These rows were distinct because they had different actor ID's, but this information was hidden in eclipse, because of how my java code displays, and in SQL because of the amount of information that was printing out. It wasn't until reducing the information to a small same that I found the issue. 

From this I learned why it is important to keep your sample size small when testing SQL Code, as it can help to find mistakes in the data that is being shown. I also learned how specific you need to be when using SQL code, especially when joining two or more tables together. Even if the data you are shown is correct in one instance, it may be incorrect in others. 