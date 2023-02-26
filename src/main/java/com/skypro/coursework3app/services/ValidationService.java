package com.skypro.coursework3app.services;

import com.skypro.coursework3app.model.socks.SocksBatch;
import com.skypro.coursework3app.model.socks.SocksColor;
import com.skypro.coursework3app.model.socks.SocksSize;

public interface ValidationService {
    boolean validate(SocksBatch socksBatch);
    boolean validate(SocksColor socksColor, SocksSize socksSize, int cottonMin, int cottonMax);
}


