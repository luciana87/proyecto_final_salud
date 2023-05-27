package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Imagen;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImagenServicio {
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen guardar(MultipartFile archivo) throws MiException, IOException {
        if (archivo != null) {
            Imagen imagen = new Imagen();
            imagen.setMime(archivo.getContentType());
            imagen.setNombre(archivo.getName());
            imagen.setContenido(archivo.getBytes());
            return imagenRepositorio.save(imagen);
        }

        return null;
    }

    public Imagen actualizarImagen (MultipartFile archivo, String idImagen) throws MiException, IOException {
        if (archivo != null) {
            Imagen imagen = new Imagen();

            if (idImagen != null) {
                Optional<Imagen> imagenOptional = imagenRepositorio.findById(idImagen);
                if (imagenOptional.isPresent()) {
                    imagen = imagenOptional.get();
                }
            }

            imagen.setMime(archivo.getContentType());
            imagen.setNombre(archivo.getName());
            imagen.setContenido(archivo.getBytes());
            return imagenRepositorio.save(imagen);
        }
        return null;
    }
}
