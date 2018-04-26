package com.foodakai.servicecaller.responses.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgroPortalRecommenderResponse {

    private List<AgroPortalRecommenderEntity> entities;

    public AgroPortalRecommenderResponse() {
    }

    public List<AgroPortalRecommenderEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<AgroPortalRecommenderEntity> entities) {
        this.entities = entities;
    }
}
