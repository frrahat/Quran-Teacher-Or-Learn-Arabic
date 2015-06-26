package QuranTeacher.RenderAudio;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

public class ZPlayer implements Runnable {

	private Thread t;

	private boolean isPause;

	private boolean ret;
	private boolean isRunning;

	private Bitstream bitstream;

	private Decoder decoder;

	private AudioDevice audio;

	private boolean closed = false;

	private boolean complete = false;

	private int lastPosition = 0;

	public ZPlayer(InputStream stream) throws JavaLayerException {
		this(stream, null);
	}

	public ZPlayer(InputStream stream, AudioDevice device)
			throws JavaLayerException {
		bitstream = new Bitstream(stream);
		decoder = new Decoder();
		isPause = false;
		isRunning = true;

		if (device != null) {
			audio = device;
		} else {
			FactoryRegistry r = FactoryRegistry.systemRegistry();
			audio = r.createAudioDevice();
		}
		audio.open(decoder);
		t = new Thread(this, "one");
		t.start();
	}

	@Override
	public void run() {
		ret = true;
		int frames = Integer.MAX_VALUE;

		while (frames-- > 0 && ret) {
			synchronized (this) {
				if (isPause) {
					try {
						wait();
					} catch (InterruptedException ex) {
						Logger.getLogger(ZPlayer.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
				if (isRunning == false) {
					ret = false;
					continue;
				}
			}
			try {
				ret = decodeFrame();
			} catch (JavaLayerException ex) {
				Logger.getLogger(ZPlayer.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}

		if (!ret) {
			// last frame, ensure all data flushed to the audio device.
			AudioDevice out = audio;
			if (out != null) {
				out.flush();
				synchronized (this) {
					complete = (!closed);
					close();
				}
			}
		}

	}

	public synchronized void close() {
		AudioDevice out = audio;
		if (out != null) {
			closed = true;
			audio = null;
			// this may fail, so ensure object state is set up before
			// calling this method.
			out.close();
			lastPosition = out.getPosition();
			try {
				bitstream.close();
			} catch (BitstreamException ex) {
			}
		}
	}

	public synchronized boolean isComplete() {
		return complete;
	}

	public int getPosition() {
		int position = lastPosition;

		AudioDevice out = audio;
		if (out != null) {
			position = out.getPosition();
		}
		return position;
	}

	protected boolean decodeFrame() throws JavaLayerException {
		try {
			AudioDevice out = audio;
			if (out == null)
				return false;

			Header h = bitstream.readFrame();

			if (h == null)
				return false;

			// sample buffer set when decoder constructed
			SampleBuffer output = (SampleBuffer) decoder.decodeFrame(h,
					bitstream);

			synchronized (this) {
				out = audio;
				if (out != null) {
					out.write(output.getBuffer(), 0, output.getBufferLength());
				}
			}

			bitstream.closeFrame();
		} catch (RuntimeException ex) {
			throw new JavaLayerException("Exception decoding audio frame", ex);
		}

		return true;
	}

	synchronized void pause() {
		isPause = true;
	}

	synchronized void resume() {
		isPause = false;
		notify();
	}

	synchronized void stop() {
		isRunning = false;
	}
}
