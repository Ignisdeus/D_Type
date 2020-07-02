// background setup;
int starCount = 1000;
float[] stars = new float[starCount];
float[] starsX = new float[starCount];
float[] starsY = new float[starCount];
float[] starsSize = new float[starCount];
float starsSpeed = 1.0f;
boolean bossFightActive = true;
boolean ftl = false;

void drawBackground() {

  if (ftl == true && starsSpeed < 15.0f) {
    starsSpeed += 0.05;
  }
  if (ftl == false && starsSpeed > 1.0f) {
    starsSpeed -= 0.05;
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
      starsSize[i] = random(0.5, 2);
      starsX[i] = width + starsSize[i];
    }
  }
}
// player set up 
float playerX, playerY, playerSpeed;
float playerWidth = 75, playerHeight= 12, playerScore, playerCPit = 20, pYFix = 6.25;


void player() {  

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
  triangle(playerX + ((playerWidth/2 + playerCPit/2)-1), playerY +  pYFix, playerX + ((playerWidth/2 + playerCPit/2)-1), playerY - pYFix-8.75, playerX + ((playerWidth/2 + playerCPit/2)*1.35), playerY + pYFix);
  // Tail End 
  rect(playerX- playerWidth/3, playerY - playerHeight, playerWidth/3, playerHeight*2);
  fill(120);
  rect(playerX- playerWidth/3, playerY - playerHeight*1.8, playerWidth/3, playerHeight/2);
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

void playerShoot() {

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
void playerShot() {
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


void enemy() {
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
        enemySpeed += 0.5;
        score += 100;
      }
    }

    if (enemyX[i] - enemyHeight* 1.5f < playerX + ((playerWidth/2 + playerCPit/2)*1.35) 
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
void hud() {

  fill(0, 206, 209);
  rect(hudCenterX, hudCenterY, width, hudHeight*1.2);
  rect(hudCenterX/2, hudCenterY, width/3, hudHeight *1.7);
  triangle(hudCenterX/2-(width/3)/2, 0, hudCenterX/2-(width/3)/2, hudHeight *1.35, (hudCenterX/2-(width/3)/2) - 100, 0 );
  triangle(hudCenterX/2+(width/3)/2, 0, hudCenterX/2+(width/3)/2, hudHeight *1.35, (hudCenterX/2+(width/3)/2) + 100, 0 );

  fill(0, 2, 46);
  rect(hudCenterX, hudCenterY, width, hudHeight);
  rect(hudCenterX/2, hudCenterY, width/3, hudHeight *1.5);
  triangle(hudCenterX/2-(width/3)/2, 0, hudCenterX/2-(width/3)/2, hudHeight *1.25, (hudCenterX/2-(width/3)/2) - 100, 0 );
  triangle(hudCenterX/2+(width/3)/2, 0, hudCenterX/2+(width/3)/2, hudHeight *1.25, (hudCenterX/2+(width/3)/2) + 100, 0 );
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
void lifeBar() {

  if (life > 100) {
    life = 100;
  }
  lifeBarWith = life*3;
  rectMode(CORNER);
  fill(0, 206, 209);
  rect(lifeBarX -hpHolder * 0.02, hudCenterY, hpHolder * 1.04, lifeBarHeight* 1.4);

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
    burn -= 0.5;
    life -= 0.5;
  }
  if ( Healthpup > 0) {
    Healthpup--;
    ;
    life ++;
  }
}
// score desplay
String scoreT = "Score: ";
void score() {
  fill(255);
  textSize(24);
  text(scoreT + score, hudCenterX, hudCenterY * 1.2f );
}
// clock set up 
int clock = 1, second = 15, frame =0;
void timer() {

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

void boss() {


  if ( bossX > (width - bossWidth *1.5f)|| bossX < (hudHeight + bossWidth *1.5f) ) {

    bossX -= bossJump;
  }

  moveTimer ++;

  if ( moveTimer > random(10.0, 60.0)) {
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
    triangle(bossX - bossWidth/2, bossY, bossX - ((bossWidth/2) * 0.75), bossY, bossX, bossY - bossHeight );
    fill(205, 155, 0);
    triangle(bossX + bossWidth/2, bossY, bossX + ((bossWidth/2) * 0.75), bossY, bossX, bossY - bossHeight );
  }
  if ( bossHit == true) {
    fill(255, 0, 0);
    ellipse(bossX, bossY, bossWidth * 1.5f, bossHeight);
    triangle(bossX - bossWidth/2, bossY, bossX + bossWidth/2, bossY, bossX, bossY - bossHeight );
    triangle(bossX - bossWidth/2, bossY, bossX - ((bossWidth/2) * 0.75), bossY, bossX, bossY - bossHeight );
    triangle(bossX + bossWidth/2, bossY, bossX + ((bossWidth/2) * 0.75), bossY, bossX, bossY - bossHeight );
  }
  if (active == false) {
    active = true;
    bossBulletX = bossX - bossWidth/2;
    bossBulletY = bossY;
  }
}
// boss hud set up 
float bossHpBarX, bossHealth = 100, bossHpBackground, bossBurn=0; 

void bossHud() {


  rectMode(CORNER);
  fill(0, 206, 209);
  rect(bossHpBarX -4, hudCenterY -1, bossHpBackground * 1.05f, lifeBarHeight * 1.2f);
  fill(0, 2, 46);
  rect(bossHpBarX, hudCenterY, bossHpBackground, lifeBarHeight);
  fill(255, 0, 0);
  rect(bossHpBarX, hudCenterY, bossHealth *2, lifeBarHeight);



  // This is done for the effect 
  if ( bossBurn > 0) {
    bossBurn -= 0.2;
    bossHealth -= 0.2;
  }
  rectMode(CENTER);
}

// boss combat set up 
float bossBulletX, bossBulletY, bossBulletSpeed, bossBulletWidth = 25, bossBulletAim = 5;
boolean active = false; 

void bossBullet() {


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
  void update() {

    pupLoc.x -= speed;
  }
  boolean change = true;
  void render() {
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
  void onCollsionEnter2D() {

    if ( pupLoc.x < - size/2) {
      //size= (int)random(25, 50);
      speed=(int)random(2, 8);
      pupLoc =new PVector(random(width + size/2, width*2), random(hudCenterX +(hudHeight+size/2)));
    }

    if (pupLoc.x - size/2 < playerX + ((playerWidth/2 + playerCPit/2)*1.35) 
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
  void update() {

    pupLoc.x -= speed;
  }
  boolean change = true;
  void render() {
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
  void onCollsionEnter2D() {

    if ( pupLoc.x < - size/2) {
      //size= (int)random(25, 50);
      speed=(int)random(2, 8);
      pupLoc =new PVector(random(width + size/2, width*4), random(hudCenterX +(hudHeight+size/2)));
    }

    if (pupLoc.x - size/2 < playerX + ((playerWidth/2 + playerCPit/2)*1.35) 
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

  void update() { 

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