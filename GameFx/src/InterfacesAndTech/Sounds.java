package InterfacesAndTech;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sounds {
	@SuppressWarnings("unused")
	private InputStream in;

	@SuppressWarnings("unused")
	private AudioInputStream audioIn;

	private Clip clip;

	private String sound;

	private float Volume;
	private boolean isPlaying = true;
	private boolean isABackground;

	public Sounds(String sound, boolean isABackground, float lessVolume) {
		this.sound = sound;
		this.Volume = lessVolume;
		this.isABackground = isABackground;

		try {
			InputStream in = getClass().getResourceAsStream("/sounds/" + this.sound + ".wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(in));
			this.clip = AudioSystem.getClip();
			clip.open(audioIn);

			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-Volume); // Reduce volume by 10 decibels.

		} catch (Exception e) {
			// Do nothing
		}
	}

	public void play() {
		try {
			if (isABackground) {
				clip.loop(Clip.LOOP_CONTINUOUSLY); // endless loop for
													// background music
			} else {
				clip.start(); // play the audio once
				clip.setMicrosecondPosition(0); // set clip to start
			}
		} catch (Exception e) {
			System.out.println("something went wrong in play");
		}

	}

	public void stopBackgroundSound() {
		try {
			if (isABackground) {
				clip.stop();
			}
		} catch (Exception e) {
			System.out.println("something went wrong with stopping the background sound");
		}

	}

	public void onOrOffSound() {
		if (isPlaying) {
			if (clip != null && clip.isRunning()) {
				clip.stop();
			}

			isPlaying = false;
		} else {
			clip.start();
			isPlaying = true;
		}
	}
}
