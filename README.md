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

# Instructions
- Fork this repository
- Clone the repository
- Compile program and run it
- Use keys 0 to 5 to change the visuals
- Enjoy :)

# How it works
**Ella:**

**Gráinne:**

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

