package com.sitema.blog.sistema_blog_spring_boot.controlador;


import com.sitema.blog.sistema_blog_spring_boot.dto.ComentarioDTO;
import com.sitema.blog.sistema_blog_spring_boot.servicio.ComentarioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacionId(@PathVariable(value = "publicacionId") Long publicaionId){

        return comentarioServicio.obtenerComentariosPorPublicacionId(publicaionId);

    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> obtenercomentarioPorId(@PathVariable(value = "publicacionId") Long publicacionId, @PathVariable(value = "id") Long comentarioId){

        ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId);
        return new ResponseEntity<>(comentarioDTO,HttpStatus.OK);
    }

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value = "publicacionId") Long publicaionId,@Valid @RequestBody ComentarioDTO comentarioDTO){

        return new ResponseEntity<>(comentarioServicio.crearComentario(publicaionId,comentarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> actualizarcomentario(@Valid @PathVariable(value = "publicacionId") Long publicacionId, @PathVariable(value = "id") Long comentarioId, @RequestBody ComentarioDTO comentarioDTO){
        ComentarioDTO cometarioActualizado = comentarioServicio.actualizarcomentario(publicacionId,comentarioId,comentarioDTO);

        return new ResponseEntity<>(cometarioActualizado,HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<String> eliminarComentario(@PathVariable(value = "publicacionId") Long publicacionId, @PathVariable(value = "id") Long comentarioId){
        comentarioServicio.eliminarcomentario(publicacionId,comentarioId);

        return new ResponseEntity<>("comentario eliminado con exito",HttpStatus.OK);

    }
}
