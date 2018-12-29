package me.encast.midinoteblockmusic.song;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;
import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manages playback and stopping of songs to players.
 */
public class SongManager {

    /**
     * Stores {@link SequencerData} keyed by a {@link Player}'s
     * {@link UUID}.
     */
    private Map<UUID, SequencerData> sequencers = Maps.newConcurrentMap();

    public SongManager() {
    }

    /**
     * Plays the noteblock version of the provided MIDI file to the
     * {@link Player}.
     *
     * @param player The player to play the song to.
     * @param file The MIDI file to convert and play.
     */
    public void play(Player player, File file) {
        if(file.exists()) {
            try {
                Sequencer s = MidiSystem.getSequencer(false);
                s.setSequence(MidiSystem.getSequence(file));
                Transmitter transmitter = s.getTransmitter();
                transmitter.setReceiver(new MCReceiver(player));
                s.open();
                s.start();

                ExecutorService service = Executors.newSingleThreadExecutor();
                service.submit(() -> {
                    while(true) {
                        if(!s.isRunning()) {
                            s.close();
                            sequencers.remove(player.getUniqueId());
                            service.shutdownNow();
                        }
                        Thread.sleep(5000);
                    }
                });

                sequencers.put(player.getUniqueId(), new SequencerData(s, service));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Attempts to stop the playback of the song to the {@link Player}.
     * Due to the song playback being managed within the runtime, reloading
     * the server will not stop song playback, and this method will not be
     * able to stop the {@link Player} from hearing the noteblock song.
     *
     * @param player The player to stop the song for.
     * @return TRUE if the song was stopped successfully, FALSE otherwise.
     */
    public boolean stop(Player player) {
        try {
            SequencerData data = sequencers.getOrDefault(player.getUniqueId(), null);
            if(data != null) {
                if(data.getSequencer() != null) {
                    data.getSequencer().stop();
                    data.getSequencer().close();
                }

                if(data.getExecutorService() != null) {
                    data.getExecutorService().shutdownNow();
                }
                sequencers.remove(player.getUniqueId());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
