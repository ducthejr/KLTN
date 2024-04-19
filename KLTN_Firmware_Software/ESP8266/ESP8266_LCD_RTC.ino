#include <Wire.h>
#include <Servo.h>
#include <SPI.h> 
#include "RTClib.h"
#include <LiquidCrystal_I2C.h>
#include <ESP8266WiFi.h>
#include "FirebaseESP8266.h"
#define WIFI_SSID "IUH"
#define WIFI_PASSWORD "12345678"
#define FIREBASE_AUTH "KnQtC3ULvP81lQxyN5trNohZB6pPciZaQlCTdJq3"
#define FIREBASE_HOST "demokhoaluan-d5b24-default-rtdb.asia-southeast1.firebasedatabase.app"
RTC_DS1307 RTC;
Servo msv;
LiquidCrystal_I2C lcd(0x27,16,2); 
FirebaseData firebaseData;
String path = "/";
FirebaseJson json;
int sw;
int year;
int month;
int day;
int hr;
int mn;
int sc;
float hum;
float temp;
float weight;
int WL;
int controlState;
int lamp;
int fan;
int pump;
int servo_;
int light;
void setup()
{
  Serial.begin(115200);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  if (!Firebase.beginStream(firebaseData, path)) {
    Serial.println("REASON:" + firebaseData.errorReason());
    Serial.println();
  }
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();
  if (Firebase.getInt(firebaseData, path + "/year")) year = firebaseData.intData();
  if (Firebase.getInt(firebaseData, path + "/month")) month = firebaseData.intData();
  if (Firebase.getInt(firebaseData, path + "/day")) day = firebaseData.intData();
  if (Firebase.getInt(firebaseData, path + "/hr")) hr = firebaseData.intData();
  if (Firebase.getInt(firebaseData, path + "/mn")) mn = firebaseData.intData();
  if (Firebase.getInt(firebaseData, path + "/sc")) sc = firebaseData.intData();

  lcd.init();      
  Wire.begin();
  RTC.begin();              
  lcd.backlight();
  msv.attach(D4);
  pinMode(D3, INPUT_PULLUP);
  RTC.adjust(DateTime(year,month,day,hr,mn,sc)); 
  Serial.print("Time and date");
  Serial.print(year);
  lcd.clear();
  

}

void loop()
{ 
  aS();
  if (Firebase.getInt(firebaseData, path + "/relayState")) controlState = firebaseData.intData();
  if(controlState == 1){
    autoControl();
  } else manual();
  LCD();
  
  DateTime now = RTC.now();
 
  Serial.print(now.year(), DEC);
  Serial.print('/');
  Serial.print(now.month(), DEC);
  Serial.print('/');
  Serial.print(now.day(), DEC);
  Serial.print(' ');
  Serial.print(now.hour(), DEC);
  Serial.print(':');
  Serial.print(now.minute(), DEC);
  Serial.print(':');
  Serial.print(now.second(), DEC);
  Serial.println();
  delay(1000);
}
void LCD(){
  if (Firebase.getFloat(firebaseData, path + "/nhietdo")) temp = firebaseData.floatData();
  if (Firebase.getFloat(firebaseData, path + "/doam")) hum = firebaseData.floatData();
  if (Firebase.getFloat(firebaseData, path + "/weight")) weight = firebaseData.floatData();
  if (Firebase.getInt(firebaseData, path + "/water")) WL = firebaseData.intData();
  Serial.println(temp);
  Serial.println(hum);
  Serial.println(weight);
  Serial.println(WL);
  
  lcd.setCursor(0, 0);
  lcd.print("ND: ");
  lcd.setCursor(3, 0);
  lcd.print(temp);
  lcd.setCursor(9, 0);
  lcd.print("DA: ");
  lcd.setCursor(12, 0);
  lcd.print(hum);
  lcd.setCursor(0, 1);
  lcd.print("WL: ");
  lcd.setCursor(3, 1);
  lcd.print(WL);
  lcd.setCursor(7, 1);
  lcd.print("We: ");
  lcd.setCursor(11, 1);
  lcd.print(weight);
  delay(1000);
  lcd.clear();
}
void autoControl(){
 
  if(light == 1){
    lamp = 0;
  }else lamp = 1;
  Firebase.setInt(firebaseData,path + "/den",lamp);

  if(temp>=38){
    fan = 0;
  }else fan = 1;
  Firebase.setInt(firebaseData,path + "/quat",fan);
  
  if(WL <= 10){
    pump = 0;
  }else if(WL>=80){
    pump = 1;
  }
  Firebase.setInt(firebaseData,path + "/maybom",pump);

  if(weight <= 0.01){
    msv.write(180);
    servo_ = 0;
  }else if(weight >= 0.2 ){
    msv.write(0);
    servo_ = 1;
  }
  Firebase.setInt(firebaseData,path + "/servo",servo_);

 
}
void manual(){
  if (Firebase.getInt(firebaseData, path + "/servo")) servO = firebaseData.intData();
  if(servO == 0){
    msv.write(180);
  }else msv.write(0);
}
void aS(){
  Firebase.setInt(firebaseData,path + "/AS",light);
  light = digitalRead(D5);
  Serial.print("AS: ");
  Serial.println(light);
  delay(200);
}