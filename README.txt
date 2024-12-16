MRS WONG'S BASICS

The goal of the game is to collect all 7 notebooks and escape the school (and Mrs. Wong's wrath).

Tips:
- Mrs Wong will move towards you when you enter and exit doors.
- You cannot outrun her without sprinting, so conserve your stamina!
- Save your items!

Who did what:
Lucas
- Mrs Wong class and pathfinding
- Player class (checkroom methods and movement)
- High scores 
- Notebook questions
- Popups
- Bit of room mapping

Jacky
- Map rendering from text file
- Most of mapping
- Most of graphics/UI (item ui)
- Overall Program Structure (tile system & player class structure)
- Items (notebooks and chocolate)
- sprinting
- sprites
- Musica
- EndGame logic

Known Bugs: 
- To sprint, you have to momentarily stop moving. 
- To use your item, you also have to stop moving and hold down J for a sec.
- These are both caused by keyEvents being annoying.
- In addition, keyevents also messes up the screen transition that relies on detecting whether a key is pressed
- This could potentially mess up the game reset
- Rule of thumb, don't click off the game tab