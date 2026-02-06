package com.example.charityapi.exception;

public class InvalidDonationAmountException extends RuntimeException {
    public InvalidDonationAmountException(String message) { super(message); }
}
