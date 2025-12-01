# Ardogame                              
![Screen shot from astroDash](./screenshot.jpg)
## Description
AstroDash:

Concept:
We created a small Arduino game where the Arduino handles all the game logic, including the movement of the meteors and the timing of the Dash.

The goal of the game is simple:
we have to move our hand over the ultrasonic sensor\potentiometer at the right moment to keep the Spaceship moving.
If our timing is correct, the spaceship continues moving.
If we move too early or too late, the ship crashes and the game ends.

As the game progresses, the meteors speed gradually increases, making the timing more challenging.
Our score is the number of seconds we can stay alive before the game ends.

It’s a fast and fun reaction game that tests precision and timing, with the Arduino controlling the logic, the ultrasonic sensor\potentiometer detecting our actions, and the phone screen displaying the game.

### 🪨 Arduino-Controlled meteor Dodge Game (Android Edition)
Play AstroDash on your phone using just your hand!

### 📌 About the Project

This project is a modern twist on the classic Meteor Dodge Game, now fully playable on Android devices.
The spaceship is controlled by your hand via an ultrasonic sensor\potentiometer connected to an Arduino Uno, which communicates directly with your phone over USB serial.

✔ No keyboard or touchscreen needed

✔ Real-time hand tracking for smooth control

✔ Educational project for Arduino and mobile app development


### 🎮 How It Works

Hand movement is detected by the HC-SR04 ultrasonic sensor\or using the potentiometer , turning it will make it move.

Arduino Uno reads the distance\the value and sends it over USB serial to the Android phone

The Android app reads the serial data and moves the spaceship on screen in real time.

The game runs like classic Meteor Dodging Game, including ship and meteor movement, collisions, and scoring.

### ✨ Features

🖐️ Hand-controlled ship movement

📱 Runs directly on Android

🔌 USB Serial communication

🎨 Smooth graphics inside our app

🧪 Educational — ideal for learning Arduino, sensors, and Android development

🎓 Academic & educational license


### 🛠️ Hardware Required

Arduino Uno

HC-SR04 ultrasonic sensor\ Potentiometer

USB OTG cable (Arduino → Phone)

Jumper wires

Android phone

App Developped (APK from your project)

### ⚡ Wiring Overview

<div align="left"> <img src="https://i0.wp.com/randomnerdtutorials.com/wp-content/uploads/2013/11/ultrasonic-sensor-with-arduino-hc-sr04.jpg?resize=828%2C386&quality=100&strip=all&ssl=1" width="500"> </div>

<div align="right"> <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvoDYQRg3Vpmevni9SsJnvbCFMK4TLxAazyQ&s" width="500"> </div>

HC-SR04\Potentiometer → Arduino Uno

HC-SR04\Potentiometer Pin	Arduino Pin

VCC	5V

GND	GND

TRIG	D9

ECHO	D10

### 🧩 System Architecture

Hand → Ultrasonic Sensor\Potentiometer → Arduino Uno → USB Serial → Android App → Spaceship Movement

### 💻 Arduino Code (Uno)

### 📱 Android App Integration

The app reads serial data from Arduino Uno via USB OTG.

Spaceship moves horizontally according to hand distance\ turning degree.

Meteors movement and collisions are handled inside the app.

⚠️ On first connection, the app may require USB permission to access the Arduino.

### 🔧 Setup Instructions (Phone)

Connect Arduino Uno to the Android phone via OTG cable.

Upload the Arduino code to the board.

Install your Android APK on the phone.

Open the app and grant USB permission.

Move your hand in front of the sensor\ use it to turn the potentiometer to control the spaceship

### 👥 Team Members + roles

#### Mebarki abdellah (GitHub Organisation)

#### Benmessaoud razik (Project Manager)

#### Aissani Anir (Hardware Specialist)

#### Saighi Abd El Moumene (Software Specialist)

#### Akkouche Yakoub (Game Designer)

* ‼️ Note : EVERY MEMBER OF THE PROJECT DID CONTRIBUTE IN EVERY ASPECT OF THE PROJECT.

### 📄 License

This project is released under the Academic & Educational Use License (AEUL).
See the LICENSE file for full details.

