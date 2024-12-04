package br.com.ifba.conectaedu.repository.projection;

import java.time.LocalDate;

public interface FeriadoProjection {

    Long getId();
    String getNome();
    LocalDate getDataInicio();
    LocalDate getDataFim();

}
