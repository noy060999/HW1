package com.example.hw_1;

import android.widget.TextView;

class Player implements Comparable{
        private String name;
        private String score;
        /*private String location;
        private TextView nameTV;
        private TextView scoreTV;
        private TextView locationTV;*/

        public Player(String name, String score, String location){
            this.name = name;
            this.score = score;
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

       /* public String getLocation(){
            return location;
        }*/

        public void setName(String name) {
            this.name = name;
        }

        public void setScore(String score) {
            this.score = score;
        }

       /* public void setLocation(String location) {
            this.location = location;
        }

        public void setNameTXTVIEW(TextView nameTV){
            this.nameTV = nameTV;
        }

    public void setScoreTV(TextView scoreTV) {
        this.scoreTV = scoreTV;
    }

    public void setLocationTV(TextView locationTV) {
        this.locationTV = locationTV;
    }

    public TextView getNameTV() {
        return nameTV;
    }

    public TextView getScoreTV() {
        return scoreTV;
    }

    public TextView getLocationTV() {
        return locationTV;
    }*/

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
