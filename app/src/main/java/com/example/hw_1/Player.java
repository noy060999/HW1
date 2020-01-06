package com.example.hw_1;

import android.widget.TextView;

class Player implements Comparable{
        private String name;
        private String score;
        private double latitude,longitude;
        /*private String location;
        private TextView nameTV;
        private TextView scoreTV;
        private TextView locationTV;*/

        public Player(String name, String score, double longitude, double latitude){
            this.name = name;
            this.score = score;
            this.longitude = longitude;
            this.latitude = latitude;
            //this.location = location;
        }
        public Player(){

        }

        public String getName(){
            return name;
        }

        public String getScore(){
            return score;
        }

        public double getLatitude (){
            return this.latitude;
        }

        public double getLongitude(){
            return this.longitude;
        }

        public void setLatitude(double latitude){
            this.latitude = latitude;
        }

        public void setLongitude(double longitude){
            this.longitude = longitude;
        }

       /* public String getLocation(){
            return location;
        }*/

        public void setName(String name) {
            this.name = name;
        }

        public void setScore(String score) {
            this.score = score;
        }

    @Override
    public int compareTo(Object o) {
            Player other = (Player)o;
            if (other.getScore() != null && this.getScore() != null) {
                if (Integer.parseInt(this.getScore()) > Integer.parseInt(other.getScore()))
                    return 1;
                else if (Integer.parseInt(this.getScore()) < Integer.parseInt(other.getScore()))
                    return -1;
                else
                    return 0;
            }
            else return -1;
    }
}
