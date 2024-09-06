# Android Module

## Android API

### General Format
Messages between the Android app and Raspberry Pi will be in the following format:

```json
{"cat": "xxx", "value": "xxx"}
```
Note: 

### Android to Raspberry PI

**Obstacles**
The message sent from Android to Raspberry Pi will be in the following format for obstacles: (week 8)
```json
{
"cat": "obstacles",
"value": {
    "obstacles": [{"x": 5, "y": 10, "id": 1, "d": 2}, {"x": 6, "y": 11, "id": 2, "d": 4}],
    "mode": "0"
}
```
Direction of the robot (d)

    NORTH - UP - 0
    EAST - RIGHT - 2
    SOUTH - DOWN - 4
    WEST - LEFT 6


**Start Movement**
Android will send the following message to Raspberry Pi to start the movement of the robot (assuming obstacles were set).
```json
{"cat": "control", "value": "start"}
```

### Raspberry Pi to android
**Location Updates**
Raspberry Pi will periodically notify Android with the updated location of the robot, so its location may be displayed on the iPad
```json
{"cat": "location", "value": {"x": 1, "y": 1, "d": 0}}
```

**Image Recognition**

Raspberry Pi will send the following message to Android, so that Android can update the results of the image recognition:
```json
{"cat": "image-rec", "value": {"image_id": "A", "obstacle_id":  "1"}}
```
**Other messages**
Raspberry Pi will occasionally also send some error or log or info messages. Currently, no implementation needs to be done for this.

## Project Structure
```
.
├── .idea/                  ## run config
├── app/
│   └── src/
│       ├── androidTest/
│       │   └── ... 
│       ├── main/
│       │   ├── java        ## java source code
│       │   └── res         ## UI collaterals
│       └── test/
│           └── ...
├── gradle/                 ## gradle build system
├── .gitignore
├── build.gradle               
├── gradle.properties     
├── gradlew                 ## gradle scripts
├── gradlew.bat             ## gradle scripts
├── README.md
└── settings
```
## Version
* JDK 11.0
* Gradle 7.5
