package com.example.simpleblog.application;

public abstract class AbstractUseCase<I, O> {

    public abstract O execute(I input);

}
