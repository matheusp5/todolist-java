package dev.mxtheuz.todolist.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse<T> {
    private int code;
    private String message;
    private T content;
}
