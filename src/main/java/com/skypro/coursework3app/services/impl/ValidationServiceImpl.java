package com.skypro.coursework3app.services.impl;

import com.skypro.coursework3app.model.socks.SocksBatch;
import com.skypro.coursework3app.model.socks.SocksColor;
import com.skypro.coursework3app.model.socks.SocksSize;
import com.skypro.coursework3app.services.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validate(SocksBatch socksBatch) {
        {
            return socksBatch.getSocks() != null
                    && socksBatch.getQuantity() > 0
                    && socksBatch.getSocks().getColor() != null
                    && socksBatch.getSocks().getSize() != null
                    && checkCotton(socksBatch.getSocks().getCottonPart(), socksBatch.getSocks().getCottonPart());
        }
    }

    @Override
    public boolean validate(SocksColor socksColor, SocksSize socksSize, int cottonMin, int cottonMax) {
        return socksColor != null
                && socksSize != null
                && checkCotton(cottonMin, cottonMax);
    }

    private boolean checkCotton(int cottonMin, int cottonMax) {
        return cottonMin >= 0 && cottonMax <= 100;
    }
}
