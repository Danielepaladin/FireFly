package it.univaq.disim.oop.firefly.controller;

public interface DataInitializable<T> {

	default void initializeData(T t) {
	}

}
