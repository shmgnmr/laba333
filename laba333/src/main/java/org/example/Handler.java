package org.example;

import java.util.HashMap;

public abstract class Handler {
    Handler nextFileHandler;

    public abstract boolean checkType(String filePath);

    public abstract HashMap<String, Reactor> loadReactors(String filePath);

    public void setNextFileHandler(Handler nextFileHandler) {
        this.nextFileHandler = nextFileHandler;
    }

    public HashMap<String, Reactor> commonloadReactors(String filePath) {
        HashMap<String, Reactor> reactors = new HashMap<>();
        if (checkType(filePath) == true) {
            reactors = loadReactors(filePath);
        } else {
            reactors = nextFileHandler.commonloadReactors(filePath);
        }
        return reactors;
    }

    ;
}
