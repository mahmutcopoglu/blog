package com.mahmutcopoglu.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProcessStatus {
    SUCCESS("SUCCESS"), ERROR("ERROR");

    private String value;
}
