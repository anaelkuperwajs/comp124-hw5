package org.macalester.edu.comp124.hw5;

import java.util.List;

/**
 * Creates a template object and has helper methods
 */

public class Template {

    private String name;
    private List<Point> templatePoints;

    public Template(String name, List<Point> templatePoints){
        this.name = name;
        this.templatePoints = templatePoints;
    }

    public List<Point> getTemplatePoints(){
        return templatePoints;
    }

    public String getName(){
        return name;
    }
}
