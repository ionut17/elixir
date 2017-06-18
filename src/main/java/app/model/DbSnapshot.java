package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Ionut on 21-May-17.
 */
public class DbSnapshot {

    private int startYear;

    private int endYear;

    private int semester;

    private String description;

    private String id;

    //Constructors

    public DbSnapshot(){}

    public DbSnapshot(String key){
        String[] values = key.split("_");
        this.startYear = Integer.valueOf(values[0]);
        this.endYear = Integer.valueOf(values[1]);
        this.semester = Integer.valueOf(values[2]);
        this.setId(this.startYear+"_"+this.endYear+"_"+this.semester);
    }

    public DbSnapshot(int startYear, int endYear, int semester){
        this.startYear = startYear;
        this.endYear = endYear;
        this.semester = semester;
        this.setId(this.startYear+"_"+this.endYear+"_"+this.semester);
    }

    public DbSnapshot(int startYear, int endYear, int semester, String description){
        this.startYear = startYear;
        this.endYear = endYear;
        this.semester = semester;
        this.description = description;
        this.setId(this.startYear+"_"+this.endYear+"_"+this.semester);
    }

    //Setters and getters

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public String getKey(){
        return "snapshot_"+String.valueOf(startYear)+"_"+String.valueOf(endYear)+"_"+String.valueOf(semester);
    }

    @JsonIgnore
    public String getPropertyPrefix(){
        return "spring.snapshot_"+String.valueOf(startYear)+"_"+String.valueOf(endYear)+"_"+String.valueOf(semester);
    }

    @JsonIgnore
    public String getProperty(String property){
        return "spring.snapshot_"+String.valueOf(startYear)+"_"+String.valueOf(endYear)+"_"+String.valueOf(semester)+"."+property;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
