// This is the Arduino part where it takes input from sensors then send them to the app " Ardogame " 

// Ultra-fast HC-SR04 code for Arduino
// TRIG = D9, ECHO = D8

void setup() {
  Serial.begin(19200);

  DDRB |= (1 << 1);    // D9 = output
  DDRB &= ~(1 << 0);   // D8 = input
}

unsigned long ultraFastPing() {
  // Trigger pulse 10us
  PORTB &= ~(1 << 1);
  delayMicroseconds(2);
  PORTB |= (1 << 1);
  delayMicroseconds(10);
  PORTB &= ~(1 << 1);

  // Wait HIGH
  unsigned long start = micros();
  while (!(PINB & (1 << 0))) {
    if (micros() - start > 20000) return 0; // timeout
  }
  unsigned long echoStart = micros();

  // Wait LOW
  while (PINB & (1 << 0)) {}
  unsigned long echoEnd = micros();

  return echoEnd - echoStart;
}

void loop() {
  unsigned long duration = ultraFastPing(); 
  float distance = duration * 0.0343 / 2; 
  Serial.println(distance); // sending the distance to the app
}
