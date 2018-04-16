package org.macalester.edu.comp124.hw5;

public class MatchScore {

    private double score;
    private Template template;

    public MatchScore(double score, Template template){
        this.score = score;
        this.template = template;
    }

    public double getScore(){
        return score;
    }

    public Template getTemplate(){
        return template;
    }
}
