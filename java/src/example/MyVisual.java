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
    float c = map(getAmplitude(), 0, 500, 0, 255);


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

  //float c = map(amplitude, 0, 500, 0, 255);
  fill(c, 0, 0);


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
        // hexagonTunnel.repaint()
        drawHexagons(width / 2.0f, height / 2.0f, 200.0f + getAmplitude(), 20, getSmoothedBands());
        int numColors = 360; // Number of colors in the rainbow
        int[] colors = new int[numColors]; // Array to hold rainbow colors

        for (int i = 0; i < numColors; i++) {
            colors[i] = color(i, 100, 100); // Hue ranges from 0 to 360
          }

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


                //adding a red sine wave 
                for(int x = 0 ; x < width ; x ++) { 
                    int i = x % ab.size();
                    stroke(0, 255, 255);
                    noFill();
                    // Smooth the x-coordinate of the line endpoint
                    float lerpedX = cx + lerpedBuffer[i] * cx;
                    smoothedX = lerp(smoothedX, lerpedX, 2f); 
            
                    line(i, cy, x, smoothedX);
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

  //hexagon, stars, shooting stars, confetti and lines all added for Ella's hexagon visual
  void drawHexagons(float x, float y, float outerRadius, int numHexagons, float[] ab) {
      float angleStep = TWO_PI / 6;
      float maxAmplitude = max(ab);
      float gap = 10; // Gap between hexagons
    
      
    
      for (int i = 0; i < numHexagons; i++) {
        float innerRadius = outerRadius * (numHexagons - i) / numHexagons;
        float brightness = map(ab[i % ab.length], 0, maxAmplitude, 50, 100); // Reactivity to music
    
        if (i % 2 == 0) {
          // Set blue color (RGB values)
          fill(0, 0, 255, brightness); // Blue color
        } else {
          // Set orange color (RGB values)
          fill(255, 165, 0, brightness); // Orange color
        }
    
        // Set the fill color for confetti (random colors)
        float confettiHue = random(360);
        float confettiSaturation = random(50, 100); // Random saturation between 50 and 100
        float confettiBrightness = random(50, 100); // Random brightness between 50 and 100
        fill(confettiHue, confettiSaturation, confettiBrightness);
    
        beginShape();
        for (int j = 0; j < 6; j++) {
          float angle = j * angleStep;
          float hx = x + cos(angle) * innerRadius;
          float hy = y + sin(angle) * innerRadius;
          vertex(hx, hy);
        }
        endShape(CLOSE);
    
        // Draw stars, circles, rectangles, and shooting stars in the gap
        drawStars(x, y, outerRadius, innerRadius, ab[i % ab.length]);
        //drawShootingStars(x, y, outerRadius, innerRadius, ab[i % ab.length]);
        drawConfetti(x, y, innerRadius, outerRadius, ab[i % ab.length]);
    
    
        outerRadius -= gap; // Adjust the outer radius for the next hexagon
      }
  }
    
  void drawLines(float x1, float y1, float x2, float y2) {
      stroke(255); // Set the color of the lines to white
      line(x1, y1, x2, y2); // Draw a line between the given points
  }
    
  void drawStars(float x, float y, float outerRadius, float innerRadius, float amplitude) {
      float gap = 10; // Gap between hexagons
      int numStars = floor((outerRadius - innerRadius) / gap); // Number of stars based on the gap size
      float minStarSize = 1;
      float maxStarSize = 3;
    
      float previousStarX = x;
      float previousStarY = y;
    
      // Set purple color (RGB values)
      fill(128, 0, 128); // Purple color
    
      for (int i = 0; i < numStars; i++) {
        float randomAngle = random(TWO_PI);
        float randomDistance = random(innerRadius, outerRadius);
        float starX = x + cos(randomAngle) * randomDistance;
        float starY = y + sin(randomAngle) * randomDistance;
        float starSize = random(minStarSize, maxStarSize);
        ellipse(starX, starY, starSize, starSize);
    
      // Call the drawLines function to draw lines between stars
      drawLines(starX, starY, previousStarX, previousStarY);
      }
  }
    
    
  void drawShootingStars(float x, float y, float outerRadius, float innerRadius, float amplitude) {
      // Draw shooting stars between hexagons with a different color
      float gap = 10; // Gap between hexagons
      int numStars = floor((outerRadius - innerRadius) / gap); // Number of shooting stars based on the gap size
      float minStarSize = 1;
      float maxStarSize = 3;
    
      // Set shooting star color (RGB values)
      fill(255, 255, 0); // Yellow color
    
      for (int i = 0; i < numStars; i++) {
        float randomAngle = random(TWO_PI);
        float randomDistance = random(innerRadius, outerRadius);
        float starX = x + cos(randomAngle) * randomDistance;
        float starY = y + sin(randomAngle) * randomDistance;
        float starSize = random(minStarSize, maxStarSize);
        ellipse(starX, starY, starSize, starSize);
      }
  }
    
  void drawConfetti(float x, float y, float innerRadius, float outerRadius, float amplitude) {
      int numConfetti = 50; // Number of confetti particles
      float minConfettiSize = 2;
      float maxConfettiSize = 5;
    
      for (int i = 0; i < numConfetti; i++) {
        // Random position within the hexagon
        float randomAngle = random(TWO_PI);
        float randomDistance = random(innerRadius, outerRadius);
        float confettiX = x + cos(randomAngle) * randomDistance;
        float confettiY = y + sin(randomAngle) * randomDistance;
    
        // Random confetti size
        float confettiSize = random(minConfettiSize, maxConfettiSize);
    
        // Draw confetti particle
        ellipse(confettiX, confettiY, confettiSize, confettiSize);
      }
  }
  

  public static void main(String[] args) {
        PApplet.main("example.MyVisual");
    }
}
