package example;

import ie.tudublin.*;
import processing.core.PShape;

public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;
    int mode = 0;
    float angle = 0;

    PShape spider_head;
    float ry;

    public void settings() {
        size(2048, 1000, P3D);

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

        try
        {
            spider_head = loadShape("data\\spiderman1.obj");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            System.err.println("Error loading shape file: " + e.getMessage());
        }

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

    void drawWaveform(float amplitude, float x, float y, float centerX, float centerY) 
    {
        float distance = dist(x, y, centerX, centerY); // Calculate distance from center
        float maxDistance = dist(0, 0, width / 2, height / 2); // Maximum distance from center
        float mappedDistance = map(distance, 0, maxDistance, 0, 1); // Map distance to range [0, 1]
        float newSize = map(amplitude, 0, 1, 10, 400) * mappedDistance; // Enlarge based on amplitude and distance
        float hue = map(amplitude, 0, 1, 0, 255);
        fill(hue, 255, 255);
        noStroke();
        rectMode(CENTER);
        rect(x, y, newSize, newSize);
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
            
            ry += 0.02;
    
            translate(width/2, height/2 + 100, -200);
            rotateZ(PI);
            rotateY(ry);
            shape(spider_head);
            // ambientLight(255, 255, 255);
            // pointLight(0, 100, 100, 100, -spider_head.height, 1000);
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
