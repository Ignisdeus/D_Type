// runs level one
void levelOne() {
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
void levelTwo() {


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

void leaderBordScreen() {
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
    text(playerLetter[i], boxPosX[i], boxPosY + boxHeight *.2 );
  }
  fill(0, 0, random(150, 250));
  rect(boxPosX[playerFill], boxPosY, boxWidth, boxHeight);
  triangle(boxPosX[playerFill] - boxWidth/2, boxPosY -(boxHeight/2 + boxShrink/2), boxPosX[playerFill] + boxWidth/2, boxPosY -(boxHeight/2 + boxShrink/2), boxPosX[playerFill], boxPosY -(boxHeight/2 + boxShrink*3));
  triangle(boxPosX[playerFill] - boxWidth/2, boxPosY +(boxHeight/2 + boxShrink/2), boxPosX[playerFill] + boxWidth/2, boxPosY +(boxHeight/2 + boxShrink/2), boxPosX[playerFill], boxPosY +(boxHeight/2 + boxShrink*3));
  fill(0);
  rect(boxPosX[playerFill], boxPosY, boxWidth - boxShrink, boxHeight-boxShrink);
  fill(255);
  text(alphabet[playerLetterChoise[playerFill]], boxPosX[playerFill], boxPosY + boxHeight *.2 );

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
void splash() {


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
String playerOnePlay="1 PLAYER = 1 COIN(S)", playerTwoPlay="2 PLAYER = 2 COIN(S)", coin = "INSERT COIN", cred = "CREDIT 00", otherInfo = "©    2017      BY       IGGY      CORP.";
String howTo = "HOW TO PLAY", wasd="WASD TO MOVE", space ="SPACE TO FIRE";
void mainMenu() {

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