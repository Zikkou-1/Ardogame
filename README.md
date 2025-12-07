# Ardogame 🎮
### External Sensor-Based Game Controller for Android  

**Ardogame** is a hybrid hardware-software project that began as a part of a *L1 S1 Sofware course*.  
The goal is simple : **replace traditional touchscreen controls with a physical controller built using Arduino and real-world sensors**.

by combining an Arduino-based sensor board with an Anrdoid game, Ardogame introduces an entirely new way to interact with mobile games.

## 🔧 How it workds

### **1. Arduino-Based Controller**
**Ardogame** uses an Arduino ( or any compatible microcontroller ) as the core of a modular controller .  
Instead of regular buttons or joysticks, the controller can include:

+ **Ultrosonic** sensors 
+ **Heat/temperature** sensors 
+ **Potentiometers** sensors 
+ **Light** sensors 
+ **Pressure** sensors 
+ Any custom compatible module that provides a meaningful gameplay

The Arduino reads sensor values and sends compact string message to the phone via :

+ **USB Serial**, or 

+ **Bluetooth** for wireless mode

### **2. Android Game**
On the phone, Ardogame reveives and interprets the Arduino's serial message.  
Each message corresponds to an in-game action or state.

This allows for :

+ **Party-Style** games
+ **Single-player** action games
+ **Experimental** controls that go beyond touch screen limitations

## ✨Features. 

+ 🎮 **Custom Hardware Controller** - Build your own controller using Arduino + any sensors you want.
+ 📡 **USB or Bluetooth Input** - Supports both wired and wireless communication.
+ 📱 **Works With Any Android Game (w/ serial input)** - Just parse the incoming string and map it to controls.
+ 👋 **Gesture-Based Gameplay** - Hand movement detection using ultrasonic sensors. 
+ 🔧 **Modular Sensor System** - Swap sensors to instantly change the gameplay style. 
+ 🔌 **Lightweight Communication Protocol** - Simple string-based serial protocal for fast and stable communication.
+ 🛠️ **Fully DIY + Extendable** - Perfect for hobbyists, students, scientific clubs, and experimentation.
+ 🔁 **Repurpose Old Phones** - Turn unused Android phones into.
+ 🎉 **Great for parties & Group Games** - Fun, physical, interactive experiences.
+ 🔋 **Low-Cost, Low-Power** - Uses inexpensive components and reuses existing phones.
+ 🧩 **Cross-Sensor Experiments** - Mix distance, heat, light, pressure, or motion sensors for advanced controllers.
+ 🚀 **Future XR interaction Potential** - Can evovle intro a physical pinput layer for AR apps.
