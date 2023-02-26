package com.skypro.coursework3app.controllers;

import com.skypro.coursework3app.controllers.dto.ResponseDto;
import com.skypro.coursework3app.model.socks.SocksBatch;
import com.skypro.coursework3app.model.socks.SocksColor;
import com.skypro.coursework3app.model.socks.SocksSize;
import com.skypro.coursework3app.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
@Tag(name = "API учета носков", description = "Учет прихода, расхода, брака и количества носков")

public class SocksController {

    private final SocksService socksService;

    @PostMapping
    @Operation(summary = "Регистрация прихода товара на склад")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Удалось добавить приход"),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<ResponseDto> arrivalOfGoods(@RequestBody SocksBatch socksBatch) {
        socksService.arrivalOfGoods(socksBatch);
        return ResponseEntity.ok(new ResponseDto("Товар успешно добавлен на склад"));
    }

    @PutMapping
    @Operation(summary = "Регистрация отпуска товара со склада")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Удалось произвести отпуск носков со склада"),
            @ApiResponse(responseCode = "400", description = "Товара нет на складе в нужном количестве или " +
                    "параметры запроса имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<ResponseDto> releaseOfGoods(@RequestBody SocksBatch socksBatch) {
        int goods = socksService.releaseOfGoods(socksBatch);
        return ResponseEntity.ok(new ResponseDto("Количество успешно списанного товара со склада:" + goods));
    }

    @GetMapping
    @Operation(summary = "Общее количество носков на складе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос выполнен, результат в теле ответа в виде " +
                    "строкового представления целого числа"),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<ResponseDto> getTotalOfGoods(@RequestParam SocksColor socksColor,
                                                       @RequestParam SocksSize size,
                                                       @RequestParam int cottonMin,
                                                       @RequestParam int cottonMax) {
        int totalOfGoods = socksService.getTotalOfGoods(socksColor, size, cottonMin, cottonMax);
        return ResponseEntity.ok(new ResponseDto("Количество товара на складе: " + totalOfGoods));
    }

    @DeleteMapping
    @Operation(summary = "Регистрация списания испорченных (бракованных) носков")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос выполнен, товар списан со склада"),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<ResponseDto> writeOffDefectiveOfGoods(@RequestBody SocksBatch socksBatch) {
        int defectiveOfGoods = socksService.writeOffDefectiveOfGoods(socksBatch);
        return ResponseEntity.ok(new ResponseDto("Количество товара списанного со склада: " + defectiveOfGoods));
    }
}
