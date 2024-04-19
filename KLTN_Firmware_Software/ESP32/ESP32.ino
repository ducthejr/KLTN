#include <SoftwareSerial.h>
#include <ArduinoJson.h>
#include <WiFi.h>
#include "FirebaseESP32.h"
#define WIFI_SSID "IUH"
#define WIFI_PASSWORD "12345678"
#define FIREBASE_AUTH "KnQtC3ULvP81lQxyN5trNohZB6pPciZaQlCTdJq3"
#define FIREBASE_HOST "demokhoaluan-d5b24-default-rtdb.asia-southeast1.firebasedatabase.app"
//D6 = Rx & D5 = Tx
#define DEN 13
#define QUAT 27
#define SERVO 14
#define MAYBOM 12

SoftwareSerial nodemcu(16, 17);
Servo myServo;
FirebaseData firebaseData;
String path = "/";
FirebaseJson json; 

int lamp_state = 1;
int fan_state = 1;
int servo_state = 1;
int pump_state = 1;
float hum;
float temp;
float weight;
int controlState;
int water;
int rainV;
void setup() {
  Serial.begin(9600);
  nodemcu.begin(9600);
  pinMode(DEN, OUTPUT);
  pinMode(QUAT, OUTPUT);
  pinMode(SERVO, OUTPUT);
  pinMode(MAYBOM, OUTPUT);
   WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
   while (WiFi.status() != WL_CONNECTED)
   {
    Serial.print(".");
    delay(300);
   }
   Firebase.begin(FIREBASE_HOST,FIREBASE_AUTH);
   Firebase.reconnectWiFi(true);
   if(!Firebase.beginStream(firebaseData,path))
   {
    Serial.println("REASON:"+ firebaseData.errorReason());
    Serial.println();
   }
   Serial.print("Connected with IP: ");
   Serial.println(WiFi.localIP());
   Serial.println();
  while (!Serial) continue;
}

void loop() {
  
  StaticJsonBuffer<1000> jsonBuffer;
  JsonObject& data = jsonBuffer.parseObject(nodemcu);

  if (data == JsonObject::invalid()) {
    //Serial.println("Invalid Json Object");
    jsonBuffer.clear();
    return;
  }

  Serial.println("JSON Object Recieved");
  Serial.print("Recieved Humidity:  ");
  hum = data["humidity"];
   Firebase.setFloat(firebaseData,path + "/doam",hum);
  Serial.println(hum);
  Serial.print("Recieved Temperature:  ");
  temp = data["temperature"];
  Firebase.setFloat(firebaseData,path + "/nhietdo",temp);
  Serial.println(temp);
  Serial.print("Recieved Weight:  ");
  weight = data["weight"];
   Firebase.setFloat(firebaseData,path + "/weight",weight);
  Serial.println(weight);
  Serial.print("Recieved Water level:  ");
  water = data["water"];
  Firebase.setInt(firebaseData,path + "/water",water);
  Serial.println(water);
  Serial.print("Recieved rain:  ");
  rainV = data["rainV"];
  Firebase.setInt(firebaseData,path + "/TT",rainV);
  Serial.println(rainV);
  Serial.println("-----------------------------------------");
  if (Firebase.getInt(firebaseData, path + "/controlState")) controlState = firebaseData.intData();
  if(controlState==0){
    control_relay();
  }else{
    auto_control();
  }
  
}

void control_relay() {
  if (Firebase.getInt(firebaseData, path + "/den")) lamp_state = firebaseData.intData();
  digitalWrite(DEN,lamp_state);
  if (Firebase.getInt(firebaseData, path + "/quat")) fan_state = firebaseData.intData();
  digitalWrite(QUAT,fan_state);
  if (Firebase.getInt(firebaseData, path + "/servo")) servo_state = firebaseData.intData();
  digitalWrite(SERVO,servo_state);
  if(x3 == 0){
      myServo.write(180);
  }else myServo.write(0);
  if (Firebase.getInt(firebaseData, path + "/maybom")) pump_state = firebaseData.intData();
digitalWrite(MAYBOM,pump_state);
}