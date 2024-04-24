package com.benson.mediaapi.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MajorVO extends MessageVO{
    private String txnSeq;
    private String subject;
    private String textContent;
}
