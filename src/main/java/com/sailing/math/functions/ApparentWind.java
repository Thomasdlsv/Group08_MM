package com.sailing.math.functions;

public class ApparentWind {
/**
 * Calculates the apparent wind speed.
 * @param windSpeed the speed of the wind.
 * @param windAngle the angle of the wind.
 * @param boatSpeed the speed of the boat.
 * @param boatAngle the angle of the boat.
 * @return the apparent wind speed.
 */
public double[] apparentWind(double windSpeed, double windAngle, double boatSpeed, double boatAngle){
    double trueWindAngle = windAngle - boatAngle;
   
    double WindAngle = 0.0;
    double cosWS = Math.cos(Math.toRadians(windSpeed));
    double apparentWindSpeed = Math.sqrt(Math.pow(windSpeed,2) + Math.pow(boatSpeed,2) - 2*windSpeed*boatSpeed*Math.cos(Math.toRadians(trueWindAngle)));
    double apparentWindAngle = Math.acos(windSpeed*Math.cos(Math.toRadians(trueWindAngle))+boatSpeed/apparentWindSpeed);
    double apparentWindDirection= boatAngle + apparentWindAngle;
    if (apparentWindSpeed > 0.0) { // windspeed and boatspeed can annulate each other, or wind can be 0 
        double Q = (windSpeed* cosWS + boatSpeed) / apparentWindSpeed;
        if (Q > 1.0) { // fix rounding errors, C must not be greater 1.0, or the arcuscosine will fail
            Q = 1.0;
        } else if (Q < -1.0) { // fix rounding errors, C must not be greater 1.0, or the arcuscosine will fail
            Q = -1.0;
        }
        apparentWindAngle = Math.acos(Q);
        if (Double.isNaN(WindAngle)) {
            System.out.println("apparentWind(acosBeta=NaN Q:" + Q);
        }
    }
    double[] apparentWind = {apparentWindSpeed,apparentWindAngle,apparentWindDirection};
    return apparentWind;
}

public void windGust(){
    //TODO
}


}
