package it.adozioni.animali.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> ResultDto<T> success(String message, T data) {
        return new ResultDto<>(true, message, data);
    }

    public static <T> ResultDto<T> error(String message) {
        return new ResultDto<>(false, message, null);
    }

}