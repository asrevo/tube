package org.revo.Domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Index {
    @Id
    private String id;
    private String master;
    private String stream;
    private String average_bandwidth;
    private String bandwidth;
    private String codecs;
    private String resolution;
    private long execution;
}