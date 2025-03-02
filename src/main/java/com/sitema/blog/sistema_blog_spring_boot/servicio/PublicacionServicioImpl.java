package com.sitema.blog.sistema_blog_spring_boot.servicio;

import com.sitema.blog.sistema_blog_spring_boot.dto.PublicacionDTO;
import com.sitema.blog.sistema_blog_spring_boot.dto.PublicacionRespuesta;
import com.sitema.blog.sistema_blog_spring_boot.dto.PublicacionTituloDTO;
import com.sitema.blog.sistema_blog_spring_boot.dto.ResponsePublicacionDTO;
import com.sitema.blog.sistema_blog_spring_boot.entidades.Publicacion;
import com.sitema.blog.sistema_blog_spring_boot.excepciones.ResourceNotFoundException;
import com.sitema.blog.sistema_blog_spring_boot.repositorio.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServicioImpl implements PublicacionServicio{

    @Autowired
    private ModelMapper modelMaper;
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {

        Publicacion publicacion = mapearEntidad(publicacionDTO);

        Publicacion nuvaPublicacion = publicacionRepositorio.save(publicacion);

        PublicacionDTO publicacionRespuesta = mapearDTO(nuvaPublicacion);

        return publicacionRespuesta;
    }
    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numeroDePagina,medidaDePagina, sort);

        Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

        List<Publicacion> listaDePublicaciones = publicaciones.getContent();
        List<PublicacionDTO> contenido =listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion))
                .collect(Collectors.toList());

        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuesta.setMedidaDePagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());

        return publicacionRespuesta;
    }

    //Metodo para listar titulos de publicaciones
    @Override
    public ResponsePublicacionDTO obtenerTodasLasPublicacionesT(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numeroDePagina,medidaDePagina, sort);

        Page<Publicacion> publicaciones = publicacionRepositorio.findAllBy(pageable);

        List<Publicacion> listaDePublicaciones = publicaciones.getContent();
        List<PublicacionTituloDTO> contenido =listaDePublicaciones.stream().map(publicacion -> mapearDTOt(publicacion))
                .collect(Collectors.toList());

        ResponsePublicacionDTO responsePublicacionDTO = new ResponsePublicacionDTO();
        responsePublicacionDTO.setContenido(contenido);
        responsePublicacionDTO.setNumeroPagina(publicaciones.getNumber());
        responsePublicacionDTO.setMedidaDePagina(publicaciones.getSize());
        responsePublicacionDTO.setTotalElementos(publicaciones.getTotalElements());
        responsePublicacionDTO.setTotalPaginas(publicaciones.getTotalPages());
        responsePublicacionDTO.setUltima(publicaciones.isLast());

        return responsePublicacionDTO;
    }

    @Override
    public PublicacionDTO obtenerPubicacionPorId(Long id) {

        Publicacion publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion","id",id));

        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion","id",id));

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada =publicacionRepositorio.save(publicacion);

        return mapearDTO(publicacionActualizada);
    }

    @Override
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion","id",id));

        publicacionRepositorio.delete(publicacion);
    }


    //este metodo convierte entidad a DTO
    private PublicacionDTO mapearDTO(Publicacion publicacion){

        PublicacionDTO publicacionDTO = modelMaper.map(publicacion,PublicacionDTO.class);

        return publicacionDTO;
    }

    //este metodo convierte entidad a DTO
    private PublicacionTituloDTO mapearDTOt(Publicacion publicaciont){

        PublicacionTituloDTO publicacionTituloDTO1 = modelMaper.map(publicaciont,PublicacionTituloDTO.class);

        return publicacionTituloDTO1;
    }

    //convierte de DTO a entidad
    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){

        Publicacion publicacion = modelMaper.map(publicacionDTO,Publicacion.class);

        return publicacion;
    }
}
