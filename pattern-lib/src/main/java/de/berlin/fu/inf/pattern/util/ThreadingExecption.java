/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util;

/**
 *
 * @author wabu
 */
public class ThreadingExecption extends RuntimeException {
    public ThreadingExecption(Exception cause) {
        super(cause);
    }
}
