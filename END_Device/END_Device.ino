#include <SoftwareSerial.h>
#include <Servo.h>

Servo myservo;
int pos=0;
SoftwareSerial xbee(2, 3); 

void setup() {
  Serial.begin(9600);
  xbee.begin(9600);
  myservo.attach(8);
}

void loop() {
  if(xbee.available()){
    String inString = xbee.readStringUntil('\n');
    //Serial.print(inString);
    
    String origin = inString; 
    char key_first =',';
    int count =0;
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
      ++count;
    }
  }
}

void movement(String a){
  int data = 0;
  if(a[0] == 'S'){
    a = a.substring(1);
    data = a.toInt(); 
  }
  else if(a[0] == 'L'){
    data = 180; 
  }
  else if(a[0] == 'R'){
    data = 0; 
  }

  Serial.print("data: ");
  Serial.print(data);
  Serial.print("\n");
  
  myservo.write(data);
         
  delay(700);
}
