package com.ruben.formularios.controllers;

import com.ruben.formularios.model.Empleado;
import com.ruben.formularios.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Clase controladora que nos servirá para manejar las vistas de empleado
 */
@Controller
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    /**
     * Mapea a la vista list (que es el index de la aplicación)
     *
     * @param model
     * @return list.html (index)
     */
    @GetMapping({"/", "empleado/list"})
    public String listado(Model model) {
        model.addAttribute("listaEmpleados", empleadoService.findAll());
        return "list";
    }

    /**
     * Mapea a la vista del formulario de nuevo empleado
     *
     * @param model
     * @return form.html
     */
    @GetMapping("/empleado/new")
    public String nuevoEmpleadoForm(Model model) {
        model.addAttribute("empleadoForm", new Empleado());
        return "form";
    }

    /**
     * Mapea con post(datos form ocultos) a la vista de list (index)
     *
     * @param nuevoEmpleado
     * @return list.html (con los datos del form)
     */
    @PostMapping("/empleado/new/submit")
    public String nuevoEmpleadoSubmit(@ModelAttribute("empleadoForm") Empleado nuevoEmpleado) {
        empleadoService.add(nuevoEmpleado);
        return "redirect:/empleado/list";
    }

    @GetMapping("/empleado/edit/{id}")
    public String editarEmpleadoForm(@PathVariable long id, Model model) {
        Empleado empleado = empleadoService.findById(id);
        if (empleado != null) {
            model.addAttribute("empleadoForm", empleado);
            return "form";
        } else {
            return "redirect:/empleado/new";
        }
    }

    @PostMapping("/empleado/edit/submit")
    public String editarEmpleadoSubmit(@Validated @ModelAttribute("empleadoForm") Empleado empleado,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        } else {
            empleadoService.edit(empleado);
            return "redirect:/empleado/list";
        }
    }

}
