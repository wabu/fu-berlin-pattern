/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.util.Threads;

/**
 *
 * @author wabu
 */
public class Main {
    public static void main(String args[]) throws InterruptedException {
        Main16.main(args);
        Threads.shutdown();
    }
}
