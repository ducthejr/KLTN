
#include <DHT.h>
#include <SoftwareSerial.h>
#include <ArduinoJson.h>
#include "HX711.h"
#define DHTPIN 2
#define trig 4
#define echo 3

SoftwareSerial nodemcu(6, 7);
HX711 scale(A1, A0);
float weight;
float calibration_factor = 103525.00;
int enB = 13;
int in3 = 12;
int in4 = 11;
int sw1 = 10;
int sw2 = 9;
int state1;
int state2;
int rainS = 8;
int rainV;
DHT dht(DHTPIN, DHT11);
float temp;
float hum;
unsigned long duration;
int distance;
int water;
void setup() {
  Serial.begin(9600);
  nodemcu.begin(9600);
  dht.begin();  
  pinMode(enB, OUTPUT);
  pinMode(in3, OUTPUT);
  pinMode(in4, OUTPUT);
  pinMode(sw1, INPUT_PULLUP);
  pinMode(sw2, INPUT_PULLUP);
  digitalWrite(in3,LOW);
  digitalWrite(in4,LOW);
}

void loop() {

  StaticJsonBuffer<1000> jsonBuffer;
  JsonObject& data = jsonBuffer.createObject();

  dht11_func();
  sr04Sensor();
  raiN();

  data["humidity"] = hum;
  data["temperature"] = temp; 
  data["weight"] = weight;
  data["water"] = water;
  data["rainV"] = rainV;

  //Send data to NodeMCU
  data.printTo(nodemcu);
  jsonBuffer.clear();

  delay(2000);
}

void dht11_func() {

  hum = dht.readHumidity();
  temp = dht.readTemperature();
  Serial.print("Humidity: ");
  Serial.println(hum);
  Serial.print("Temperature: ");
  Serial.println(temp);
}
void sr04Sensor() {
    digitalWrite(trig,0);  
    delayMicroseconds(2);
    digitalWrite(trig,1);   
    delayMicroseconds(5);   
    digitalWrite(trig,0);   
    
    duration = pulseIn(echo,HIGH);  
    distance = int(duration/2/29.412);
    water = distance;
    Serial.println(distance);
}
void KL(){
  scale.set_scale(calibration_factor);
 
  weight = scale.get_units(5); 
  Serial.print("Weight: ");
  Serial.print(weight);
  Serial.println(" KG");
  Serial.println();
}
void raiN(){
  sw();
  rainV = digitalRead(rainS);
  if(rainV == 0 && state2!=0){
    thuan();
  }
  else if(rainV == 1 && state1!=0){
    nghich();
  }else stop();
}
void thuan(){
  analogWrite(enB,255);
  digitalWrite(in3,HIGH);
  digitalWrite(in4,LOW);
  Serial.println("quay thuan");
}
void nghich(){
  analogWrite(enB,255);
  digitalWrite(in4,HIGH);
  digitalWrite(in3,LOW);
  Serial.println("quay nghich");
}
void stop(){
  digitalWrite(in4,LOW);
  digitalWrite(in3,LOW);
  Serial.println("stop");
}
void sw(){
    state1 = digitalRead(sw1);
    state2 = digitalRead(sw2);
    Serial.println(state1);
    Serial.println(state2);
}