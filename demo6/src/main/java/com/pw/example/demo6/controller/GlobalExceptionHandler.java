package com.pw.example.demo6.controller;

import com.pw.example.demo6.entity.Result;
import com.pw.example.demo6.entity.enums.ErrorEnum;
import com.pw.example.demo6.entity.enums.ErrorTypeEnum;
import com.pw.example.demo6.entity.exception.BaseAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

/**
 * Global异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;


    /**
     * 自己封装的应用层报错
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseAppException.class)
    @ResponseBody
    public ResponseEntity<Result> handleBaseAppException(BaseAppException e) {
        log.error("Base App Exception", e);
        return new ResponseEntity<>(buildResult(e.getCode(), e.getType(), e.getDesc(), null), HttpStatus.OK);
    }


    /**
     * application错误
     * @param request
     * @param be
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    public ResponseEntity<Result> handleHttpMediaTypeNotAcceptableException(HttpServletRequest request,
                                                                            HttpMediaTypeNotAcceptableException be) {
        log.error("handle HttpMediaTypeNotAcceptableException", be);
        return new ResponseEntity<Result>(buildBaseResult(request, ErrorEnum.SYS_EXCEPTION_ERROR.getCode(),
                be.getMessage(), ErrorTypeEnum.SYSTEM.getType(), getMessage(be)), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * HttpRequestMethodNotSupportedException 错误
     * @param request
     * @param be
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                                          HttpRequestMethodNotSupportedException be) {
        log.error("handle HttpRequestMethodNotSupportedException", be);
        return new ResponseEntity<>(buildBaseResult(request, ErrorEnum.SYS_EXCEPTION_ERROR.getCode(), be.getMessage(),
                ErrorTypeEnum.SYSTEM.getType(), getMessage(be)), HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(value = {
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            TypeMismatchException.class,
            ServletRequestBindingException.class,
            BindException.class})
    @ResponseBody
    public ResponseEntity<Result> handleExceptions(HttpServletRequest request, Exception e) {

        log.error("handle Exceptions", e);

        String msg = e.getMessage();

        String errorField = null;

        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            if (bindException.hasErrors()) {
                List<FieldError> fieldErrors = bindException.getFieldErrors();
                if (!CollectionUtils.isEmpty(fieldErrors)) {
                    FieldError first = fieldErrors.get(0);
                    msg = first.getDefaultMessage();
                    errorField = first.getField();
                }
            }
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException pe = (MissingServletRequestParameterException) e;
            msg = "缺少参数" + pe.getParameterName();
        }

        Result result = buildBaseResult(request, ErrorEnum.SYS_INVLIAD_REQUEST.getCode(), msg,
                ErrorTypeEnum.APPLICATION.getType(), getMessage(e));

        return new ResponseEntity<Result>(result, HttpStatus.OK);
    }


    @ExceptionHandler(value = {
            ConversionNotSupportedException.class,
            HttpMessageNotWritableException.class,
            MultipartException.class,
            Exception.class})
    @ResponseBody
    public ResponseEntity<Result> handleException(HttpServletRequest request, Exception e) {
        log.error("Unexpected exceptions!!!", e);
        String errorMsg = e.getMessage();

        if (e instanceof MultipartException) {
            errorMsg = "文件大小不能大于50M";
        }

        return new ResponseEntity<>(buildBaseResult(request, ErrorTypeEnum.SYSTEM.getCode(), errorMsg,
                ErrorTypeEnum.APPLICATION.getType(), getMessage(e)), HttpStatus.OK);
    }



    /**
     * @param errorCode
     * @param errorType
     * @param defaultErrorMsg
     * @param data
     * @return
     */
    private Result buildResult(int errorCode, String errorType, String defaultErrorMsg, Object data) {
        Result result = Result.failResult(errorCode, defaultErrorMsg);
        log.error("errorCode={},errorMessage={}", result.getErrCode(), defaultErrorMsg);
        return result;
    }


    private Result buildBaseResult(HttpServletRequest request, int errorCode, String errorMsg,
                                   String errorType,String errorMsgForLog) {
        Result result = getBaseResult(errorCode, errorMsg, errorType);
        return result;
    }


    private Result getBaseResult(int errorCode, String errorMsg, String errorType) {
        Result result = new Result();
        result.setErrCode(errorCode);
        result.setReason(getErrorMessage(errorCode, errorMsg));
        log.error("errorCode={},reason={}", result.getErrCode(), result.getReason());

        return result;
    }

    /**
     * 如果Exception Message为空，则获取异常的堆栈信息
     * @param exception
     * @return
     */
    private String getMessage(Exception exception) {
        String message = exception.getMessage();
        if (!StringUtils.isEmpty(message)) {
            return message;
        }
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            exception.printStackTrace(new PrintWriter(buffer, true));
            message = buffer.toString();
        } catch (Exception inner) {

        }

        return message;
    }

    private String getErrorMessage(int errorCode, String errorMessage) {
        String desc = errorMessage;

        String _errorCode = String.valueOf(errorCode);

        if (StringUtils.isEmpty(errorMessage)) {
            desc = messageSource.getMessage(_errorCode, null, _errorCode, Locale.CHINESE);
        }

        return desc;
    }


}
