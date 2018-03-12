package com.wteam.horeca.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "distributors")
@Data
public class DistributorsProperties {
    private List<DistributorProperties> configuration = new ArrayList<>();
}
