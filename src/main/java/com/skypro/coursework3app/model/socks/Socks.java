package com.skypro.coursework3app.model.socks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Носки
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {

    private SocksColor color;
    private SocksSize size;
    private int cottonPart;

}
