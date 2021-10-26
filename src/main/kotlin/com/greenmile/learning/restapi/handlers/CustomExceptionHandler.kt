package com.greenmile.learning.restapi.handlers

import com.greenmile.learning.restapi.model.ErrorResponse
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import java.io.PrintWriter
import java.io.StringWriter
import java.sql.SQLIntegrityConstraintViolationException
import java.sql.SQLInvalidAuthorizationSpecException
import javax.naming.AuthenticationNotSupportedException

@ControllerAdvice
class CustomExceptionHandler {

    private fun generateErrorResponse(
        status: HttpStatus,
        message: String,
        e: Exception,
    ): ResponseEntity<ErrorResponse> {
        // converting the exception stack trace to a string
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        val stackTrace = sw.toString()

        // example: logging the stack trace
        // log.debug(stackTrace)

        // environment-based logic
        val stackTraceMessage =
            when (System.getenv("ENV").uppercase()) {
                "STAGING" -> stackTrace // returning the stack trace
                "PRODUCTION" -> null // returning no stack trace
                else -> stackTrace // default behavior
            }

        return ResponseEntity(ErrorResponse(status, message, stackTraceMessage), status)
    }

    @ExceptionHandler(
        SQLIntegrityConstraintViolationException::class,
        HttpClientErrorException.BadRequest::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestParameterException::class,
        IllegalArgumentException::class
    )
    fun constraintViolationException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, e.message ?: "Bad request", e)
    }

    @ExceptionHandler(SQLInvalidAuthorizationSpecException::class)
    fun unauthorizedException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.FORBIDDEN, "You are not authorized to do this operation", e)
    }

    @ExceptionHandler(AuthenticationNotSupportedException::class)
    fun forbiddenException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.UNAUTHORIZED, "You are not allowed to do this operation", e)
    }

    @ExceptionHandler(
        EntityNotFoundException::class,
        NoSuchElementException::class,
        IndexOutOfBoundsException::class,
        KotlinNullPointerException::class
    )
    fun notFoundException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "Resource not found", e)
    }

    @ExceptionHandler(
        Exception::class
    )
    fun internalServerErrorException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error", e)
    }

}