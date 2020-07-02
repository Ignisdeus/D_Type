import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 
import ddf.minim.signals.*; 
import ddf.minim.spi.*; 
import ddf.minim.ugens.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Space_shooter extends PApplet {

//C16707919 







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

public void setup() {
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
  bossHpBarX =  (hudCenterX * 1.5f);
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

public void keyPressed() {
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

public void keyReleased() {  
  keys[keyCode] =false;
  canPress = true;
}
public boolean checkKey(int k) {  
  return keys[Character.toLowerCase(k)] || keys[Character.toUpperCase(k)];
}
// level active 
int level = 0;
public void draw() {
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
// background setup;
int starCount = 1000;
float[] stars = new float[starCount];
float[] starsX = new float[starCount];
float[] starsY = new float[starCount];
float[] starsSize = new float[starCount];
float starsSpeed = 1.0f;
boolean bossFightActive = true;
boolean ftl = false;

public void drawBackground() {

  if (ftl == true && starsSpeed < 15.0f) {
    starsSpeed += 0.05f;
  }
  if (ftl == false && starsSpeed > 1.0f) {
    starsSpeed -= 0.05f;
  }


  noStroke();
  background(0);
  fill(255);
// stars loop 
  for (int i =0; i < stars.length; i++) {

    starsX[i] -= starsSpeed;
    ellipse(starsX[i], starsY[i], starsSize[i], starsSize[i]);

    if (starsX[i] < 0) {

      starsY[i] = random(0, height);
      starsSize[i] = random(0.5f, 2);
      starsX[i] = width + starsSize[i];
    }
  }
}
// player set up 
float playerX, playerY, playerSpeed;
float playerWidth = 75, playerHeight= 12, playerScore, playerCPit = 20, pYFix = 6.25f;


public void player() {  

  if (playerY - playerHeight/2 < 75) {
    playerY =75 + playerHeight;
  }
  if (playerY + playerHeight/2 > height) {
    playerY = height - playerHeight;
  }
  if (playerX - playerWidth/2 < 0) {
    playerX = + playerWidth/2;
  }
  if (playerX + playerHeight/2 > width/2) {
    playerX = width/2 - playerHeight;
  }


  // main center
  fill(175);
  rect(playerX, playerY, playerWidth, playerHeight);
  fill(120);
  rect(playerX, playerY- playerHeight/3, playerWidth, playerHeight/3);
  // cockpit
  fill(175);
  rect(playerX + playerWidth/2, playerY - (playerCPit / 5), playerCPit, playerCPit);
  fill(120);
  rect(playerX + playerWidth/2, playerY- playerCPit/2, playerCPit, playerCPit/3);
  fill(175);
  triangle(playerX + ((playerWidth/2 + playerCPit/2)-1), playerY +  pYFix, playerX + ((playerWidth/2 + playerCPit/2)-1), playerY - pYFix-8.75f, playerX + ((playerWidth/2 + playerCPit/2)*1.35f), playerY + pYFix);
  // Tail End 
  rect(playerX- playerWidth/3, playerY - playerHeight, playerWidth/3, playerHeight*2);
  fill(120);
  rect(playerX- playerWidth/3, playerY - playerHeight*1.8f, playerWidth/3, playerHeight/2);
  fill(175);
  rect(playerX- playerWidth/5, playerY + playerHeight, playerWidth/2, playerHeight);
  fill(120);
  rect(playerX- playerWidth/5, playerY + playerHeight/5, playerWidth/2, playerHeight/2);
}
// player shooting set up 
int goodGuyBulletCount = 30;
int bulletMarker =0;
float[] bulletX = new float[goodGuyBulletCount];
float[] bulletY = new float[goodGuyBulletCount];
int bulletHeight = 5, bulletWidth = 10;
float playerBulletSpeed = 14;
int shootTimer = 0, shotDelay = 15;

public void playerShoot() {

  if (bulletMarker < goodGuyBulletCount-1 && shootTimer > shotDelay) {
    playerFireSound.rewind();
    playerFireSound.play();
    shootTimer = 0;
    bulletX[bulletMarker] = playerX + playerWidth/2;
    bulletY[bulletMarker] = playerY;
    bulletMarker ++;
  }
  if (bulletMarker == goodGuyBulletCount-1) {
    bulletMarker = 0;
  }
}  
public void playerShot() {
  shootTimer ++;
  for (int i =0; i < bulletX.length; i++) {
    if (bulletX[i] - bulletWidth < width + bulletWidth) {
      fill(255, 0, 0);
      rect(bulletX[i], bulletY[i], bulletWidth, bulletHeight);
      bulletX[i] += playerBulletSpeed;
    }
  }
}
// enemyCount set up 
int enemyCount = 10;
int[] enemyX = new int[enemyCount];
int[] enemyY = new int[enemyCount];
boolean[] enemyActive = new boolean[enemyCount];
float enemySpeed;
int enemyWidth = 50, enemyHeight = 25;
float enemySpawnTimer = 0;
boolean enemySpawn = true;
int eSpawnTime =10;


public void enemy() {
  for (int i =0; i < enemyX.length; i++) {

    for (int oi = 0; oi < enemyX.length; oi++) {

      if (enemyX[i] + enemyWidth > enemyX[oi]- enemyWidth
        &&enemyX[i] - enemyWidth < enemyX[oi]- enemyWidth) {
        enemyActive[oi] = false;
        println("kill");
        enemySpawnTimer = eSpawnTime;
        enemyX[oi] = (int) random(width + enemyWidth, width*2);
        enemyY[oi] = (int)random(hudHeight + enemyHeight, height - enemyHeight);
      }
    }
  }
  if (enemySpeed > 15.0f) {

    enemySpeed = 15.0f;
  }
  if (enemySpeed < 5.0f) {

    enemySpeed = 5.0f;
  }
  if ( enemySpawn == true) {
    enemySpawnTimer ++;
  }
  if (enemySpawnTimer > eSpawnTime) {
    enemySpawnTimer =0;
    int toBeActive = (int) random(0, enemyCount -1);
    if (enemyActive[toBeActive] == false) {
      enemyActive[toBeActive] = true;
    } else {
      enemySpawnTimer= eSpawnTime+1;
    }
  }
  for (int i =0; i < enemyX.length; i++) { 
    if (enemyActive[i] == true) {
      enemyX[i] -=  enemySpeed;
      fill(175);
      // arc(enemyX[i], enemyY[i], enemyHeight*3, enemyHeight, 0, 90);
      stroke(255, 0, 0);
      ellipse(enemyX[i], enemyY[i], enemyHeight, enemyHeight);
      arc(enemyX[i], enemyY[i]+ enemyHeight/2, enemyHeight*3, enemyHeight, 0, PI, CHORD);
      arc(enemyX[i], enemyY[i] - enemyHeight/2, enemyHeight*3, enemyHeight, PI, TWO_PI, CHORD);
      noStroke();
    } 
    if (enemyX[i] < 0 - enemyWidth/2) {
      enemyActive[i] = false;
      enemyX[i] = (int) random(width + enemyWidth, width*2);
      enemyY[i] = (int)random(hudHeight + enemyHeight, height - enemyHeight);
    }
    for (int b =0; b < bulletX.length; b++) {
      if (enemyX[i] - enemyHeight * 1.5f < bulletX[b]
        && enemyX[i] + enemyHeight * 1.5f > bulletX[b]  
        && enemyY[i] + enemyHeight*1.5f > bulletY[b]
        && enemyY[i] - enemyHeight*1.5f < bulletY[b]
        && bulletX[b] < width) {
        badGuyExpl.rewind();
        badGuyExpl.play();
        enemyExpl[i].explLoc = new PVector(enemyX[i],  enemyY[i] + enemyHeight/2);
        enemyActive[i] = false;
        enemyX[i] = (int) random(width + enemyWidth, width*2);
        enemyY[i] = (int)random(hudHeight + enemyHeight, height - enemyHeight);
        //enemyY[i] = height/2;
        //enemyX[i] = width*2;
        bulletX[b]=width + bulletWidth;
        enemySpeed += 0.5f;
        score += 100;
      }
    }

    if (enemyX[i] - enemyHeight* 1.5f < playerX + ((playerWidth/2 + playerCPit/2)*1.35f) 
      &&  enemyX[i] + enemyHeight*1.5f > playerX - playerWidth/2 
      && enemyY[i] + enemyHeight*0.5f  > playerY-playerHeight*3 
      && enemyY[i] - enemyHeight*0.5f < playerY + playerHeight/2 ) {
      enemyActive[i] = false;
      badGuyExpl.rewind();
      badGuyExpl.play();
      enemyExpl[i].explLoc = new PVector(enemyX[i], enemyY[i] + enemyHeight/2);
      enemyX[i] = (int) random(width + enemyWidth, width*2);
      enemyY[i] = (int)random(75 + enemyHeight, height - enemyHeight);
      burn += 10;
    }
  }
}

// score and hud set up 
int score = 0;
float hudCenterX, hudCenterY, hudHeight = 60;
public void hud() {

  fill(0, 206, 209);
  rect(hudCenterX, hudCenterY, width, hudHeight*1.2f);
  rect(hudCenterX/2, hudCenterY, width/3, hudHeight *1.7f);
  triangle(hudCenterX/2-(width/3)/2, 0, hudCenterX/2-(width/3)/2, hudHeight *1.35f, (hudCenterX/2-(width/3)/2) - 100, 0 );
  triangle(hudCenterX/2+(width/3)/2, 0, hudCenterX/2+(width/3)/2, hudHeight *1.35f, (hudCenterX/2+(width/3)/2) + 100, 0 );

  fill(0, 2, 46);
  rect(hudCenterX, hudCenterY, width, hudHeight);
  rect(hudCenterX/2, hudCenterY, width/3, hudHeight *1.5f);
  triangle(hudCenterX/2-(width/3)/2, 0, hudCenterX/2-(width/3)/2, hudHeight *1.25f, (hudCenterX/2-(width/3)/2) - 100, 0 );
  triangle(hudCenterX/2+(width/3)/2, 0, hudCenterX/2+(width/3)/2, hudHeight *1.25f, (hudCenterX/2+(width/3)/2) + 100, 0 );
  score();
  lifeBar();
  if (level ==2) {
    bossHud();
  }
  if (level == 1) {
    timer();
  }
}
// boss hud changes 

float life = 100, lifeBarX, lifeBarY = hudHeight * 0.40f, lifeBarWith = life*3, lifeBarHeight = 15;
float hpHolder = lifeBarWith;
float burn, Healthpup;
public void lifeBar() {

  if (life > 100) {
    life = 100;
  }
  lifeBarWith = life*3;
  rectMode(CORNER);
  fill(0, 206, 209);
  rect(lifeBarX -hpHolder * 0.02f, hudCenterY, hpHolder * 1.04f, lifeBarHeight* 1.4f);

  fill(0, 2, 46);
  rect(lifeBarX, hudCenterY * 1.1f, hpHolder, lifeBarHeight);

  if (life > 60) {
    fill(0, 255, 0);
  } else if (life >25) {
    fill(255, 255, 0);
  } else if (life >0) {
    fill(255, 0, 0);
  }
  if (life < 0) {
    life = 0;
  }
  rect(lifeBarX, hudCenterY * 1.1f, life*3, lifeBarHeight);
  //rect(lifeBarX, lifeBarY, lifeBarWith, lifeBarHeight);
  rectMode(CENTER); 

  // This is done for the effect 
  if ( burn > 0) {
    burn -= 0.5f;
    life -= 0.5f;
  }
  if ( Healthpup > 0) {
    Healthpup--;
    ;
    life ++;
  }
}
// score desplay
String scoreT = "Score: ";
public void score() {
  fill(255);
  textSize(24);
  text(scoreT + score, hudCenterX, hudCenterY * 1.2f );
}
// clock set up 
int clock = 1, second = 15, frame =0;
public void timer() {

  frame++;
  if (frame % 60 == 0 && second != -1) {
    second--;
  }
  if (second == -1 && frame % 60 == 0) {
    second = 59;
    clock--;
  }
  if (clock < 0 && bossFightActive == false) {
    level = 3;
  } else if (clock < 0 && bossFightActive == true) {
    level = 2;
  }
  if (second > 9) {
    text(clock +": " + second, hudCenterX * 1.5f, hudCenterY * 1.2f );
  } else {
    text(clock +": 0" + second, hudCenterX * 1.5f, hudCenterY * 1.2f );
  }
}
// boss set up 
float bossX, bossY, bossWidth = 100, bossHeight = 50, bossJump = 5, moveTimer = 0;
boolean bossHit = false, bossMove = true;

public void boss() {


  if ( bossX > (width - bossWidth *1.5f)|| bossX < (hudHeight + bossWidth *1.5f) ) {

    bossX -= bossJump;
  }

  moveTimer ++;

  if ( moveTimer > random(10.0f, 60.0f)) {
    moveTimer =0;
    bossMove = !bossMove;
  }
  bossHit= false;
  for (int b =0; b < bulletX.length; b++) {
    if (bulletX[b] > bossX - bossWidth/2 
      && bulletX[b] < bossX + bossWidth/2
      && bulletY[b] > bossY - bossHeight
      &&  bulletY[b] < bossY + bossHeight
      && bulletX[b] < width) {
      bHit.rewind();
      bHit.play();
      bossHit = true;
      bossBurn = 5;
      score += 50;
      bulletX[b]=width + bulletWidth;
    }
  }
  if ( bossHealth <= 0) {
    badGuyExpl.rewind();
    badGuyExpl.play();
    score *= 2;
    level = 3;
  }

  if ( bossMove == true && bossY < height - bossHeight) {
    bossY += bossJump;
  } 
  if ( bossMove == false && bossY > bossHeight) {
    bossY -= bossJump;
  }

  if ( bossHit == false) {
    fill(0, 17, 171);
    ellipse(bossX, bossY, bossWidth * 1.5f, bossHeight);
    fill(225, 175, 0);
    triangle(bossX - bossWidth/2, bossY, bossX + bossWidth/2, bossY, bossX, bossY - bossHeight );
    fill(255, 215, 0);
    triangle(bossX - bossWidth/2, bossY, bossX - ((bossWidth/2) * 0.75f), bossY, bossX, bossY - bossHeight );
    fill(205, 155, 0);
    triangle(bossX + bossWidth/2, bossY, bossX + ((bossWidth/2) * 0.75f), bossY, bossX, bossY - bossHeight );
  }
  if ( bossHit == true) {
    fill(255, 0, 0);
    ellipse(bossX, bossY, bossWidth * 1.5f, bossHeight);
    triangle(bossX - bossWidth/2, bossY, bossX + bossWidth/2, bossY, bossX, bossY - bossHeight );
    triangle(bossX - bossWidth/2, bossY, bossX - ((bossWidth/2) * 0.75f), bossY, bossX, bossY - bossHeight );
    triangle(bossX + bossWidth/2, bossY, bossX + ((bossWidth/2) * 0.75f), bossY, bossX, bossY - bossHeight );
  }
  if (active == false) {
    active = true;
    bossBulletX = bossX - bossWidth/2;
    bossBulletY = bossY;
  }
}
// boss hud set up 
float bossHpBarX, bossHealth = 100, bossHpBackground, bossBurn=0; 

public void bossHud() {


  rectMode(CORNER);
  fill(0, 206, 209);
  rect(bossHpBarX -4, hudCenterY -1, bossHpBackground * 1.05f, lifeBarHeight * 1.2f);
  fill(0, 2, 46);
  rect(bossHpBarX, hudCenterY, bossHpBackground, lifeBarHeight);
  fill(255, 0, 0);
  rect(bossHpBarX, hudCenterY, bossHealth *2, lifeBarHeight);



  // This is done for the effect 
  if ( bossBurn > 0) {
    bossBurn -= 0.2f;
    bossHealth -= 0.2f;
  }
  rectMode(CENTER);
}

// boss combat set up 
float bossBulletX, bossBulletY, bossBulletSpeed, bossBulletWidth = 25, bossBulletAim = 5;
boolean active = false; 

public void bossBullet() {


  if (active == true) {
    bossBulletX -= bossBulletSpeed;
    fill(0, 255, 0);
    ellipse( bossBulletX, bossBulletY, bossBulletWidth, bossBulletWidth);
    fill(0, 200, 0);
    ellipse( bossBulletX, bossBulletY, bossBulletWidth*0.5f, bossBulletWidth * 0.5f);

    if ( bossBulletX < playerX - playerWidth) {
      bossBulletSpeed = 15;
    } else {
      bossBulletSpeed = 10;
    }
    if ( bossBulletX < - bossBulletWidth) {
      active = false;
    }

    if ( bossBulletY < playerY) {
      bossBulletY += bossBulletAim;
    }
    if ( bossBulletY > playerY) {
      bossBulletY -= bossBulletAim;
    }
    if ((bossBulletX - bossBulletWidth/2) <= (playerX + playerWidth) 
      && (bossBulletX + bossBulletWidth/2) >= (playerX - playerWidth)
      && (bossBulletY - bossBulletWidth/2) <= (playerY + playerHeight/2) 
      && (bossBulletY + bossBulletWidth/2) >= (playerY - playerHeight/2) ) {
      bHit.rewind();
      bHit.play();
      life -= 5;
      active = false;
    }
  }
}
boolean splashReset = true;

// spower up classes 
class SpeedPup {

  PVector pupLoc;
  float size, speed;


  SpeedPup(float x, float y, float size, float speed) {

    pupLoc = new PVector(x, y);
    this.size=size;
    this.speed = speed;
  }
  public void update() {

    pupLoc.x -= speed;
  }
  boolean change = true;
  public void render() {
    for (int i =1; i < 8; i++) {
      if (change == true) {
        fill(0, 0, 255);
      } else {
        fill(255);
      }
      ellipse(pupLoc.x, pupLoc.y, size/i, size/1);
      change = !change;
    }
  }
  public void onCollsionEnter2D() {

    if ( pupLoc.x < - size/2) {
      //size= (int)random(25, 50);
      speed=(int)random(2, 8);
      pupLoc =new PVector(random(width + size/2, width*2), random(hudCenterX +(hudHeight+size/2)));
    }

    if (pupLoc.x - size/2 < playerX + ((playerWidth/2 + playerCPit/2)*1.35f) 
      &&  pupLoc.x + size/2 > playerX - playerWidth/2 
      && pupLoc.y +  size/2 > playerY-playerHeight*3 
      && pupLoc.y - size/2 < playerY + playerHeight/2) {
      enemySpeed -= 4.0f;
      Pup.rewind();
      Pup.play();
      //size= (int)random(25, 50);
      speed=(int)random(5, 8);
      pupLoc =new PVector(random(width + size/2, width*2), random(hudHeight+size/2, height - size/2));
    }
  }
}
class HealthPup {

  PVector pupLoc;
  float size, speed;


  HealthPup(float x, float y, float size, float speed) {

    pupLoc = new PVector(x, y);
    this.size=size;
    this.speed = speed;
  }
  public void update() {

    pupLoc.x -= speed;
  }
  boolean change = true;
  public void render() {
    for (int i =1; i < 8; i++) {
      if (change == true) {
        fill(0, 255, 0);
      } else {
        fill(255, 0, 0);
      }
      ellipse(pupLoc.x, pupLoc.y, size/i, size/1);
      change = !change;
    }
  }
  public void onCollsionEnter2D() {

    if ( pupLoc.x < - size/2) {
      //size= (int)random(25, 50);
      speed=(int)random(2, 8);
      pupLoc =new PVector(random(width + size/2, width*4), random(hudCenterX +(hudHeight+size/2)));
    }

    if (pupLoc.x - size/2 < playerX + ((playerWidth/2 + playerCPit/2)*1.35f) 
      &&  pupLoc.x + size/2 > playerX - playerWidth/2 
      && pupLoc.y +  size/2 > playerY-playerHeight*3 
      && pupLoc.y - size/2 < playerY + playerHeight/2) {
      life +=10;
      //size= (int)random(25, 50);
      Pup.rewind();
      Pup.play();
      speed=(int)random(5, 8);
      pupLoc =new PVector(random(width + size/2, width*4), random(hudHeight+size/2, height - size/2));
    }
  }
}
class EExpl {

  int counter=0;
  PVector explLoc = new PVector();

  EExpl(float x, float y) {

    explLoc = new PVector(x, y);
  }

  public void update() { 

    if (counter < 2 
      && explLoc.x < width
      && explLoc.x > 0) {
      counter ++;
      image(boom[2-counter], explLoc.x, explLoc.y);
    }
    if (counter >= 2) {
      counter =0; 
      explLoc = new PVector (width*2, height/2);
    }
  }
}
// this is the set up and use of the socre bord 
String[] names = {"IGY", "SNW", "BLK"};
int[] scores = {16000, 10000, 5000};
String nameHolder = "Test";
String userInput = "AAA";
String moveName = "AAA";
String title = "LEADERBOARD", restart = "PRESS M FOR MAIN MENU";
int nameT = 0, scoreHolder;
boolean check = true;
float textWidth, textHeight;
// desplays the keybord 
public void leaderBord() {
  leaderBordBg();
  textAlign(CENTER);
  textFont(secondFont);
  textSize(50);
 
  leaderBordCheck();
  
  fill(0, random(100, 200), 0);

  for ( int i = 0; i < names.length; i++) {

    text(names[i] + ":" + scores[i], textWidth, textHeight );
    textHeight += 100;
  }
  textHeight = 200;
   fill(70, 173, 212);
   text(title, textMidX, textMidY - textMidY* 0.7f);
   textSize(20);
   text(restart, textMidX, textMidY + textMidY* 0.7f);
}
// updates the leader bord 
public void leaderBordCheck() {
  check =false;
  moveName = userInput;

  if (score > scores[0]) {
    scoreHolder = scores[0];
    scores[0] = score;
    score = scoreHolder;
    nameHolder = names[0];
    names[0] = moveName;
    moveName = nameHolder;
  }
  if (score > scores[1]) {
    scoreHolder = scores[1];
    scores[1] = score;
    score = scoreHolder;
    nameHolder = names[1];
    names[1] = moveName;
    moveName = nameHolder;
  }
  if (score > scores[2]) {
    scoreHolder = scores[2];
    scores[2] = score;
    score = scoreHolder;
    nameHolder = names[2];
    names[2] = moveName;
    moveName = nameHolder;
  }
  score =0;
}
// this is the background for the leader bord
int boxNo= 50; 
float bBoxHeight, bBoxWidth; 
float[] boxX= new float[boxNo];
float [] boxY= new float[boxNo];
public void leaderBordBg(){
  
rectMode(CORNER);
background(0);
noStroke();
  for(int i=0; i<boxX.length; i++){
    
    fill(0,random(150,200),0);
    rect(boxX[i], boxY[i],bBoxHeight, bBoxWidth);
    fill(0,random(100,150),0);
    rect(boxX[i], boxY[i]-bBoxHeight*1.4f,bBoxHeight, bBoxWidth);
    fill(0,random(50,100),0);
    rect(boxX[i], boxY[i]-bBoxHeight*3.0f,bBoxHeight, bBoxWidth);
    boxY[i] ++;
    
    if(boxY[i] > height + bBoxHeight){
      
      boxY[i] = - bBoxHeight;
      
    }
  }
  rectMode(CENTER);
  fill(0);
  rect(textMidX,textMidY, width/2, height);
  
  
// changes the level back to the main screen 
  if (checkKey('m')) {
    check =false;
    level = 0;
  }

  
  
}
// runs level one
public void levelOne() {
  textAlign(LEFT);

  drawBackground();
  sPup.render();
  sPup.update();
  sPup.onCollsionEnter2D();
  hPup.render();
  hPup.update();
  hPup.onCollsionEnter2D();
  for( int i =0; i < enemyExpl.length; i++){
    enemyExpl[i].update();
  }
  player();
  playerShot();
  enemy();
  hud();

// changes to faster then light trivle 
  if (second < 10 && clock == 0) {
    enemySpawn = false;
    ftl =true;
  }


  // Controles 
  if (checkKey('w')) {
    playerY -= playerSpeed;
  }
  if (checkKey('s')) {
    playerY += playerSpeed;
  }
  if (checkKey('a')) {
    playerX -= playerSpeed;
  }
  if (checkKey('d')) {
    playerX += playerSpeed;
  }
  if (checkKey(' ')) {
    playerShoot();
  }
}
// runs level 2 code ( the boss fight)
public void levelTwo() {


  textAlign(LEFT);
  drawBackground();
  player();
  playerShot();
  boss();
  bossBullet();
  hud();

  if (checkKey('w')) {
    playerY -= playerSpeed;
  }
  if (checkKey('s')) {
    playerY += playerSpeed;
  }
  if (checkKey('a')) {
    playerX -= playerSpeed;
  }
  if (checkKey('d')) {
    playerX += playerSpeed;
  }
  if (checkKey(' ')) {
    playerShoot();
  }
}
// run the code for the player to imput there name
int boxCount = 3;
float[] boxPosX = new float[boxCount];
float boxPosY;
float boxHeight = 75, boxWidth=50, boxShrink = 10;
int playerFill=0;
boolean canPress = true, typing =true;
char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
int[] playerLetterChoise = {0, 0, 0};
char[] playerLetter = {'A', 'A', 'A'};
//float ringCount = 26, ringMaxX, ringMaxY, ringX, ringY, sizeDownX, sizeDownY;
//boolean change = true;
//float ballX=ringX, ballY=ringY, ballSize = 50, ballSpeed= 5;
float bgBoxX, bgBoxY, bgBoxWidth, bgBoxHeight;
String useArrows = "USE ARROW KEYS TO ENTER NAME", cToSet = "PRESS C TO CONFORM";


// desplay for the leader bord set up so that the player can see what they are doing 

public void leaderBordScreen() {
  background(0);
  noFill();
  stroke(0, 255, 0);
  rect(bgBoxX, bgBoxY, bgBoxWidth*1.5f, bgBoxHeight*1.5f);
  rect(bgBoxX, bgBoxY, bgBoxWidth, bgBoxHeight);
  line(bgBoxX, bgBoxY, 0, 0);
  line(bgBoxX, bgBoxY, width, 0);
  line(bgBoxX, bgBoxY, 0, height);
  line(bgBoxX, bgBoxY, width, height);
  fill(0);
  rect(bgBoxX, bgBoxY, bgBoxWidth/2, bgBoxHeight/2);
  textFont(secondFont);
  textSize(30);
  fill(70, 173, 212);
  text(useArrows, bgBoxX, bgBoxY - bgBoxY * 0.6f);
  text(cToSet, bgBoxX, bgBoxY + bgBoxY * 0.4f);






  textAlign(CENTER);

  /*for (int i =0; i < balls.length; i++){
   
   balls[i]. render();
   balls[i]. OnCollisionEnter2D();
   
   }*/

  for (int i=0; i < boxPosX.length; i++) {
    fill(0, random(150, 250), 0);
    rect(boxPosX[i], boxPosY, boxWidth, boxHeight);
    triangle(boxPosX[i] - boxWidth/2, boxPosY -(boxHeight/2 + boxShrink/2), boxPosX[i] + boxWidth/2, boxPosY -(boxHeight/2 + boxShrink/2), boxPosX[i], boxPosY -(boxHeight/2 + boxShrink*3));
    triangle(boxPosX[i] - boxWidth/2, boxPosY +(boxHeight/2 + boxShrink/2), boxPosX[i] + boxWidth/2, boxPosY +(boxHeight/2 + boxShrink/2), boxPosX[i], boxPosY +(boxHeight/2 + boxShrink*3));
    fill(0);
    rect(boxPosX[i], boxPosY, boxWidth - boxShrink, boxHeight-boxShrink);
    fill(255);
    textSize(50);
    text(playerLetter[i], boxPosX[i], boxPosY + boxHeight *.2f );
  }
  fill(0, 0, random(150, 250));
  rect(boxPosX[playerFill], boxPosY, boxWidth, boxHeight);
  triangle(boxPosX[playerFill] - boxWidth/2, boxPosY -(boxHeight/2 + boxShrink/2), boxPosX[playerFill] + boxWidth/2, boxPosY -(boxHeight/2 + boxShrink/2), boxPosX[playerFill], boxPosY -(boxHeight/2 + boxShrink*3));
  triangle(boxPosX[playerFill] - boxWidth/2, boxPosY +(boxHeight/2 + boxShrink/2), boxPosX[playerFill] + boxWidth/2, boxPosY +(boxHeight/2 + boxShrink/2), boxPosX[playerFill], boxPosY +(boxHeight/2 + boxShrink*3));
  fill(0);
  rect(boxPosX[playerFill], boxPosY, boxWidth - boxShrink, boxHeight-boxShrink);
  fill(255);
  text(alphabet[playerLetterChoise[playerFill]], boxPosX[playerFill], boxPosY + boxHeight *.2f );

  if (keyPressed && canPress == true) {
    if (keyCode == RIGHT) {
      pClick.rewind();
      pClick.play();
      playerFill ++;

      if (playerFill > 2) {

        playerFill =0;
      }
      if (playerFill < 0) {
        playerFill=2;
      }
      playerLetter[playerFill] = alphabet[playerLetterChoise[playerFill]];

      canPress = false;
    }
    if (keyCode ==LEFT) {
      pClick.rewind();
      pClick.play();
      playerFill --;


      if (playerFill > 2) {

        playerFill =0;
      }
      if (playerFill < 0) {
        playerFill=2;
      } 
      playerLetter[playerFill] = alphabet[playerLetterChoise[playerFill]];
      canPress = false;
    }
    if (keyCode == UP) {
      pClick.rewind();
      pClick.play();
      playerLetterChoise[playerFill] ++;
      if (playerLetterChoise[playerFill] > 25) {
        playerLetterChoise[playerFill] =0;
      }
      if (playerLetterChoise[playerFill] < 0) {
        playerLetterChoise[playerFill] =25;
      }
      playerLetter[playerFill] = alphabet[playerLetterChoise[playerFill]];
      canPress = false;
    }
    if (keyCode ==DOWN) {
      pClick.rewind();
      pClick.play();
      playerLetterChoise [playerFill]--;
      if (playerLetterChoise[playerFill] > 25) {
        playerLetterChoise[playerFill] =0;
      }
      if (playerLetterChoise[playerFill] < 0) {
        playerLetterChoise[playerFill] =25;
      }
      playerLetter[playerFill] = alphabet[playerLetterChoise[playerFill]];
      canPress = false;
    }
    if (checkKey('c') && canType ==true) {

      canType= false;
    }
    //println(canType);
  }
}
// resets the games varabels for the next go around tells the game if the player socred enough to enter there name or not 
public void splash() {


  if ( splashReset == true) {
    splashReset =false;
    bossHealth = 100;
    clock =1;
    life =100;
    second =30;
    enemySpawn = true;
    bossX = width  +( bossWidth* 1.5f);
    bossY = height/2;
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

    for (int i =0; i < bulletX.length; i++) {
      bulletX[i] = width + bulletWidth;
    }
  }
  if (score > scores[2]) {
    leaderBordScreen();
  } else {
    level = 4;
  }


  if (checkKey('c')) {
    pClick.rewind();
    pClick.play();
    userInput = "" + alphabet[playerLetterChoise[0]] + alphabet[playerLetterChoise[1]] + alphabet[playerLetterChoise[2]];
    level = 4;
  }
}
/*

 This game is baced on R-TYPE as a nod to this I followed the 
 main splah screen for R-TYPE as an eater egg
 
 */
String gameTitle = "D-TYPE", playerPartOne = "BLAST OFF AND STRIKE", playerPartTwo = "THE EVIL RATTA EMPIRE!", pToP= " PRESS P TO PLAY";
String playerOnePlay="1 PLAYER = 1 COIN(S)", playerTwoPlay="2 PLAYER = 2 COIN(S)", coin = "INSERT COIN", cred = "CREDIT 00", otherInfo = "\u00a9    2017      BY       IGGY      CORP.";
String howTo = "HOW TO PLAY", wasd="WASD TO MOVE", space ="SPACE TO FIRE";
public void mainMenu() {

  background(0);
  textFont(mainFont);
  textSize(150);
  fill(255);
  text(gameTitle, textMidX, textMidY + (textMidY * 0.6f));
  textSize(153);
  fill(185);
  text(gameTitle, textMidX, textMidY + (textMidY * 0.6f));
  textFont(secondFont);
  textSize(20);
  fill(70, 173, 212);
  text(playerPartOne, textMidX + (textMidX* 0.6f), textMidY - (textMidY * 0.8f));
  text(playerPartTwo, textMidX + (textMidX* 0.62f), textMidY - (textMidY * 0.7f));
  text(playerOnePlay, textMidX + (textMidX* 0.6f), textMidY - (textMidY * 0.5f));
  text(playerTwoPlay, textMidX + (textMidX* 0.6f), textMidY - (textMidY * 0.4f));
  fill(212, 70, 173);
  text(coin, textMidX + (textMidX* 0.6f), textMidY - (textMidY * 0.2f));
  fill(70, 173, 212);
  text(pToP, textMidX, textMidY + (textMidY * 0.2f));
  text(cred, textMidX + (textMidX* 0.6f), textMidY);
  text(otherInfo, textMidX + (textMidX* 0.6f), textMidY + (textMidY * 0.8f));
  textSize(30);
  text(howTo, textMidX - textMidX*0.7f, textMidY - (textMidY * 0.7f));
  textSize(31);
  fill(70, 173, 225);
  text(howTo, textMidX - textMidX*0.7f, textMidY - (textMidY * 0.7f));
  fill(70, 173, 212);
  textSize(20);
  text(wasd, textMidX - textMidX*0.7f, textMidY - (textMidY * 0.5f));
  text(space, textMidX - textMidX*0.7f, textMidY - (textMidY * 0.6f));
  
  if (checkKey('p')) {
    pClick.rewind();
    pClick.play();
    level = 1;
  }
}
  public void settings() {  size(960, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Space_shooter" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
