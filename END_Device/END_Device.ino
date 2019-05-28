#include <SoftwareSerial.h>
#include <Stepper.h>
#include <SoftReset.h>

//TODO delete [TEST] tags, when Final develop version.

const int stepsPerRevolution=2048;
int pos_spin =0;
int pos_horizon =0;
    
SoftwareSerial xbee(2, 3);   
Stepper myStepper_spin(stepsPerRevolution, 11,9,10,8);
Stepper myStepper_push(stepsPerRevolution, 7,5,6,4);
Stepper myStepper_horizon(stepsPerRevolution, A3,A1,A2,A0);

void setup() {  
  Serial.begin(9600);
  xbee.begin(9600);
  myStepper_spin.setSpeed(10);
  myStepper_horizon.setSpeed(10);
  myStepper_push.setSpeed(10);

}

void loop() {
  if(/*[TEST] Serial*/xbee.available()){
    pos_spin =0;
    pos_horizon =0;  
    
    String inString = xbee.readStringUntil('\n'); //[TEST] Serial.readStringUntil('\n');
    //Serial.print(inString);
    
    
    String origin = inString; 
    char key_first =',';
    
    int get_index = 0;
    String tmp = "";
    String cp = origin;
    

    while(true){
      get_index = cp.indexOf(key_first);
      if(-1 != get_index){
        tmp=cp.substring(0,get_index);
        movement(tmp);
        cp = cp.substring(get_index+1);
      }
      
      else{
        movement(cp);
        break;
      }
    }
    movement_init();
    //xbee.write('A');
    soft_restart();
  }
}

void movement(String a){
  int data = 0;
  if(a[0] == 'S'){
    a = a.substring(1);
    data = a.toInt();
    data = data * 56; // '56' is 10degree
    movement_spin(data);
    pos_spin = data;
  }
  
  else if(a[0] == 'P'){
    a = a.substring(1);
    data = a.toInt();
    if(data == 1){ //just Push, init() function is auto recovery Pull
      data = -2048;
      movement_push(data);
    }
  }
  
  else if(a[0] == 'H'){
    a = a.substring(1);
    data = a.toInt();
    
    if(data == 0){
      data = -2048;
      movement_horizon(data);
      pos_horizon = data;
    }
    else{
      data = 2048;
      movement_horizon(data);
      pos_horizon = data;
    }
  }
}

//Moter spin
void movement_spin(int value){
  int angle = value;
  myStepper_spin.step(angle);
  //delay(200);
}
//Moter horizon
void movement_horizon(int value){
  int pos = value;
  myStepper_horizon.step(pos);
  //delay(200);
}
//Moter push
void movement_push(int value){
  int pos = value;
  myStepper_push.step(pos);
  //delay(200);
}
//init function
void movement_init(){
  //TODO find & put in current initialization position values
  int tmp_spin = -1*(pos_spin); 
  int tmp_horizon = -1*(pos_horizon);
  int tmp_push = 2048; //mean 0, pull status
  myStepper_push.step(tmp_push);
  myStepper_spin.step(tmp_spin);
  myStepper_horizon.step(tmp_horizon);
  delay(50);
}
