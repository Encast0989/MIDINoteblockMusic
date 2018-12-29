package me.encast.midinoteblockmusic.song;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Implementation of the {@link Receiver} class provided in the
 * {@link javax.sound.midi} package. This implementation is used to read all
 * incoming {@link MidiMessage} and translate it to noteblock sounds.
 * All sounds are played using {@link Instrument#PIANO}.
 *
 * Due to Minecraft's octave constraints, octaves of the MIDI file are converted
 * to Minecraft's octaves as follows:
 * MIDI octave => 0 (positive) -> 1
 * MIDI octave < 0 (negative) -> 0
 *
 * Note names, and their states (natural, sharp, flat), are kept as is. Due to this
 * being the case, octave translations often cause the noteblock version of the
 * song to sound repetitive in areas.
 */
public class MCReceiver implements Receiver {

    private Player player;

    private final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public MCReceiver(Player player) {
        this.player = player;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if(message instanceof ShortMessage) {
            ShortMessage m = (ShortMessage) message;
            if(m.getCommand() == ShortMessage.NOTE_ON) {
                int key = m.getData1();
                int octave = (key % 12) - 1;
                int note = Math.abs(octave % 12);

                String name = NOTE_NAMES[note];
                octave = octave < 0 ? 0 : 1;
                boolean sharp = name.contains("#");
                Note.Tone tone = Note.Tone.valueOf(name.replaceAll("#", ""));

                if(player != null)
                    player.playNote(player.getLocation(), Instrument.PIANO,
                            sharp ? Note.sharp(octave, tone) : Note.natural(octave, tone));
            }
        }
    }

    @Override
    public void close() {
    }
}
