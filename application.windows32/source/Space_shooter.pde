//C16707919 
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;

// font and image varables 
PFont mainFont;
PFont secondFont;
float textMidX, textMidY;
int numFrames = 4; 
int currentFrame =0;
PImage[] boom = new PImage[numFrames];
// all audio varables 
Minim playerFire;
AudioPlayer playerFireSound;
Minim expl;
AudioPlayer badGuyExpl;
Minim click;
AudioPlayer pClick;
Minim hit;
AudioPlayer bHit;
Minim PuP;
AudioPlayer Pup;

void setup() {
  // image setup 
  imageMode(CENTER);
  boom[0] = loadImage("expl_0.png");
  boom[1] = loadImage("expl_1.png");
  boom[2] = loadImage("expl_2.png");
  boom[3] = loadImage("expl_3.png");

// sound setup 
  playerFire = new Minim(this);
  playerFireSound = playerFire.loadFile("Laser_Shoot.wav");
  expl = new Minim(this);
  badGuyExpl= expl.loadFile("Explosion.wav");
  click = new Minim(this);
  pClick = click.loadFile("Blip_Select2.wav");
  hit = new Minim(this);
  bHit = hit.loadFile("Hit_Hurt3.wav");
  PuP = new Minim(this);
  Pup = hit.loadFile("Powerup.wav");
// set up of the game
  mainFont = createFont("quadrangle.ttf", 32);
  secondFont = createFont("arialbd.ttf", 32);
  rectMode(CENTER);
  textAlign(CENTER);
  size(960, 600); 
  textMidX = width/2;
  textMidY = height/2;
  bgBoxX = width/2;
  bgBoxY = height/2;
  bgBoxWidth= width/2;
  bgBoxHeight=height/2;
  bBoxHeight = height/boxNo; 
  bBoxWidth = width/boxNo;
  for (int i =0; i <boxX.length; i++) {
    boxY[i] = random(-height, 0);
    boxX[i] = 0+bBoxWidth*i;
  }
  // background Setup
  for (int i =0; i < stars.length; i++) {
    starsX[i] = random(0, width);
    starsY[i] = random(0, height);
    starsSize[i] = random(0.5f, 2);
  }
  //set player start location 
  playerX = 50;
  playerY = height/2;
  playerSpeed = 7;
  enemySpeed = 5.0f;
  boxPosY = height/2;
  // loop set ups for all components 
  for (int i =0; i < boxPosX.length; i ++) {
    boxPosX[i] = (width/2 - boxWidth * 3)  + (boxWidth * (3 *i));
  }

  for (int i =0; i < bulletX.length; i++) {
    bulletX[i] = width + bulletWidth;
  }

  for (int i =0; i < enemyX.length; i++) {
    enemyX[i] = (int) random(width, width*2);
    enemyY[i] = (int)random(75 + enemyHeight/2, height - enemyHeight/2);
  }
// text locations 
  textWidth= width/2;
  textHeight = 200;
// hud locations
  hudCenterX= width/2;
  hudCenterY= 30;
  lifeBarX = (hudCenterX * 0.2f);
// bos location
  bossX = width  +( bossWidth* 1.5f);
  bossY = height/2;
  bossHpBarX =  (hudCenterX * 1.5);
  //bossHpBackground = bossHpBar * 1.4;
  bossHpBackground = bossHealth*2 ;
  textAlign(CENTER);
  frameRate(60);
  score = 000;
  sPup = new SpeedPup(width*2, random(0, height), 25, (int)random(5, 8));
  hPup = new HealthPup(width*4, random(0, height), 25, (int)random(5, 8));
  
  for(int i =0; i < enemyX.length; i++){
    enemyExpl[i] = new EExpl(width*2, height/2 );
  }
}
// setting up explosion array 
int explCount = 10;
EExpl[] enemyExpl = new EExpl[explCount];
SpeedPup sPup;
HealthPup hPup;
// keybord input set up 
boolean[] keys = new boolean[1000];
boolean canType = false;
//int ballsCount = 50;
//ball[] balls = new ball[ballsCount];

void keyPressed() {
  keys[keyCode] =true;

  /*if (canType) {
   userInput = userInput + key;
   if (keyCode == BACKSPACE) {
   userInput = "";
   }
   if ( userInput.length() > 3) {
   userInput = userInput.substring(0, userInput.length()-1);
   }
   }*/
}

void keyReleased() {  
  keys[keyCode] =false;
  canPress = true;
}
boolean checkKey(int k) {  
  return keys[Character.toLowerCase(k)] || keys[Character.toUpperCase(k)];
}
// level active 
int level = 0;
void draw() {
  println(frameRate);
  // level changing 
  if (life == 0) {
    level = 3;
    life = 100;
  }
  if (level ==0) {
    typing = false;

    

    playerFill=0;
    nameT = 0;
    mainMenu();
  }
  if (level == 1) {
    splashReset = true; 
    levelOne();
  }

  if (level == 2) {
    ftl = false;
    levelTwo();
  }
  if (level == 3) {
    splash();
  }
  if (level == 4) {
    leaderBord();
  }
  //testing 
  /*if (checkKey('k')) {
   level =3;
   }
   if (checkKey('l')) {
   score += 20000;
   }*/
}