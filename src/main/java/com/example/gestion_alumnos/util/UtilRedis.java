package com.example.gestion_alumnos.util;

import com.example.gestion_alumnos.entity.Alumno;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UtilRedis {

    private UtilRedis()
    {

    }
    public static String convertToJsonEntity(Alumno alumno) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(alumno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <T> T convertFromJson(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
