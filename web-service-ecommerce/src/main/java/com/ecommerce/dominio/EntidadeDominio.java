package com.ecommerce.dominio;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class EntidadeDominio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public EntidadeDominio() {
    }

    public EntidadeDominio(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
