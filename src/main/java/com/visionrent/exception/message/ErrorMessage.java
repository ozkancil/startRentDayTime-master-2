package com.visionrent.exception.message;

public abstract class ErrorMessage {

    public static final String RESOURCE_NOT_FOUND_MESSAGE = "Resource with id %d not found";

    public static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";

    public static final String JWT_TOKEN_MESSAGE = "JWT token validation error";

    public static final String EMAIL_ALREADY_EXIST_MESSAGE = "Email: %s already exist";

    public static final String ROLE_NOT_FOUND_EXCEPTION = "Role %s not found";

    public static final String PRINCIPAL_NOT_FOUND_MESSAGE = "User not found";

    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You don't have any permission to change this data";

    public static final String PASSWORD_NOT_MATCHED = "Your passwords are not matched";

    public static final String IMAGE_NOT_FOUND_MESSAGE = "ImageFile with id %s not found";

    public static final String IMAGE_USED_MESSAGE = "ImageFile is used by other car";


    public static final String RESERVATION_TIME_INCORRECT_MESSAGE = "Reservation pick up time or drop off time is not correct";

    public static final String CAR_NOT_AVAILABLE_MESSAGE = "Car is not available for selected time";

    public static final String RESERVATION_STATUS_CAN_NOT_CHANGE_MESSAGE = "Reservation can not be updated for CANCELLED or DONE reservation";


    public static final String CAR_USED_BY_RESERVATION_MESSAGE = "Car can not be deleted. Car is used by a reservation";

    public static final String EXCEL_REPORT_REPORT_ERROR_MESSAGE = "Error occurred while generating excel report";


}
