package br.com.ifba.conectaedu.repository.projection;

import java.time.LocalDate;

public interface ProgramaEducacionalProjection {

    Long getId();
    String getNome();
    LocalDate getDataInicio();
    LocalDate getDataTermino();
    String getPeriodo();
    String getNivelEnsino();

}
