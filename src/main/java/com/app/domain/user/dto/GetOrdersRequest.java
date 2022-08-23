package com.app.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SortType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class GetOrdersRequest {

    @JsonProperty("accesstoken")
    private String accessToken;

    @JsonProperty("refreshtoken")
    private String refreshToken;

    private String sortMethod;

    private String sortType;

    private String dateWith;

    private String dateFor;

    public Date getDateWith() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.dateWith);
        } catch (ParseException err) {
            return null;
        }
    }

    public Date getDateFor() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.dateFor);
        } catch (ParseException err) {
            return null;
        }
    }
}
