package example;

import ie.tudublin.*;


public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;
    int mode = 0;
    float angle = 0;

    public void settings() {
        size(1024, 500);

        // Use this to make fullscreen
        //fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        //fullScreen(P3D, SPAN);
    }

    public void setup() {
        startMinim();

        // Call loadAudio to load an audio file to process
        loadAudio("data\\Post Malone, Swae Lee - Sunflower (Spider-Man_ Into the Spider-Verse) (256 kbps).mp3");

        // Call this instead to read audio from the microphone
        // startListening();

        wf = new WaveForm(this);
        abv = new AudioBandsVisual(this);
    }

    public void keyPressed() {
        if (key >= '0' && key <= '2') {
			mode = key - '0';
		}
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }

    public void draw() {
        background(0);
        // try {
        //     // Call this if you want to use FFT data
        //     calculateFFT();
        // } catch (VisualException e) {
        //     e.printStackTrace();
        // }
        // Call this is you want to use frequency bands
        calculateFrequencyBands();

        // Call this is you want to get the average amplitude
        calculateAverageAmplitude();
        wf.render();
        abv.render();

        switch(mode){
            case 0: //Gráinne 
            background(0);
            break;

            case 1: //Ella
            break;

            case 2: //Loredana

            //SPIDER

            // Increment angle for swinging motion
            angle += 0.02;

            // Clear background
            background(255);
            
            // Calculate swinging motion
            float swing = sin(angle) * 50;

            //cobweb
            stroke(0);
            line(width / 2 + swing, 0, width / 2 + swing, height / 2);

            // Body
            fill(0);
            ellipse(width/2 + swing, height/2, 30, 30);
            
            // Legs on right side
            drawLeg(width/1.97f + swing, height/2, 30, PI/3);
            drawLeg(width/1.97f + swing, height/2, 30, PI/6);
            drawLeg(width/1.97f + swing, height/2, 30, -PI/6);
            drawLeg(width/1.97f + swing, height/2, 30, -PI/3);

            // Legs on the left side
            drawLeg(width/2.02f + swing, height/2, 30, -2*PI/3);
            drawLeg(width/2.02f + swing, height/2, 30, -5*PI/6);
            drawLeg(width/2.02f + swing, height/2, 30, PI);
            drawLeg(width/2.02f + swing, height/2, 30, 5*PI/6);

        
            break;
        }
    }

    void drawLeg(float x, float y, float len, float angle) {
        float dx = len * cos(angle);
        float dy = len * sin(angle);
        line(x, y, x + dx, y + dy);
    }
}
