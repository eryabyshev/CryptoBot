package ru.evgeny.exmobot.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message<T> {
    private T object;
    private String text;
    private MessageType type;
    private Date date;
}
