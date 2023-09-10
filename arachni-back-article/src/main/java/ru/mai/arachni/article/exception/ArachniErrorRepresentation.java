package ru.mai.arachni.article.exception;

import lombok.Value;

@Value
public class ArachniErrorRepresentation {
    String errorCode;
    String message;
}
