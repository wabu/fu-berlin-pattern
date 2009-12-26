/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import de.berlin.fu.inf.pattern.util.fun.Proc;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author wabu
 */
public class Threads {
    public final static int DEFAULT_POO_LSIZE=4;
    private final static ExecutorService exec = Executors.newFixedThreadPool(DEFAULT_POO_LSIZE);

    public static <F,T> Collection<T> doParralell(Collection<F> it,
            final Function<? super F, T> task) {
        try {
            return Collections2.transform(exec.invokeAll(Collections2.transform(it, new Function<F, Callable<T>>() {
                public Callable<T> apply(final F from) {
                    return new Callable<T>() {
                        public T call() throws Exception {
                            return task.apply(from);
                        }
                    };
                }
            })), new Force<T>());
        } catch (InterruptedException ex) {
            throw new ThreadingExecption(ex);
        }
    }

    public static <F,T> Iterable<T> doParralell(Iterable<F> it,
            final Function<? super F, T> task) {
        final LinkedList<Future<T>> result = new LinkedList<Future<T>>();
        for(final F elem : it) {
            result.add(exec.submit(new Callable<T>() {
                public T call() throws Exception {
                    return task.apply(elem);
                }
            }));
        }
        return Iterables.transform(result, new Force<T>());
    }

    public static void run(Runnable ... tasks) {
        try {
            for(Runnable t : tasks) {
                t.run();
            }
        } finally {
            shutdown();
        }
    }

    public static class Force<T> implements Function<Future<T>, T> {
        public T apply(Future<T> from) {
            try {
                return from.get();
            } catch (InterruptedException ex) {
                throw new ThreadingExecption(ex);
            } catch (ExecutionException ex) {
                throw new ThreadingExecption(ex);
            }
        }
    }

    public static <E> void doParralell(Iterable<E> it, final Proc<? super E> proc) {
        LinkedList<Future<?>> fs = new LinkedList<Future<?>>();
        for(final E elem : it) {
            fs.add(exec.submit(new Runnable() {
                public void run() {
                    proc.apply(elem);
                }
            }));
        }
        for(Future<?> f : fs) {
            try {
                f.get();
            } catch (InterruptedException ex) {
                throw new ThreadingExecption(ex);
            } catch (ExecutionException ex) {
                throw new ThreadingExecption(ex);
            }
        }
    }

    public static void shutdown() {
        exec.shutdown();
    }
}
