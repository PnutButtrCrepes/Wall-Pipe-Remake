package com.crepes.butter.peanut;

import javax.sound.sampled.*;

public class SoundGenerator {
	public static final float SAMPLE_RATE = 44000f;
	public static final int SAMPLE_SIZE = 8;

	private static AudioFormat af;
	private static SourceDataLine sdl;

	public static void initSoundSystem() {
		try {
			af = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, 1, true, false);
			sdl = AudioSystem.getSourceDataLine(af);

			sdl.open(af);
			sdl.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public static byte[] constructSine(int frequency, int milliseconds, double volume) {
		int numberOfSamples = milliseconds * (int) (SAMPLE_RATE / 1000);
		float samplesPerPeriod = SAMPLE_RATE / frequency;
		int maxValue = (int) (Math.pow(2, SAMPLE_SIZE - 1) - 1);

		byte[] buffer = new byte[numberOfSamples];
		for (int i = 0; i < numberOfSamples; i++) {
			double angle = i / samplesPerPeriod * 2.0 * Math.PI;
			buffer[i] = (byte) (Math.sin(angle) * maxValue * volume);
		}

		return buffer;
	}

	public static byte[] constructPulse(int frequency, float dutyCycle, int milliseconds, double volume) {
		int numberOfSamples = milliseconds * (int) (SAMPLE_RATE / 1000);
		float samplesPerPeriod = SAMPLE_RATE / frequency;
		int maxValue = (int) (Math.pow(2, SAMPLE_SIZE - 1) - 1);

		byte[] buffer = new byte[numberOfSamples];
		for (int i = 0; i < numberOfSamples; i++) {
			if (((i % samplesPerPeriod) / samplesPerPeriod) <= dutyCycle)
				buffer[i] = (byte) (maxValue * volume);
			else
				buffer[i] = 0;
		}

		return buffer;
	}

	public static byte[] constructNoise(int noiseResolution, int milliseconds, double volume) {
		int numberOfSamples = milliseconds * (int) (SAMPLE_RATE / 1000);
		int maxValue = (int) (Math.pow(2, SAMPLE_SIZE - 1) - 1);

		byte[] buffer = new byte[numberOfSamples];
		for (int i = 0; i < numberOfSamples; i++) {
			if (i % noiseResolution != 0) {
				buffer[i] = buffer[i - 1];
				continue;
			}

			double random = Math.random();

			if (random < 0.5)
				buffer[i] = (byte) (maxValue * volume);
			else
				buffer[i] = 0;
		}

		return buffer;
	}

	public static void playWave(byte[] buffer) {
		sdl.write(buffer, 0, buffer.length);
	}

	public static void destroySoundSystem() {
		sdl.stop();
		sdl.close();
	}

	/*
	 * public static void main(String[] args) throws Exception { initSoundSystem();
	 * //playWave(constructPulse(200, 0.5f, 600, 0.25)); // this is the Wall Pipe
	 * placement sound //playWave(constructNoise(80, 2000, 0.1)); // roughly water
	 * start sound }
	 */
}