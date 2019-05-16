#include <SoftwareSerial.h>

SoftwareSerial xbee(2, 3); 
int btn1 = 5;
int btn2 = 6;
String val1 = "S180,S160,R,S130,S100,L,R,L,S90";
String val2 = "S180,S1,S170,S10,S160,S20,S150,S30,S140,S40,S130,S50,S120,S60,S110,S70,S100,S80,S90";
void setup() {
  Serial.begin(9600);
  xbee.begin(9600);
  pinMode(btn1, INPUT_PULLUP);
  pinMode(btn2, INPUT_PULLUP);
  Serial.println("Start!");
}
void loop() {
  
  if(digitalRead(btn1)==LOW){
    xbee.println(val1);
    Serial.println(val1);
    delay(1000);
  }
  if(digitalRead(btn2)==LOW){
    xbee.println(val2);
    Serial.println(val2);
    delay(1000);
  }
  
  if(Serial.available()){
    String inString = Serial.readStringUntil('\n');
    xbee.println(inString);
    Serial.print("Sent!: ");
    Serial.println(inString);
  }
}
