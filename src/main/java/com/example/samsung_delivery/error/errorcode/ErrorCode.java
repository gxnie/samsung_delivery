package com.example.samsung_delivery.error.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */

    LOGIN_REQUIRED(BAD_REQUEST, "로그인이 필요합니다."),
    EXPIRED_SET_IMPOSSIBLE(BAD_REQUEST, "잘못된 만료기한 요청입니다."),
    STORE_CLOSED(BAD_REQUEST,"영업시간이 아닙니다."),
    FIXED_RATE_ERROR(BAD_REQUEST , "정률할인은 0부터 100사이의 할인률만 가능합니다."),
    FIXED_AMOUNT_ERROR(BAD_REQUEST,  "정액쿠폰은 1000원 이상부터 가능합니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_USER(UNAUTHORIZED, "권한이 없습니다. 해당유저만 가능합니다."),

    /* 403 FORBIDDEN : 권한이 없음 */
    INVALID_USER_ROLE(FORBIDDEN, "사장님만 가능한 작업입니다."),
    INVALID_STORE_ACCESS(FORBIDDEN, "본인 가게에만 접근할 수 있습니다."),
    INVALID_COUPON_ISSUED(FORBIDDEN, "이미 발급받은 쿠폰입니다."),
    INVALID_COUPON_STORE(FORBIDDEN,"발급받은 상점에만 사용할 수 있습니다."),
    INVALID_COUPON_ACCESS(FORBIDDEN , "해당쿠폰은 더이상 사용할 수 없습니다."),
    PRICE_NOT_ENOUGH(FORBIDDEN, "주문금액이 부족합니다."),
    POINT_NOT_ENOUGH(FORBIDDEN , "포인트 부족"),
    COUPON_CANNOT_ISSUED(FORBIDDEN, "해당쿠폰은 더이상 발급할 수 없습니다."),
    COUPON_EXPIRED(FORBIDDEN,"해당쿠폰은 만료되었습니다."),



    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "유저 정보를 찾을 수 없습니다."),
    STORE_NOT_FOUND(NOT_FOUND, "해당 가게가 존재하지 않습니다."),
    MENU_NOT_FOUND(NOT_FOUND, "해당 메뉴가 존재하지 않습니다."),
    ORDER_NOT_FOUND(NOT_FOUND, "주문 정보를 찾을 수 없습니다."),
    COUPON_NOT_FOUND(NOT_FOUND, "쿠폰 정보를 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    INVALID_DATE_EXCEPTION(FORBIDDEN, "현재 날짜보다 이후 입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
