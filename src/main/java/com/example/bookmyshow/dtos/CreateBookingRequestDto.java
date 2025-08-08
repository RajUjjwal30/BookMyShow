package com.example.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CreateBookingRequestDto {
    private Long userId;
    private Long showId;
    private List<Long> showSeatIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public List<Long> getShowSeatIds() {
        return showSeatIds;
    }

    public void setShowSeatIds(List<Long> showSeatIds) {
        this.showSeatIds = showSeatIds;
    }
}
