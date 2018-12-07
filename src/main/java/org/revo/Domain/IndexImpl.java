package org.revo.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexImpl {
    private String index;
    private String resolution;
    private Status status;

    public IndexImpl() {
    }

    public IndexImpl(String index, String resolution, Status status) {
        this.index = index;
        this.resolution = resolution;
        this.status = status;
    }
}
