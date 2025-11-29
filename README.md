# Ardogame                              
![Description of image](https://miro.medium.com/v2/resize:fit:1032/1*OSbJK0NpxjmNXQEnQYVqmg.gif)
## Description
PANGDUINO:

Concept:
We created a small Arduino game where the Arduino handles all the game logic, including the movement of the bouncing object and the timing of the jumps.

The goal of the game is simple:
we have to move our hand over the ultrasonic sensor at the right moment to keep the object bouncing.
If our timing is correct, the object continues bouncing.
If we move too early or too late, the bounce fails and the game ends.

As the game progresses, the bouncing speed gradually increases, making the timing more challenging.
Our score is the number of successful bounces we achieve before the game ends.

It’s a fast and fun reaction game that tests precision and timing, with the Arduino controlling the logic, the ultrasonic sensor detecting our actions, and the phone screen displaying the game.

### 🏓 Arduino-Controlled Pong Game (Android Edition)
Play Pong on your phone using just your hand!

### 📌 About the Project

This project is a modern twist on the classic Pong game, now fully playable on Android devices.
The paddle is controlled by your hand via an ultrasonic sensor connected to an Arduino Uno, which communicates directly with your phone over USB serial.

✔ No keyboard or touchscreen needed

✔ Real-time hand tracking for smooth paddle control

✔ Educational project for Arduino and mobile app development


### 🎮 How It Works

Hand movement is detected by the HC-SR04 ultrasonic sensor.

Arduino Uno reads the distance and sends the value over USB serial to the Android phone.

The Android app reads the serial data and moves the paddle on screen in real time.

The game runs like classic Pong, including ball movement, collisions, and scoring.

### ✨ Features

🖐️ Hand-controlled paddle movement

📱 Runs directly on Android

🔌 USB Serial communication

🎨 Smooth graphics inside your app

🧪 Educational — ideal for learning Arduino, sensors, and Android development

🎓 Academic & educational license


### 🛠️ Hardware Required

Arduino Uno

HC-SR04 ultrasonic sensor

USB OTG cable (Arduino → Phone)

Jumper wires

Android phone

App installed (APK from your project)

### ⚡ Wiring Overview

<div align="center"> <img src="https://i0.wp.com/randomnerdtutorials.com/wp-content/uploads/2013/11/ultrasonic-sensor-with-arduino-hc-sr04.jpg?resize=828%2C386&quality=100&strip=all&ssl=1" width="500"> </div>

HC-SR04 → Arduino Uno

HC-SR04 Pin	Arduino Pin

VCC	5V

GND	GND

TRIG	D9

ECHO	D10

### 🧩 System Architecture

Hand → Ultrasonic Sensor → Arduino Uno → USB Serial → Android App → Paddle Movement

### 💻 Arduino Code (Uno)

### 📱 Android App Integration

The app reads serial data from Arduino Uno via USB OTG.

Paddle moves vertically according to hand distance.

Ball movement and collisions are handled inside the app.

⚠️ On first connection, the app may require USB permission to access the Arduino.

### 🔧 Setup Instructions (Phone)

Connect Arduino Uno to the Android phone via OTG cable.

Upload the Arduino code to the board.

Install your Android APK on the phone.

Open the app and grant USB permission.

Move your hand in front of the sensor to control the paddle!

### 👥 Team Members

#### Mebarki abdellah

#### Benmessaoud razik

#### Aissani Anir

#### Saighi Abd El Moumene

#### Akkouche Yakoub

### 📄 License

This project is released under the Academic & Educational Use License (AEUL).
See the LICENSE file for full details.

### 🙌 Credits

Arduino open-source hardware

HC-SR04 sensor tutorials

Android USB Serial libraries

Team members who contributed to the project

Teachers/mentors who supervised the project
