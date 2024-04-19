
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <SPI.h>
#include <MFRC522.h>
#define SS_PIN D2  
#define RST_PIN D1  
MFRC522 mfrc522(SS_PIN, RST_PIN);  /

#define ON_Board_LED 2 
#define led 15
const char* ssid = "IUH";
const char* password = "12345678";
int readsuccess;
byte readcard[4];
char str[32] = "";
String StrUID;

HTTPClient http;
WiFiClient client;

void setup() {
  Serial.begin(115200);
  SPI.begin();      
  mfrc522.PCD_Init(); 

  delay(500);

  WiFi.begin(ssid, password); 
  Serial.println("");
    
  pinMode(ON_Board_LED,OUTPUT); 
  pinMode(led,OUTPUT); 
  digitalWrite(ON_Board_LED, HIGH);
  digitalWrite(led, LOW);
  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    digitalWrite(ON_Board_LED, LOW);
    digitalWrite(led, HIGH);
    delay(250);
    digitalWrite(ON_Board_LED, HIGH);
    digitalWrite(led, LOW);
    delay(250);
  }
  digitalWrite(ON_Board_LED, HIGH);
  Serial.println("");
  Serial.print("Successfully connected to : ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  readsuccess = getid();
 
  if(readsuccess) {  
  digitalWrite(ON_Board_LED, LOW);
  digitalWrite(led, HIGH);
    String UIDresultSend, postData;
    UIDresultSend = StrUID;
   
    
    postData = "UIDresult=" + UIDresultSend;

    
    http.begin(client, "http://192.168.245.111/HTQLNuoiHeo/getUID.php"); 
    http.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
   
    int httpCode = http.POST(postData);   
    String payload = http.getString();   
  
    Serial.println(UIDresultSend);
    Serial.println(httpCode);  
    Serial.println(payload);    
    
    http.end();  //Close connection
    delay(1000);
  digitalWrite(ON_Board_LED, HIGH);
   digitalWrite(led, LOW);
  }
}


int getid() {  
  if(!mfrc522.PICC_IsNewCardPresent()) {
    return 0;
  }
  if(!mfrc522.PICC_ReadCardSerial()) {
    return 0;
  }
 
  Serial.print("THE UID OF THE SCANNED CARD IS : ");
  
  for(int i=0;i<4;i++){
    readcard[i]=mfrc522.uid.uidByte[i];
    array_to_string(readcard, 4, str);
    StrUID = str;
  }
  mfrc522.PICC_HaltA();
  return 1;
}

void array_to_string(byte array[], unsigned int len, char buffer[]) {
    for (unsigned int i = 0; i < len; i++)
    {
        byte nib1 = (array[i] >> 4) & 0x0F;
        byte nib2 = (array[i] >> 0) & 0x0F;
        buffer[i*2+0] = nib1  < 0xA ? '0' + nib1  : 'A' + nib1  - 0xA;
        buffer[i*2+1] = nib2  < 0xA ? '0' + nib2  : 'A' + nib2  - 0xA;
    }
    buffer[len*2] = '\0';
}