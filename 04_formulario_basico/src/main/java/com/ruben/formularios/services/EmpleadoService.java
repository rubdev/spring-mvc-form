package com.ruben.formularios.services;

import com.ruben.formularios.model.Empleado;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Servicio principal que inicializar lista con datos de empleado
 */
@Service
public class EmpleadoService {

    private List<Empleado> repositorio = new ArrayList<>();

    @PostConstruct
    public void init() {
        repositorio.addAll(
                Arrays.asList(new Empleado(1, "Goku", "goku@dbz.com", "666555444"),
                        new Empleado(2, "Vegeta", "vegeta@dbz.com", "666333222"),
                        new Empleado(3, "Freezer", "freezer@dbz.com", "666111000")
                )
        );
    }

    public Empleado add(Empleado e) {
        repositorio.add(e);
        return e;
    }

    public List<Empleado> findAll() {
        return repositorio;
    }

    public Empleado findById(long id) {
        Empleado result = null;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < repositorio.size()) {
            if (repositorio.get(i).getId() == id) {
                encontrado = true;
                result = repositorio.get(i);
            } else {
                i++;
            }
        }

        return result;
    }

    public Empleado edit(Empleado e) {
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < repositorio.size()) {
            if (repositorio.get(i).getId() == e.getId()) {
                encontrado = true;
                repositorio.remove(i);
                repositorio.add(i, e);
            } else {
                i++;
            }
        }

        if (!encontrado)
            repositorio.add(e);

        return e;
    }

}
