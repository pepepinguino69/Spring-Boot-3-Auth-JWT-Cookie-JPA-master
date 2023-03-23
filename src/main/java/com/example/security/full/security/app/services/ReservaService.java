package com.example.security.full.security.app.services;

import com.example.security.full.security.app.model.*;
import com.example.security.full.security.app.payload.request.*;
import com.example.security.full.security.app.payload.response.ModeloResponse;


import java.sql.SQLException;
import java.util.List;

public interface ReservaService {

    Reserva saveReserva(ReservaRequest reservaRequest) throws Exception;
            //Long cliente_id, Long producto_id, LocalDateTime comienzo_reserva, LocalDateTime fin_reserva);
    List<Reserva> getAllReservas();
    Reserva getReservaById(Long id);
    Reserva updateReserva(ReservaRequest reservaRequest, Long id);
    Reserva deleteReserva(Long id);

    public List<Reserva>  getReservaXSucursal(Long sucursalId);
    Cliente saveCliente(Cliente cliente);

    List<Cliente> getAllClientes();

    Cliente getClienteById(Long id);

    Cliente updateCliente(Cliente cliente, Long id);

    Cliente deleteCliente(Long id);

    Categoria saveCategoria(CategoriaRequest categoriaResquest);

    List<Categoria> getAllCategorias();

    Categoria getCategoriaById(Long id);

    Categoria updateCategoria(Categoria categoria, Long id);

    Categoria deleteCategoria(Long id);

    Vehiculo saveVehiculo(VehiculoRequest vehiculoRequest);
    List<Vehiculo> getAllVehiculos();
    Vehiculo getVehiculoById(Long id);
    Vehiculo updateVehiculo(VehiculoRequest vehiculoRequest, Long id);
    Vehiculo deleteVehiculo(Long id);





    Ciudad saveCiudad(CiudadRequest CiudadRequest);

    List<Ciudad> getAllCiudades();

    Ciudad getCiudadById(Long id);

    Ciudad updateCiudad(CiudadRequest ciudadRequest, Long id);

    Ciudad deleteCiudad(Long id);

    List<Ciudad> getCiudadByPaisId(Long paisId);

    List <Sucursal> getSucursalByCiudadId(Long ciudadId);

    List <Vehiculo>getVehiculoBySucursalId(Long id);


    Modelo saveModelo(ModeloRequest modeloRequest);
    List<Modelo> getAllModelos();

    List<Modelo> getRandomModelos();

    List<Modelo> getModeloByNombre(String nombre);

    List<ModeloResponse> getDTOModelos();

    List<Vehiculo> getVehiculosByModeloCategoriaId(Long categoriaId);

    List<Modelo> getModeloByCategoriaId(Long categoriaId);

   Modelo getModeloById(Long id);
    Modelo updateModelo(ModeloRequest modeloRequest, Long id);
    Modelo deleteModelo(Long id);

    Pais savePais(PaisRequest nombre);

    List<Pais> getAllPaises();

    Pais getPaisById(Long id);

    Pais updatePais(Pais pais, Long id);

    Pais deletePais(Long id);

    Marca saveMarca(MarcaRequest marcaRequest) throws Exception;
    //Long cliente_id, Long producto_id, LocalDateTime comienzo_reserva, LocalDateTime fin_reserva);
    List<Marca> getAllMarcas();
    Marca getMarcaById(Long id);
    Marca updateMarca(MarcaRequest marcaRequest, Long id);
    Marca deleteMarca(Long id);

    Sucursal saveSucursal(SucursalRequest sucursalRequest) throws Exception;
    //Long cliente_id, Long producto_id, LocalDateTime comienzo_reserva, LocalDateTime fin_reserva);
    List<Sucursal> getAllSucursales();
    Sucursal getSucursalById(Long id);
    Sucursal updateSucursal(SucursalRequest sucursalRequest, Long id);
    Sucursal deleteSucursal(Long id);


    Caracteristica saveCaracteristica(CaracteristicaRequest caracteristicaRequest) throws Exception;
    //Long cliente_id, Long producto_id, LocalDateTime comienzo_reserva, LocalDateTime fin_reserva);
    List<Caracteristica> getAllCaracteristicas();
    Caracteristica getCaracteristicaById(Long id);
    Caracteristica updateCaracteristica(CaracteristicaRequest caracteristicaRequest, Long id);
    Caracteristica deleteCaracteristica(Long id);


    Imagen saveImagen(ImagenRequest imagenRequest) throws Exception;
    //Long cliente_id, Long producto_id, LocalDateTime comienzo_reserva, LocalDateTime fin_reserva);
    List<Imagen> getAllImagenes();
    Imagen getImagenById(Long id);
    Imagen updateImagen(ImagenRequest imagenRequest, Long id);
    Imagen deleteImagen(Long id);

   // List<Vehiculo>  searchBysucusalcategoria(SearchRequest searchRequest);

    List<ModeloResponse> findAllAvailableSQL(SearchAllRequest searchRequestAll, String db, String user, String root) throws SQLException;












}

