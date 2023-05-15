package ru.mai.arachni.exception;

import lombok.Value;

@Value
public class ArachniErrorRepresentation {
    String errorCode;
    String message;
}
