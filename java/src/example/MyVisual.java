package example;

import ie.tudublin.*;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;

    Minim m;
    AudioInput ai;
    AudioPlayer ap;
    AudioBuffer b;

    int mode = 0;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;
    
    public void settings() {
        size(1024, 500);

        // Use this to make fullscreen
        // fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        // fullScreen(P3D, SPAN);
    }

    public void setup() {
        startMinim();

        // Call loadAudio to load an audio file to process
        // loadAudio("heroplanet.mp3");
        m = new Minim(this);
        ap = m.loadFile("java\\data\\Post Malone, Swae Lee - Sunflower (Spider-Man_ Into the Spider-Verse) (256 kbps).mp3");
        ap.play();
        b = ap.mix;
        colorMode(HSB);

        // Call this instead to read audio from the microphone
        startListening();

        wf = new WaveForm(this);
        abv = new AudioBandsVisual(this);
    }

    public void keyPressed() {
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }

    float off = 0;
    
    float lerpedAvg = 0;
    float lerped = 0;

    public void draw() {
        background(0);
        float halfH = height / 2;
        float average = 0;
        float sum = 0;
        off += 1;
        // Calculate sum and average of the samples
        // Also lerp each element of buffer;
        for(int i = 0 ; i < b.size() ; i ++)
        {
            sum += abs(b.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], b.get(i), 0.05f);
        }
        average = sum / (float) b.size();

        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);
        
        float cx = width / 2;
        float cy = height / 2;

        float tot = 0;
        for(int i = 0 ; i < b.size() ; i ++)
        {
            tot += abs(b.get(i));
        }

        float avg = tot / b.size();

        lerpedAvg = lerp(lerpedAvg, avg, 0.1f);
        lerped = lerp(lerped, y, 0.1f);

        switch (mode) {
			case 0: //grainny
                background(0);
            break;

            case 1: //loredana
            break;

            case 2: //ella
            break;
        }

        try {
            // Call this if you want to use FFT data
            calculateFFT();
        } catch (VisualException e) {
            e.printStackTrace();
        }
        // Call this is you want to use frequency bands
        calculateFrequencyBands();

        // Call this is you want to get the average amplitude
        calculateAverageAmplitude();
        wf.render();
        abv.render();


    }
}
