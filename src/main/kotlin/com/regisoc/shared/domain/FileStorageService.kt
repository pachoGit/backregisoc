package com.regisoc.shared.domain

import org.springframework.web.multipart.MultipartFile

/**
 * Servicio de almacenamiento de archivos.
 *
 * Define las operaciones necesarias para guardar y eliminar archivos
 * (como imágenes de jugadores, escudos de clubes, documentos, etc.)
 * en el sistema de almacenamiento configurado (local o en la nube).
 */
interface FileStorageService {

    /**
     * Almacena un archivo en la ruta especificada.
     *
     * @param file Archivo multimedia recibido a través de una petición HTTP.
     * @param path Ruta relativa donde se guardará el archivo dentro del sistema de almacenamiento.
     * @return La URL pública del archivo almacenado.
     */
    fun store(file: MultipartFile, path: String): String

    /**
     * Elimina un archivo del sistema de almacenamiento.
     *
     * @param url URL completa del archivo que se desea eliminar.
     */
    fun delete(url: String)
}
