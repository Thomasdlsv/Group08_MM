package com.sailing;

public class Sailboat {
private double mass;
private double length;
private double width;
private double height;
private double sailArea;
private double keelDepth;
private double rudderDepth;

public Sailboat(double mass, double length, double width, double height, double sailArea, double keelDepth, double rudderDepth){
    this.mass = mass;
    this.length = length;
    this.width = width;
    this.height = height;
    this.sailArea = sailArea;
    this.keelDepth = keelDepth;
    this.rudderDepth = rudderDepth;
}
//getters and setters
public double getMass() {
    return mass;
}

public void setMass(double mass) {
    this.mass = mass;
}
public double getLength(){
    return length;
}

public void setLength(double length){
    this.length = length;


}


}
