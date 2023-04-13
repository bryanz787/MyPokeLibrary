# My PokéLibrary

### Description of Project

The project I intend to design this term is a *Pokémon* collection organizer  
and deck builder, it will allow users, whether it be collectors, trading card  
game players, or casual fans, to document their *Pokémon* cards digitally so  
that they won't need to go through their entire card collection by hand. It will  
also allow users to sort and categorize their cards by things like rarity, type,  
and evolution stage. The project will also have a deck builder feature where  
users can also build their own custom decks with cards they have in their  
collection. The deck builder feature will also make sure that the decks they  
build, are within the official *Pokémon Trading Card Game* rules and  
regulations. 

This project is of relevance to me as I consider myself a casual *Pokémon*  
card collector and I often find myself going through my collection by hand  
to find certain cards, or document the quantity of certain types I own; having  
an application that helps me keep track of my cards allows me to both save  
time when sorting my cards, and helps keep my cards in good condition as  
I rummage through them less.

### User Stories

- As a user, I want to be able to add cards to my collection.
- As a user, I want to be able to know the quantity of holofoil cards in my  
collection.
- As a user, I want to be able to create decks with the cards in my collection
- As a user, I want to be able to check if my deck satisfies the *Pokémon  
Trading Card Game* official deck rules.
- As a user, I want to be able to save my collection to a file before I exit
- As a user, I want to be able to load my collection from a file if I so choose

### Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by
pressing either the "Add Pokemon", "Add Trainer", or "Add Energy" buttons 
on the left side 
of the GUI.
- You can generate the second required action related to adding Xs to a Y by pressing
the "Remove A Deck" button
- You can locate my visual component by pressing the "Submit" or "Remove" button on 
the right hand side of the screen given that there's proper inputs for the other
fields on the GUI
- You can save the state of my application by clicking the "Save Progress" button
- You can reload the state of my application by clicking the "Load Progress" button

### Phase 4: Task 2

Wed Apr 12 21:53:03 PDT 2023
Loaded CollectionApp.

Wed Apr 12 21:53:03 PDT 2023
charmandar has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
charmeleon has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
charizard has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
fire Energy has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
fire Energy has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
fire Energy has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
fire Energy has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
fire Energy has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
pokeball has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
pokeball has been added to the collection

Wed Apr 12 21:53:03 PDT 2023
Squirtle has been added to the collection

Wed Apr 12 21:53:24 PDT 2023
caterpie has been added to the collection

Wed Apr 12 21:53:27 PDT 2023
Displayed cards in collection.

Wed Apr 12 21:53:42 PDT 2023
Saved CollectionApp.

### Phase 4: Task 3
The main change I would make to my program given more time is that I 
would try to decrease the coupling in my program, especially in the 
CollectionApp class in the ui package, as it stands right now I have
a list of decks in a given collectionApp instance, instead I would like
to change it and implement a seperate class in model to represent the 
relationship between a list of decks and the Deck class as was done 
for Collection and cards. Not only would that bring in some more consistency with
my class structure, it would also help cut down the amount of
code within the collectionApp class and move it elsewhere, improving readability.

As it stands right now, the collectionApp class is too bloated with a
many methods definitions and additional nested / anonymous classes, the main
thing I would refactor in the future is to break the class down into smaller classes
to decrease coupling and increase cohesion.



