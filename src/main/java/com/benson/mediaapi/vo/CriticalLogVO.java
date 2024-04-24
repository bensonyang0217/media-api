package com.benson.mediaapi.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriticalLogVO extends MajorVO{
    private String severity;
    private String mcParameter;
    private String mcObject;
}
