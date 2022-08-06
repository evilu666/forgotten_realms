package com.evilu.forgottenRealms.model;

/**
 * SourceCancelable
 */
public interface SourceCancelable<T> {

    public void cancel(final T source);
    
}
