package com.mahmutcopoglu.blog.dto;

import com.mahmutcopoglu.blog.enums.ProcessStatus;
import lombok.Data;

@Data
public class ServiceResponseData {
    private ProcessStatus status;
    private String errorMessage;
    private String errorMessageDetail;
    private String detail;
    private Object data;
}
