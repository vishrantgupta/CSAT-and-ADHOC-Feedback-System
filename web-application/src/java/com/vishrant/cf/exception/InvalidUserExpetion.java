/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.exception;

import org.apache.log4j.Logger;

/**
 *
 * @author vg00124233
 */
public class InvalidUserExpetion extends Exception {

    final Logger log = Logger.getLogger(InvalidUserExpetion.class);

    public InvalidUserExpetion(String msg) {
        super(msg);
        log.error("InvalidUserExpetion occured " + msg);
    }
}