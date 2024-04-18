package example;

import ie.tudublin.*;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.GeneralPath;

public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;
    int mode = 0;
    float angle = 0;

    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    PShape spider_head;
    float ry;

    //code to smooth amplitude 
    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;

    // EndlessHexagonTunnel hexagonTunnel;

    public void settings() {
        //size(2048, 1000, P3D);

        // Use this to make fullscreen
        //fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        startMinim();

        minim = new Minim(this);
        // Uncomment this to use the microphone
        // ai = minim.getLineIn(Minim.MONO, width, 44100, 16);
        // ab = ai.mix; 
        ap = minim.loadFile("data\\Post Malone, Swae Lee - Sunflower (Spider-Man_ Into the Spider-Verse) (256 kbps).mp3", 1024);
        ap.play();
        ab = ap.mix;
        // Call loadAudio to load an audio file to process
        loadAudio("data\\Post Malone, Swae Lee - Sunflower (Spider-Man_ Into the Spider-Verse) (256 kbps).mp3");

        // Call this instead to read audio from the microphone
        // startListening();

        try {
            spider_head = loadShape("data\\spiderman1.obj");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading shape file: " + e.getMessage());
        }

        spider_head.setFill(color(255, 0, 0));

        wf = new WaveForm(this);
        abv = new AudioBandsVisual(this);
        // hexagonTunnel = new EndlessHexagonTunnel(this);

        y = height / 2;
        smoothedY = y;

        lerpedBuffer = new float[width];
    }

    public void keyPressed() {
        if (key >= '0' && key <= '5') {
            mode = key - '0';
        }
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }

    void drawWaveform(float amplitude, float x, float y, float centerX, float centerY) {
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

    void flower(float x, float y, float size)
    {
        strokeWeight(size);
        stroke(random(120,255), random(255), random(255));

        translate (x,y);

        for(int i =0; i<10; i++)
        {
            rotate(TWO_PI/ 10);
            line(0,0,3 * size, 0);
        }

        strokeWeight(0);
        fill(random(120,255), random(255), random(255));
        ellipse(0, 0, 15, 15);

    }

    float off = 0;

    public void draw() {
        background(0);

        // Call this is you want to use frequency bands
        calculateFrequencyBands();

        // Call this is you want to get the average amplitude
        calculateAverageAmplitude();
        wf.render();
        abv.render();

        switch(mode){
            case 0: //Gráinne 
            background(0);
            lights();
  
            ry += 0.02;

            float centerX = width / 2;
            float centerY = height / 2;
            float stepX = width / (float)ab.size();
            float stepY = height / (float)ab.size();
    
            // Draw waveform on the top side
            for (int x = 0; x < ab.size(); x++) {
                float amplitude = ab.get(x);
                float posX = x * stepX + stepX / 2;
                float posY = stepY / 2;
                drawWaveform(amplitude, posX, posY, centerX, centerY);
            }
    
            // Draw waveform on the right side
            for (int y = 0; y < ab.size(); y++) {
                float amplitude = ab.get(y);
                float posX = width - stepX / 2;
                float posY = y * stepY + stepY / 2;
                drawWaveform(amplitude, posX, posY, centerX, centerY);
            }
    
            // Draw waveform on the bottom side
            for (int x = 0; x < ab.size(); x++) {
                float amplitude = ab.get(x);
                float posX = x * stepX + stepX / 2;
                float posY = height - stepY / 2;
                drawWaveform(amplitude, posX, posY, centerX, centerY);
            }
    
            // Draw waveform on the left side
            for (int y = 0; y < ab.size(); y++) {
                float amplitude = ab.get(y);
                float posX = stepX / 2;
                float posY = y * stepY + stepY / 2;
                drawWaveform(amplitude, posX, posY, centerX, centerY);
            }  

            translate(width/2, height/2 + 60, -250);
            rotateZ(PI);
            rotateY(ry);
            fill(255, 0, 0);
            shape(spider_head);
            break;

            case 1: //Ella
                // hexagonTunnel.repaint();
            break;

            case 2: //Loredana

                //Calculating the sum and average of the samples
                //Learping each element in the buffer
                float average = 0;
                float sum = 0;
                float cx = width /2;
                float cy = height;
            
                float smoothedX = cx;

                off += 1;

                for(int i = 0; i < ab.size(); i++) {
                    sum += abs(ab.get(i));
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.05f);
                }
                average = sum / (float) ab.size();
            
                smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);

                //SPIDER

                // Increment angle for swinging motion
                angle += 0.02;

                // Clear background
                background(255);

                // Calculate swinging motion
                float swing = sin(angle) * 50;

                //SPIDER 1

                //cobweb
                stroke(0);
                line(width / 2 + swing, 0, width / 2 + swing, height / 2);

                // Body
                fill(0);
                ellipse(width / 2 + swing, height / 2, 30, 30);

                // Legs on right side
                drawLeg(width / 1.97f + swing, height / 2, 30, PI / 3);
                drawLeg(width / 1.97f + swing, height / 2, 30, PI / 6);
                drawLeg(width / 1.97f + swing, height / 2, 30, -PI / 6);
                drawLeg(width / 1.97f + swing, height / 2, 30, -PI / 3);

                // Legs on the left side
                drawLeg(width / 2.02f + swing, height / 2, 30, -2 * PI / 3);
                drawLeg(width / 2.02f + swing, height / 2, 30, -5 * PI / 6);
                drawLeg(width / 2.02f + swing, height / 2, 30, PI);
                drawLeg(width / 2.02f + swing, height / 2, 30, 5 * PI / 6);

                //SPIDER 2

                float offsetX = 300; // Positioning the second spider to the right
                //cobweb
                stroke(0);
                line(width / 2 + swing + offsetX, 0, width / 2 + swing + offsetX, height / 2.4f);

                // Body
                fill(0);
                ellipse(width / 2 + swing + offsetX, height / 2.4f, 30, 30);

                // Legs on right side
                drawLeg(width / 1.97f + swing + offsetX, height / 2.4f, 30, PI / 3);
                drawLeg(width / 1.97f + swing + offsetX, height / 2.4f, 30, PI / 6);
                drawLeg(width / 1.97f + swing + offsetX, height / 2.4f, 30, -PI / 6);
                drawLeg(width / 1.97f + swing + offsetX, height / 2.4f, 30, -PI / 3);

                // Legs on the left side
                drawLeg(width / 2.02f + swing + offsetX, height / 2.4f, 30, -2 * PI / 3);
                drawLeg(width / 2.02f + swing + offsetX, height / 2.4f, 30, -5 * PI / 6);
                drawLeg(width / 2.02f + swing + offsetX, height / 2.4f, 30, PI);
                drawLeg(width / 2.02f + swing + offsetX, height / 2.4f, 30, 5 * PI / 6);


                //adding a red gradient sine wave 
                for(int i = 0 ; i < ab.size() ; i ++) { 
                    float hue = map(i, 0, ab.size(), 0, 32);
                    stroke(hue, 255, 255); 
                    noFill();
                    // Smooth the x-coordinate of the line endpoint
                    float lerpedX = cx + lerpedBuffer[i] * cx;
                    smoothedX = lerp(smoothedX, lerpedX, 0.1f); 
            
                    line(i, cy, i, smoothedX);
                }
                
            break;

            
            case 3: //Ella
            break;

            case 4: //Loredana
            break;

            case 5: //Gráinne

            flower(random(width), random(height), random(10,150));
    
            break;

        }
    }

    void drawLeg(float x, float y, float len, float angle) {
        float dx = len * cos(angle);
        float dy = len * sin(angle);
        line(x, y, x + dx, y + dy);
    }

    public static void main(String[] args) {
        PApplet.main("example.MyVisual");
    }
}


// class EndlessHexagonTunnel extends JPanel 
    // Ella code
// {
//     private static final int PANEL_WIDTH = 2048;
//     private static final int PANEL_HEIGHT = 1000;
//     private static final int HEX_SIZE = 50;
//     private static final int HEX_GAP = 20;
//     private static final double ROTATION_ANGLE = Math.toRadians(30);

//     private double offset = 0;
//     private MyVisual visual;

//     public EndlessHexagonTunnel(MyVisual visual) {
//         this.visual = visual;
//         setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
//         Timer timer = new Timer(20, e -> {
//             offset += 0.1;
//             visual.redraw(); // Redraw the parent applet
//         });
//         timer.start();
//     }

//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         Graphics2D g2d = (Graphics2D) g.create();
//         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//         g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//         g2d.setColor(Color.BLACK);
//         g2d.fillRect(0, 0, getWidth(), getHeight());
//         g2d.translate(PANEL_WIDTH / 2, PANEL_HEIGHT / 2);
//         g2d.rotate(offset);
//         drawHexagonTunnel(g2d);
//         g2d.dispose();
//     }

//     private void drawHexagonTunnel(Graphics2D g2d) {
//         double size = HEX_SIZE;
//         for (int i = 0; i < 20; i++) {
//             drawHexagon(g2d, 0, 0, size);
//             size -= HEX_GAP;
//         }
//     }

//     private void drawHexagon(Graphics2D g2d, double x, double y, double size) {
//         double angleIncrement = Math.PI / 3;
//         GeneralPath path = new GeneralPath();
//         for (int i = 0; i < 6; i++) {
//             double angle = i * angleIncrement;
//             double xOffset = size * Math.cos(angle);
//             double yOffset = size * Math.sin(angle);
//             if (i == 0) {
//                 path.moveTo(x + xOffset, y + yOffset);
//             } else {
//                 path.lineTo(x + xOffset, y + yOffset);
//             }
//         }
//         path.closePath();
//         g2d.setColor(Color.WHITE);
//         g2d.fill(path);
//     }
// }

