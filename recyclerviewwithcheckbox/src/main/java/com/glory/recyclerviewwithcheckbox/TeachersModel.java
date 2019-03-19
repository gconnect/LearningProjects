package com.glory.recyclerviewwithcheckbox;

public class TeachersModel {
    private int profilePix;
    private String name;
    private String description;
    private boolean isSelected;
//    private boolean isChecked;

    public TeachersModel(int profilePix, String name, String description) {
        this.profilePix = profilePix;
        this.name = name;
        this.description = description;
//        this.isChecked = isChecked;
    }

    public int getProfilePix() {
        return profilePix;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setProfilePix(int profilePix) {
        this.profilePix = profilePix;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //
//    public boolean isChecked() {
//        return isChecked;
//    }
//
//    public void setChecked(boolean checked) {
//        isChecked = checked;
//    }


    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
