package com.example.madassignment.scholarship;


public class Scholarship {

    String scholarshipID, institution, title, description, studyLevel, criteria, award, deadline, website;

    boolean saved = false;
    public Scholarship() {
    }

    public Scholarship(String scholarshipID, String institution, String title, String description, String studyLevel, String criteria, String award, String deadline, String website) {
        this.scholarshipID = scholarshipID;
        this.institution = institution;
        this.title = title;
        this.description = description;
        this.studyLevel = studyLevel;
        this.criteria = criteria;
        this.award = award;
        this.deadline = deadline;
        this.website = website;
    }

    public String getScholarshipID() {
        return scholarshipID;
    }

    public void setScholarshipID(String scholarshipID) {
        this.scholarshipID = scholarshipID;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}



