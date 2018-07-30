package com.zykj.carfigure.entity;

public class NavigationMap {
    private int type;
    private String mapName;

    public NavigationMap(int type, String mapName) {
        this.type = type;
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
