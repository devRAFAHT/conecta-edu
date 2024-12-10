package br.com.ifba.conectaedu.repository.projection;

import java.time.LocalDate;

public interface EventoEscolarProjection {

     Long getId();
     String getNome();
     LocalDate getDataInicio();
     LocalDate getDataTermino();
     String getPeriodo();

}
