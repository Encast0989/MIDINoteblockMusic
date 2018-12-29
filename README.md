# MIDINoteblockMusic
Converts MIDI songs into Minecraft noteblock songs.

**NOTE**: Reloading the server while a song is playing will cause issues! Due to how the MIDI files are played
and converted, reloading the server will continue the playback of the song (which may be a good thing), but it
cannot it be stopped.

**NOTE**: Due to Minecraft's octave constraint, notes in octaves that aren't the first or second octave get transposed
down so that the song can play properly. The downside to this is that songs may sound repetitive in areas, or might
not be recognizable at all.

## Usage
1. Compile the code (or grab latest JAR in releases), and add it to your server plugins folder.
2. Either create a new folder within the plugins folder called "MIDINoteblockMusic" or start the server and use
the command /song <name> - the name can be anything, the folder will be generated for you!
3. Add .mid files into the MIDINoteblockMusic folder - it's recommended that you name the file something friendly.
4. Use /song <name> to start playing the song - do not add .mid to the end of the name!
5. To stop the song, use /stopsong
  
