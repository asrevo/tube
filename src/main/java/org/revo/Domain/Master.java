package org.revo.Domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class Master extends Base {
    private String image;
    private String secret;
    private String file;
    private List<IndexImpl> impls;
    private double time;
    private String resolution;
}