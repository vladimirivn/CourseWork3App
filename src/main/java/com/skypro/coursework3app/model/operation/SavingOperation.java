package com.skypro.coursework3app.model.operation;

import com.skypro.coursework3app.model.socks.SocksBatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SavingOperation {
    private OperationType operationType;
    private SocksBatch socksBatch;
    private final LocalDateTime dateTime = LocalDateTime.now();

}
