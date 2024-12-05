package br.com.ifba.conectaedu.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EventoEscolarProjection {

     Long getId();
     String getNome();
     LocalDate getDataInicio();
     LocalDate getDataTermino();
     String getPeriodo();

}
