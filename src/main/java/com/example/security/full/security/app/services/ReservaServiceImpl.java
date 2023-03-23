package com.example.security.full.security.app.services;
import com.example.security.full.security.app.model.*;
import com.example.security.full.security.app.exception.ResourceNotFoundException;

import com.example.security.full.security.app.payload.request.*;
import com.example.security.full.security.app.payload.response.ModeloResponse;
import com.example.security.full.security.app.repository.*;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.*;
import java.util.*;

@Repository
public class ReservaServiceImpl implements ReservaService {





    private ReservaRepository reservaRepository;
    private ClienteRepository clienteRepository;
    private VehiculoRepository vehiculoRepository;

    private CategoriaRepository categoriaRepository;

    private CiudadRepository ciudadRepository;

    private PaisRepository paisRepository;

    private ModeloRepository modeloRepository;

    private MarcaRepository marcaRepository;

    private SucursalRepository sucursalRepository;

    private CaracteristicaRepository caracteristicaRepository;

    private ImagenRepository imagenRepository;

    private EntityManager em;




    public ReservaServiceImpl( ReservaRepository reservaRepository,
                              ClienteRepository clienteRepository,
                              VehiculoRepository vehiculoRepository,
                              CategoriaRepository categoriaRepository,
                              CiudadRepository ciudadRepository,
                              PaisRepository paisRepository,
                              ModeloRepository modeloRepository,
                              MarcaRepository marcaRepository,
                              SucursalRepository sucursalRepository,
                              CaracteristicaRepository caracteristicaRepository,
                              EntityManager em,
                              ImagenRepository imagenRepository

                              ) {


        this.em = em;
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.categoriaRepository = categoriaRepository;
        this.ciudadRepository = ciudadRepository;
        this.paisRepository = paisRepository;
        this.modeloRepository = modeloRepository;
        this.marcaRepository = marcaRepository;
        this.sucursalRepository = sucursalRepository;
        this.imagenRepository = imagenRepository;
        this.caracteristicaRepository = caracteristicaRepository;

    }

    @Override
    public Reserva saveReserva(ReservaRequest reservaRequest) throws Exception {

        Reserva reserva = new Reserva();
        reserva.setVehiculo(getVehiculoById(reservaRequest.getVehiculo_id()));
        reserva.setCliente(getClienteById(reservaRequest.getCliente_id()));
        reserva.setComienzo_reserva(reservaRequest.getComienzo_reserva());
        reserva.setFin_reserva(reservaRequest.getFin_reserva());
        reserva.getCliente().addReserva(reserva);
        reserva.getVehiculo().addReserva(reserva);
        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva getReservaById(Long id) {
        return reservaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Reserva", "id", id));
    }

    @Override
    public Reserva updateReserva(ReservaRequest reservaRequest, Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserva", "id", id));
        reserva.setVehiculo(getVehiculoById(reservaRequest.getVehiculo_id()));
        reserva.setCliente(getClienteById(reservaRequest.getCliente_id()));
        reserva.setVehiculo(getVehiculoById((reservaRequest.getVehiculo_id())));
        reserva.setComienzo_reserva(reservaRequest.getComienzo_reserva());
        reserva.setFin_reserva(reservaRequest.getFin_reserva());
        return reservaRepository.save(reserva);

    }

    @Override
    public Reserva deleteReserva(Long id) {
        Reserva existingReserva = reservaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Reserva", "id", id));
        reservaRepository.deleteById(id);
        return existingReserva;


    }

    @Override
    public List<Reserva> getReservaXSucursal(Long sucursalId) {
        List<Reserva> reservasBySucursal = new ArrayList<>();
        for (Reserva element : reservaRepository.findAll()) {

            if (element.getVehiculo().getSucursal().getId() == sucursalId) {
                reservasBySucursal.add(element);
            }
        }
        return reservasBySucursal;
    };

    @Override
    public List<ModeloResponse> findAllAvailableSQL(SearchAllRequest searchAllRequest, String db, String user, String pass) throws SQLException

    {   List<ModeloResponse> results =new ArrayList<>();
        ModeloResponse modeloResponse= new ModeloResponse();
        modeloResponse.setModelo_id(1L);
        results.add(modeloResponse);




        Connection conexion= DriverManager.getConnection(db , user,  pass);



        String joinCategoria=" join categoria on modelo.categoria_id=categoria.id ";
        String joinSucursal = " join sucursal on vehiculo.sucursal_id=sucursal.id ";
        String joinFecha="";
        String categoria = "categoria_id=? and ";
        String sucursal = "sucursal_id=? and ";
        String criteriaFecha="";
        String consulta="";
        String criteriaCaracteristicas="";
        int param = 0;
        if (searchAllRequest.getCaracteristicas().size()!=0) {
            String createMyTempTable = "CREATE TEMPORARY TABLE myTempTable (id bigint) ENGINE=MEMORY;";

            String createBusqueda = "create temporary table busqueda(id bignint,busqueda_id varchar(20) ENGINE=MEMORY;";

            String createBusquedaDetalle = "create temporary table busquedaDetalle(id bignint,busqueda_id varchar(20)," +
                    "caracteristica_id bigint) ENGINE=MEMORY;";

            String getModelos = "insert INTO myTempTable select modelo.id" +
                    " from busquedaDetalle join caracteristicas on busquedaDetalle.caracteristica_id" +
                    "= caracteristicas.caracteristica_id " + "join modelo on modelo.id = caracteristicas.model_id" +
                    " where busquedaDetalle.busqueda_id=? group by modelo.id " +
                    "having count(busquedaDetalle.caracteristica_id) = (SELECT COUNT(*)  from busquedaDetalle " +
                    "where busquedaDetalle.busqueda_id=? order by modelo.id) ;";

            String filtroDTO = "select * from myTempTable;";
            consulta = consulta + createMyTempTable + createBusqueda + createBusquedaDetalle + getModelos + filtroDTO;
            criteriaCaracteristicas ="modelo.id in (select * from myTempTable) and ";
        }
        if(searchAllRequest.getCategoriaId()==0){
             categoria="";
             joinCategoria="";}
        if(searchAllRequest.getSucursalId()==0){
             sucursal="";
             joinSucursal="";}
        if (!Objects.isNull(searchAllRequest.getComienzo_reserva())){
            joinFecha="left join reserva on reserva.vehiculo_id=vehiculo.id";
            criteriaFecha="(vehiculo_id is null or vehiculo_id not in  (select vehiculo_id from reserva where " +
                    "((comienzo_reserva>?and fin_reserva<?))));";

        }

        String  regularJoins="Select * from vehiculo join modelo on vehiculo.modelo_id = modelo.id"+joinSucursal+joinCategoria+joinFecha+" where ";
        ;
        consulta = consulta+regularJoins+sucursal+categoria+criteriaCaracteristicas+criteriaFecha;

        PreparedStatement sentencia= conexion.prepareStatement(consulta);
        if (joinSucursal != "") {
            param++;
            sentencia.setLong(param,searchAllRequest.getSucursalId() );
        }
        if (joinCategoria != "") {
            param++;
            sentencia.setLong(param,searchAllRequest.getSucursalId() );
        }
        if (criteriaFecha != "") {
            param++;
            sentencia.setDate(param, Date.valueOf(searchAllRequest.getComienzo_reserva().toLocalDate()));
            param++;
            sentencia.setDate(param, Date.valueOf(searchAllRequest.getFin_reserva().toLocalDate()));
        }


        ResultSet myRs= sentencia.executeQuery();
        return results;




    };






















    @Override
    public Cliente saveCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }
    @Override
    public List<Cliente> getAllClientes(){
        return clienteRepository.findAll();
    }
    @Override
    public Cliente getClienteById(Long id){
        return clienteRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cliente","id",id));
    }
    @Override
    public Cliente updateCliente(Cliente cliente, Long id){
        Cliente existingCliente= clienteRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cliente","id",id));
        clienteRepository.save(existingCliente);
        return existingCliente;


    }
    @Override
    public Cliente deleteCliente(Long id){
        Cliente existingCliente= clienteRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cliente","id",id));
        clienteRepository.deleteById(id);
        return existingCliente;


    }


    @Override
    public Vehiculo saveVehiculo(VehiculoRequest vehiculoRequest){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setModelo(getModeloById(vehiculoRequest.getModeloId()));
        vehiculo.setPatente(vehiculoRequest.getPatente());
        vehiculo.setKilometraje(vehiculoRequest.getKilometraje());
        vehiculo.setDisponible(vehiculoRequest.getDisponible());
        vehiculo.setPais(getPaisById(vehiculoRequest.getPaisId()));
        vehiculo.getPais().addVehiculo(vehiculo);
        return vehiculoRepository.save(vehiculo);



    }
    @Override
    public List<Vehiculo> getAllVehiculos(){
        return vehiculoRepository.findAll();
    }
    @Override
    public Vehiculo getVehiculoById(Long id){
        return vehiculoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehiculo","id",id));
    }
    @Override
    public Vehiculo updateVehiculo(VehiculoRequest vehiculoRequest, Long id){
        Vehiculo existingVehiculo= vehiculoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehiculo","id",id));
        existingVehiculo.setPais(getPaisById(vehiculoRequest.getPaisId()));
        existingVehiculo.setModelo(getModeloById(vehiculoRequest.getPaisId()));
        existingVehiculo.setKilometraje(vehiculoRequest.getKilometraje());
        existingVehiculo.setPatente(vehiculoRequest.getPatente());
        vehiculoRepository.save(existingVehiculo);
        return existingVehiculo;

    }
    @Override
    public Vehiculo deleteVehiculo(Long id){
        Vehiculo existingVehiculo= vehiculoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehiculo","id",id));
        vehiculoRepository.deleteById(id);
        return existingVehiculo;
    }

    @Override
    public List<Vehiculo> getVehiculoBySucursalId(Long sucursalId){
        List<Vehiculo> vehiculos = vehiculoRepository.findVehiculoBySucursalId(sucursalId);
        return vehiculos;
    }


        @Override
        public Categoria saveCategoria(CategoriaRequest categoriaRequest){

            Categoria categoria= new Categoria();

            categoria.setDescripcion(categoriaRequest.getDescripcion());
            categoria.setUrlImagen(categoriaRequest.getUrlImagen());
            categoria.setNombre(categoriaRequest.getNombre());
            categoriaRepository.save(categoria);
            return categoria;
        }
        @Override
        public List<Categoria> getAllCategorias(){
            return categoriaRepository.findAll();
        }
        @Override
        public Categoria getCategoriaById(Long id){
            return categoriaRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Categoria","id",id));
        }
        @Override
        public Categoria updateCategoria(Categoria categoria, Long id){
            Categoria existingCategoria= categoriaRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Vehiculo","id",id));
            existingCategoria.setNombre(categoria.getNombre());
            existingCategoria.setDescripcion(categoria.getDescripcion());
            existingCategoria.setUrlImagen(categoria.getUrlImagen());
            categoriaRepository.save(existingCategoria);
            return existingCategoria;


        }
        @Override
        public Categoria deleteCategoria(Long id){
            Categoria existingCategoria= categoriaRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Categoria","id",id));
            categoriaRepository.deleteById(id);
            return existingCategoria;


        }

    @Override
    public Ciudad saveCiudad(CiudadRequest ciudadRequest){
        Ciudad ciudad = new Ciudad();
        ciudad.setPais(getPaisById(ciudadRequest.getPaisId()));
        ciudad.setNombre(ciudadRequest.getNombre());
        getPaisById(ciudadRequest.getPaisId()).addCiudad(ciudad);
        ciudadRepository.save(ciudad);

        return ciudad;
    }
    @Override
    public List<Ciudad> getAllCiudades(){
        return ciudadRepository.findAll();
    }
    @Override
    public Ciudad getCiudadById(Long id){
        return ciudadRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ciudad","id",id));

    }
    @Override
    public List<Ciudad> getCiudadByPaisId(Long paisId){
        return ciudadRepository.findByPaisId(paisId);}

    @Override
    public List<Sucursal>  getSucursalByCiudadId(Long ciudadId){

        return sucursalRepository.findSucursalByCiudadId(ciudadId);}



    @Override
    public Ciudad updateCiudad(CiudadRequest ciudadRequest, Long id){
        Ciudad existingCiudad= ciudadRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ciudaidd","id",id));
        existingCiudad.setNombre(ciudadRequest.getNombre());
        existingCiudad.setPais(getPaisById(ciudadRequest.getPaisId()));
        ciudadRepository.save(existingCiudad);
        return existingCiudad;

    }
    @Override
    public Ciudad deleteCiudad(Long id){
        Ciudad existingCiudad= ciudadRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ciudad","id",id));
        ciudadRepository.deleteById(id);
        return existingCiudad;}



    @Override
    public Modelo saveModelo(ModeloRequest modeloRequest)  {
        Modelo modelo = new Modelo();
        modelo.setNombre(modeloRequest.getNombre());
        modelo.setDescripcion(modeloRequest.getDescripcion());
        modelo.setMarca(getMarcaById(modeloRequest.getMarca_id()));
        modelo.setPuntuacion(modeloRequest.getPuntuacion());
        Set<Caracteristica> newCaracteristicas = new HashSet<>();
        for (Long element: modeloRequest.getCaracteristicas()){
            newCaracteristicas.add(getCaracteristicaById(element));}
        modelo.setCaracteristicas(newCaracteristicas);
        getMarcaById(modeloRequest.getMarca_id()).addModelo(modelo);
        getCategoriaById(modeloRequest.getCategoria_id()).addModelo(modelo);
        modeloRepository.save(modelo);
        return modelo;
    }

    @Override
    public List<Modelo> getAllModelos(){
        return modeloRepository.findAll();
    }

    @Override
    public List<Vehiculo> getVehiculosByModeloCategoriaId(Long categoriaId){return vehiculoRepository.findVehiculoByModeloCategoriaId(categoriaId);}

@Override
    public List<ModeloResponse> getDTOModelos(){
        List<ModeloResponse> modeloResponses= new ArrayList<>();
        for (Modelo element:modeloRepository.findAll()){
            ModeloResponse elementResponse = new ModeloResponse();
            elementResponse.setModelo_id(element.getId());
            elementResponse.setMarca(element.getMarca().getNombre());
            elementResponse.setMarca_id(element.getMarca().getId());
            elementResponse.setNombre(element.getNombre());
            elementResponse.setDescripcion(element.getDescripcion());
            elementResponse.setPuntuacion(element.getPuntuacion());
            elementResponse.setCategoria_id(element.getCategoria().getId());
            elementResponse.setCategoria(element.getCategoria().getNombre());
            elementResponse.setCaracteristicas(element.getCaracteristicas());
            elementResponse.setImagenes(element.getModelosIma());
            modeloResponses.add(elementResponse);
        }
        return modeloResponses;
        }










    @Override
    public List<Modelo> getModeloByNombre(String nombre){
        return modeloRepository.findByNombre(nombre);
    }

    @Override
    public List<Modelo> getModeloByCategoriaId(Long categoriaId){
        List<Modelo> modelos = modeloRepository.findByCategoriaId(categoriaId);
        return modelos;
    }

    @Override
    public List<Modelo> getRandomModelos(){
        List<Modelo> modelos = new ArrayList<>();
        List<Modelo> modelosRandom = new ArrayList<>();
        modelos = modeloRepository.findAll();
        for (int i = 0; i <= 4; i++ ){
            Random random = new Random();
            int upperbound = modelos.size();
            int int_random = random.nextInt(upperbound);
            modelosRandom.add(modelos.get(int_random));
        }
        return modelosRandom;
    }

    @Override
    public Modelo getModeloById(Long id){
        return modeloRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Modelo","id",id));
    }

    @Override
    public Modelo updateModelo(ModeloRequest modeloRequest, Long id) {
        Modelo existingModelo = modeloRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Modelo", "id", id));
        existingModelo.setMarca(getMarcaById(modeloRequest.getMarca_id()));
        existingModelo.setNombre(modeloRequest.getNombre());
        existingModelo.setDescripcion(modeloRequest.getDescripcion());
        existingModelo.setPuntuacion(modeloRequest.getPuntuacion());
        Set<Caracteristica> newCaracteristicas = new HashSet<>();
        for (Long element : modeloRequest.getCaracteristicas()) {
            newCaracteristicas.add(getCaracteristicaById(element));
        }
        existingModelo.setCaracteristicas(newCaracteristicas);
        modeloRepository.save(existingModelo);
        return existingModelo;
    }


    @Override
    public Modelo deleteModelo(Long id){
        Modelo existingModelo= modeloRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Modelo","id",id));
        modeloRepository.deleteById(id);
        return existingModelo;


    }

    @Override
    public Pais savePais(PaisRequest nombre){

        Pais pais= new Pais();
        pais.setNombre(nombre.getNombre());
        paisRepository.save(pais);
        return pais;
    }
    @Override
    public List<Pais> getAllPaises(){
        return paisRepository.findAll();
    }

    @Override
    public Pais getPaisById(Long id){
        return paisRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Pais","id",id));
    }
    @Override
    public Pais updatePais(Pais pais, Long id){
        Pais existingPais= paisRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehiculo","id",id));
        existingPais.setNombre(pais.getNombre());
        paisRepository.save(existingPais);
        return existingPais;


    }
    @Override
    public Pais deletePais(Long id) {
        Pais existingPais = paisRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Pais", "id", id));
        paisRepository.deleteById(id);
        return existingPais;


    }

    @Override
    public Marca saveMarca(MarcaRequest marcaRequest){

        Marca  marca= new Marca();
        marca.setNombre(marcaRequest.getNombre());
        marcaRepository.save(marca);

        return marca;
    }
    @Override
    public List<Marca> getAllMarcas(){
        return marcaRepository.findAll();
    }
    @Override
    public Marca getMarcaById(Long id){
        return marcaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Marca","id",id));
    }
    @Override
    public Marca updateMarca(MarcaRequest marcaRequest , Long id){
        Marca existingMarca=marcaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Marca","id",id));
        existingMarca.setNombre(marcaRequest.getNombre());
       marcaRepository.save(existingMarca);
        return existingMarca;


    }
    @Override
    public Marca deleteMarca(Long id) {
        Marca existingMarca =marcaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Marca", "id", id));
       marcaRepository.deleteById(id);
        return existingMarca;
    }

    @Override
    public Sucursal saveSucursal(SucursalRequest sucursalRequest){
        Sucursal sucursal = new Sucursal();
        sucursal.setCiudad(getCiudadById(sucursalRequest.getCiudadId()));
        sucursal.setNombre(sucursalRequest.getNombre());
        sucursal.setDireccion(sucursalRequest.getDireccion());
        sucursal.setLatitud(sucursalRequest.getLatitud());
        sucursal.setLongitud(sucursalRequest.getLongitud());
        sucursal.getCiudad().addSucursal(sucursal);


        return sucursalRepository.save(sucursal);
    }
    @Override
    public List<Sucursal> getAllSucursales(){
        return sucursalRepository.findAll();
    }
    @Override
    public Sucursal getSucursalById(Long id){
        return sucursalRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Sucursal","id",id));
    }
    @Override
    public Sucursal updateSucursal(SucursalRequest sucursalRequest, Long id){
        Sucursal existingSucursal= sucursalRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Sucursalidd","id",id));
        existingSucursal.setNombre(sucursalRequest.getNombre());
        existingSucursal.setCiudad(getCiudadById(sucursalRequest.getCiudadId()));
        sucursalRepository.save(existingSucursal);
        return existingSucursal;

    }
    @Override
    public Sucursal deleteSucursal(Long id){
        Sucursal existingSucursal= sucursalRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Sucursal","id",id));
        sucursalRepository.deleteById(id);
        return existingSucursal;}


    @Override
    public Caracteristica saveCaracteristica(CaracteristicaRequest nombre){

        Caracteristica caracteristica= new Caracteristica();
        caracteristica.setNombre(nombre.getNombre());
        caracteristicaRepository.save(caracteristica);
        return caracteristica;
    }
    @Override
    public List<Caracteristica> getAllCaracteristicas(){
        return caracteristicaRepository.findAll();
    }
    @Override
    public Caracteristica getCaracteristicaById(Long id){
        return caracteristicaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Caracteristica","id",id));
    }
    @Override
    public Caracteristica updateCaracteristica(CaracteristicaRequest caracteristicaRequest, Long id){
        Caracteristica existingCaracteristica= caracteristicaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehiculo","id",id));
        existingCaracteristica.setNombre(caracteristicaRequest.getNombre());
        existingCaracteristica.setUrlIcono(caracteristicaRequest.getUrlIcono());

        caracteristicaRepository.save(existingCaracteristica);
        return existingCaracteristica;


    }
    @Override
    public Caracteristica deleteCaracteristica(Long id) {
        Caracteristica existingCaracteristica = caracteristicaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Caracteristica", "id", id));
        caracteristicaRepository.deleteById(id);
        return existingCaracteristica;


    }

    @Override
    public Imagen saveImagen(ImagenRequest imagenRequest){

        Imagen imagen= new Imagen();
        imagen.setNombre(imagenRequest.getNombre());
        imagen.setUrlImagen(imagenRequest.getUrlImagen());
        getModeloById(imagenRequest.getModeloId()).addImagen(imagen);
        imagenRepository.save(imagen);
        return imagen;
    }
    @Override
    public List<Imagen> getAllImagenes(){
        return imagenRepository.findAll();
    }
    @Override
    public Imagen getImagenById(Long id){
        return imagenRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Imagen","id",id));
    }
    @Override
    public Imagen updateImagen(ImagenRequest imagenRequest, Long id){
        Imagen existingImagen= imagenRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Imagen","id",id));
        existingImagen.setNombre(imagenRequest.getNombre());
        existingImagen.setUrlImagen(imagenRequest.getUrlImagen());
        imagenRepository.save(existingImagen);
        return existingImagen;


    }
    @Override
    public Imagen deleteImagen(Long id) {
        Imagen existingImagen = imagenRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Imagen", "id", id));
        imagenRepository.deleteById(id);
        return existingImagen;


    }





}

