# Music Visualiser Project

Name: Ella Fahey

Student Number: C22396101

Name: Gráinne Bannister

Student Number: C22331903

Name: Loredana Bura

Student Number: C22370523

## Instructions
- Fork this repository and use it a starter project for your assignment
- Create a new package named your student number and put all your code in this package.
- You should start by creating a subclass of ie.tudublin.Visual
- There is an example visualiser called MyVisual in the example package
- Check out the WaveForm and AudioBandsVisual for examples of how to call the Processing functions from other classes that are not subclasses of PApplet

# Description of the assignment
For our programming assignemnt, we delved into the realm of music visualization using Java Processing. Taking cues from "Sunflower" by Post Malone, a track from the Spiderman: Into the Spiderverse soundtrack, we set out to create six unique visualizations paying homage to the iconic Spiderman universe. Leveraging Java's capabilities, we merged music analysis algorithms with graphic rendering techniques, resulting in dynamic visuals that responded dynamically to the song's frequencies. From oscillating spider webs to pulsating flowers and dynamic hexagonal tunnels, each visualization captured the essence of the music while infusing elements inspired by the superhero saga. This project provided us with a hands-on opportunity to refine our object-oriented design skills, dive deeper into programming concepts, and unleash our creativity in visual programming.

# Instructions
- Fork this repository
- Clone the repository
- Compile program and run it
- Use keys 0 to 5 to change the visuals
- Enjoy :)

# How it works
**Ella:**

Throughout the duraiton of the assignment I created two visuals, these include the Hexagon and Starburst visual. Firstly, I'll decribe how the hexagon visual works.

The Hexagon Visual incorporates various methods designed to create an engaging and dynamic visualization inspired by hexagonal shapes and patterns. It includes methods such as drawHexagons, drawConfetti, drawStars, drawShootingStars, and drawLines, each contributing to the overall aesthetic and interactivity of the visualization. Additionally, Case 4 within the draw() method is dedicated to invoking the hexagon visual, orchestrating the rendering of hexagons, confetti particles, stars, and shooting stars based on the audio input's frequency. 

```Java
  case 4: 
         //Ella
            // hexagonTunnel.repaint()
            colorMode(HSB); // Set color mode to HSB

            drawHexagons(width / 2.0f, height / 2.0f, 400.0f + getAmplitude(), 20, getSmoothedBands());
            float c = map(amplitude, 0, 500, 0, 255);
            //fill(c, 0, 0);
            fill(c, 255, 255);
        
        break;


//hexagon, stars, shooting stars, confetti and lines all added for Ella's hexagon visual
    void drawHexagons(float x, float y, float outerRadius, int numHexagons, float[] ab) 
    {
        if (ab == null) {
            return; // Exit the method early if ab is null
        }
        float angleStep = TWO_PI / 6;
        float maxAmplitude = max(ab);
        float gap = 10; // Gap between hexagons
        float maxOuterRadius = 400.0f + maxAmplitude;//calculate max outer radius dynamically based on max amplitude

        
        for (int i = 0; i < numHexagons; i++) 
        {
            float innerRadius = maxOuterRadius * (numHexagons - i) / numHexagons;//ensures that innerRadius decreases as i increases,
            // resulting in smaller inner radii towards the center of the hexagon arrangement.
            float brightness = map(ab[i % ab.length], 0, maxAmplitude, 50, 100); // Reactivity to music
            float newSize = map(ab[i % ab.length], 0, maxAmplitude, 50, 400); // Adjust size based on frequency


            if (i % 2 == 0) {
                // Set blue color (RGB values)
                fill(0, 0, 255, brightness); // Blue color
            } else {
                // Set orange color (RGB values)
                fill(255, 165, 0, brightness); // Orange color
            }

             // Draw the hexagon with the adjusted size
            drawHexagon(x, y, newSize);

            // Set the fill color for confetti (random colors)
            float confettiHue = random(360);
            float confettiSaturation = random(90, 255); // Random saturation between 90 and 255
            float confettiBrightness = random(90, 255); // Random brightness between 90 and 255
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
            drawStars(x, y, maxOuterRadius, innerRadius, ab[i % ab.length]);
            //drawShootingStars(x, y, outerRadius, innerRadius, ab[i % ab.length]);
            drawConfetti(x, y, innerRadius, maxOuterRadius, ab[i % ab.length]);


            maxOuterRadius -= gap; // Adjust the outer radius for the next hexagon
        }
    }

    void drawHexagon(float x, float y, float size) {
        float angleStep = TWO_PI / 6;
        beginShape();
        for (int j = 0; j < 6; j++) {
            float angle = j * angleStep;
            float hx = x + cos(angle) * size;
            float hy = y + sin(angle) * size;
            vertex(hx, hy);
        }
        endShape(CLOSE);
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

```

For my second visual I created a Starburst-like visual. This component of the program creates a dynamic and visually engaging representation that emanates from the center of the canvas. Responding to the frequency data extracted from the audio input, it generates a pulsating pattern characterized by vibrant colors and shifting shapes, synchronizing seamlessly with the music's rhythm and intensity.

```Java

case 3: 
            background(0); //background to black
            translate(width / 2, height / 2); //translate origin to center of canvas
            maxAmplitude = max(ab.toArray()); //find max value in the ab ArrayList and assign to maxAmplitude
            int numSegments = 400; //num segment in tunnel
            float segmentAngle = TWO_PI / numSegments; //angle of each segment
            float tunnelRadius = min(width, height) / 3; //difine radius of tunnel
        
            for (int i = 0; i < numSegments; i++) {
            float angle = i * segmentAngle;

            //calcuclate coordinates of start and end points of segment
            float x1 = cos(angle) * tunnelRadius;
            float y1 = sin(angle) * tunnelRadius;
            float x2 = cos(angle + segmentAngle) * tunnelRadius;
            float y2 = sin(angle + segmentAngle) * tunnelRadius;

            float freq = ab.get(i % ab.size()) * 3000; // Adjust frequency range
            float z1 = map(freq, 0, maxAmplitude * 10, -200, 200); // Adjust mapping to control tunnel depth
            float z2 = map(ab.get((i + 1) % ab.size()) * 3000, 0, maxAmplitude * 10, -200, 200);
        
            stroke(map(i, 0, numSegments, 0, 255), 255, 255); //set stroke color based on segment index
            line(x1, y1, z1, x2, y2, z2);
         }
        break;

```




**Gráinne:**

For my portion of my project, I choose to create a 3D object visual which would match the theme of our project - spiderman. I used a 3D object which I found online to be the main element in my visual. I did this by importing the shape and loading it using pShape, load image. To make the head spin, I used the rotateZ and rotateY functions to rotate the shape in a 360 degree rotation. Then for the visual which borders the spiderman head, I used a sound wave representation.

```Java
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
```

```Java
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
```

For my second visual, I choose to stick with the theme of the song and create a visual with flowers. To do this, I created flowers by using the stroke method to draw 10 petals and rotate them evenly using the PI constant. I then added an elipse in the middle of the flower to finish the flower shape. I filled the flowers with randomly generated colours. 

```Java
void flower(float x, float y, float size) //function to draw flowers
{
strokeWeight(size); 
stroke(random(120,255), random(255), random(255)); //random color chosen for flowers

translate (x,y); 

for(int i =0; i<10; i++) //10 petals on the flower
{
    rotate(TWO_PI/ 10); //evenly spaced
    line(0,0,3 * size, 0); 
}

strokeWeight(0);
fill(random(120,255), random(255), random(255)); //elipse colour
ellipse(0, 0, 15, 15);

}
```

I implemented the flower in my case to be created randomly. It will have a random width, random height and random size and will generate sporadically on the screen.  

```Java
case 5: //Gráinne

    flower(random(width), random(height), random(10,150));

break;
```

**Loredana:**

I worked on the particles visual and the hanging spiders visual. The particle visualisation was achieved by creating a large number of ellipse particles that move around the screen in a random manner. If the distance between particles is less than the radius specified, then the particles will be connected by a line. As the particles move away from each other, the line fades away. By increasing the speed and the number of particles, I was able to obtain a visual that resembles a moving spiderweb. I dealt with all the possibilities of the particles moving off screen in the code below:

```Java
//if the position of x moves too far left, it will appear from the right side of the screen
if (pos.x < -10){
	pos.x = width;
} 
//if the position of x moves too far right, it will appear from the left side of the screen
if (pos.x > width + 10){
	pos.x = 0;
}            
//if the position of x moves too far up the screen, it will appear from the bottom of the screen
if (pos.y < -10) {
	pos.y = height;
}
//if the position of x moves too far down the screen, it will appear from the top of the screen
if (pos.y > height + 10){
	pos.y = 0;
} 

//Particle moves in differnt directions without going off screen by using constrain()
vel.x = constrain(vel.x + random(-spd, spd), -max, max);
vel.y = constrain(vel.y + random(-spd, spd), -max, max);
```

By implementing these boundary handling mechanisms and velocity constraints, the code ensures that the particles remains within the visible area of the screen while still allowing for dynamic movement in various directions. For the colour, I used map to set random particles to different shades of blue and red: 

```Java
if (chooseColour < 0.8) {
	//setting blue colour
	float blueHue = map(chooseColour, 0.5f, 1.0f, 170, 175);
	stroke(blueHue, 255, 255);
} 
else {
	//setting red colour
	float redHue = map(chooseColour, 0, 0.5f, 0, 2);
	stroke(redHue, 255, 255);   
}
```

The second visualisation consists of three spiders swinging from a web and a red sine wave. The spiders were created using an ellipse for the body and eight lines for the legs. Each leg is drawn using a function called 'drawLeg' that specifies its starting position and angle relative to the body's center. I used the variable 'swing' to control the horizontal movement of the spiders. 

```Java
//cobweb
stroke(255);
line(width / 2 + swing, 0, width / 2 + swing, height / 2);

// Body
fill(255);
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
```

I added a red sine wave that responds to music, which was achieved through the use of lerp and smoothing techniques. The implementation utilises a for loop to iterate over the width of the screen, ensuring that each pixel along the horizontal axis is considered for line generation.

```Java
for(int x = 0 ; x < width ; x ++) { 
	int i = x % ab.size();
	stroke(0, 255, 255);
	noFill();
	// Smooth the x-coordinate of the line endpoint
	float lerpedX = cx + lerpedBuffer[i] * cx;
	smoothedX = lerp(smoothedX, lerpedX, 2); 
	
	line(x, cy, x, smoothedX);
}
```



# What I am most proud of in the assignment
**Ella:**

**Gráinne:**

I'm most proud of the spiderman head object as I had to go through the process of learning new applications to import it. When I found the shape I wanted to use online https://free3d.com/3d-model/spiderman-head-95908.html, I found out that I would have to change the stl file to an obj file. I did this by downloading an app called blender which enabled me to change it to an obj file and I also implemented the spiderman red colour in the mtl file. I found the process of converting the file and using functions in java processing such as rotate interesting, as I was able to play with how the spiderman head would move around the screen. This created a nice visual which suited the music and the overall theme of the project. I am also proud of how my team was able to work together from the start till the end of the project. We all contributed our best efforts when organising and planning the project which made the actual project easy and efficient. 

**Loredana:**

What I am most proud of in this assignment is the implementation of the particles. At first, it was very challenging to conceptualise how to create a dynamic and visually appealing particle system, and I spent a lot of time experimenting and testing the code. Each obstacle presented a unique puzzle to solve, and through perseverance and some luck, I was able to overcome these challenges and develop a visualisation that exceeded my initial expectations. I am also proud of the team work that went into this project.Through effective communication and hard work, we were able to come up with interesting ideas that appeal to our unique strengths and interests, solve problems, and provide valuable feedback to one another.

# Screenshots

![Screenshot 2024-04-25 130652](https://github.com/Loredana10/MusicVisuals/assets/124162358/34c5f143-7aef-4aff-b8f7-21446f2a5caa)

![Screenshot 2024-04-25 122903](https://github.com/Loredana10/MusicVisuals/assets/124162358/577eddf1-ae67-465c-8acf-0183d2e83bd0)

![image](https://github.com/Loredana10/MusicVisuals/assets/124162358/82ae1361-5d80-476f-8bcc-7c6c6aec9a56)
![Screenshot 2024-04-25 130802](https://github.com/Loredana10/MusicVisuals/assets/124162358/b1339f30-6c17-45e0-814a-518c4623acb4)
![Screenshot 2024-04-25 130758](https://github.com/Loredana10/MusicVisuals/assets/124162358/a99f0b6a-1e0e-4e96-8d9d-08c92825294b)

![Screenshot 2024-04-25 130746](https://github.com/Loredana10/MusicVisuals/assets/124162358/20458bab-a03d-41fe-a39c-395c38f45d96)

![Screenshot 2024-04-25 130741](https://github.com/Loredana10/MusicVisuals/assets/124162358/a6a260e1-854c-40a0-b716-e86ed33697b3)

![Screenshot 2024-04-25 130734](https://github.com/Loredana10/MusicVisuals/assets/124162358/291bbcca-97ac-42b8-8a93-a7f4bb9fd9b6)
![Screenshot 2024-04-25 130717](https://github.com/Loredana10/MusicVisuals/assets/124162358/26b86095-4133-46dc-b042-605a61169d0f)

![image](https://github.com/Loredana10/MusicVisuals/assets/124152490/a64000c8-a031-4cb7-8309-d17ebdbc4cec)


# Youtube Video

https://youtu.be/28JY8GFCsB0 

# Markdown Tutorial

This is *emphasis*

This is a bulleted list

- Item
- Item

This is a numbered list

1. Item
1. Item

This is a [hyperlink](http://bryanduggan.org)

# Headings
## Headings
#### Headings
##### Headings

This is code:

```Java
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

So is this without specifying the language:

```
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

This is an image using a relative URL:

![An image](images/p8.png)

This is an image using an absolute URL:

![A different image](https://bryanduggandotorg.files.wordpress.com/2019/02/infinite-forms-00045.png?w=595&h=&zoom=2)

This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)

This is a table:

| Heading 1 | Heading 2 |
|-----------|-----------|
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |

