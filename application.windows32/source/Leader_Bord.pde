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
void leaderBord() {
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
void leaderBordCheck() {
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
void leaderBordBg(){
  
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