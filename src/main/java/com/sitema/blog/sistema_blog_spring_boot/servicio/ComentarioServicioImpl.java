package com.sitema.blog.sistema_blog_spring_boot.servicio;

import com.sitema.blog.sistema_blog_spring_boot.dto.ComentarioDTO;
import com.sitema.blog.sistema_blog_spring_boot.entidades.Comentario;
import com.sitema.blog.sistema_blog_spring_boot.entidades.Publicacion;
import com.sitema.blog.sistema_blog_spring_boot.excepciones.BlogAppExceptions;
import com.sitema.blog.sistema_blog_spring_boot.excepciones.ResourceNotFoundException;
import com.sitema.blog.sistema_blog_spring_boot.repositorio.ComentarioRepositorio;
import com.sitema.blog.sistema_blog_spring_boot.repositorio.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServicioImpl implements ComentarioServicio{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;


    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {

        Comentario comentario = mapearentidad(comentarioDTO);

        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("publicacion", "id" ,publicacionId));

        comentario.setPublicacion(publicacion);
        Comentario nuveComentario = comentarioRepositorio.save(comentario);

        return mapearDTO(nuveComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId) {

        List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);

        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("publicacion", "id" ,publicacionId));

        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppExceptions(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicacion");
        }

        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarcomentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("publicacion", "id" ,publicacionId));

        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppExceptions(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicacion");
        }

        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        Comentario comentarioActualizado = comentarioRepositorio.save(comentario);

        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarcomentario(Long publicacionId, Long comentarioId) {

        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("publicacion", "id" ,publicacionId));

        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppExceptions(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicacion");
        }

        comentarioRepositorio.delete(comentario);
    }

    private ComentarioDTO mapearDTO(Comentario comentario){

        ComentarioDTO comentarioDTO = modelMapper.map(comentario,ComentarioDTO.class);


        return comentarioDTO;
    }

    private Comentario mapearentidad(ComentarioDTO comentarioDTO){

        Comentario comentario = modelMapper.map(comentarioDTO,Comentario.class);


        return comentario;
    }
}
