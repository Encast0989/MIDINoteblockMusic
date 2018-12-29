package me.encast.midinoteblockmusic.song;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.sound.midi.Sequencer;
import java.util.concurrent.ExecutorService;

/**
 * Data class that stores the {@link Sequencer} which handles the MIDI
 * playback, and the {@link ExecutorService} which manages the closing,
 * and removal of the {@link Sequencer}.
 */
@AllArgsConstructor
@Getter
public class SequencerData {

    private Sequencer sequencer;
    private ExecutorService executorService;
}
