package io.muzoo.ssc.webapp.service;

public class UsernameNotUniqueExpection extends UserServiceException{

    public UsernameNotUniqueExpection(String message) {
        super(message);
    }
}
