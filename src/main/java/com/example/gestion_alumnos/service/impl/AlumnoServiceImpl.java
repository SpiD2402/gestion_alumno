package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.request.RequestAlumno;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.agregates.response.ResponseReniec;
import com.example.gestion_alumnos.config.RedisService;
import com.example.gestion_alumnos.dao.AlumnoDao;
import com.example.gestion_alumnos.entity.Alumno;
import com.example.gestion_alumnos.feignClient.ReniecClient;
import com.example.gestion_alumnos.service.AlumnoService;
import com.example.gestion_alumnos.util.AlumnoValidate;
import com.example.gestion_alumnos.util.UtilRedis;
import jdk.jshell.execution.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoValidate alumnoValidate;
    private final AlumnoDao alumnoDao;
    private final ReniecClient reniecClient;


    private final RedisService redisService;

    public AlumnoServiceImpl(AlumnoValidate alumnoValidate, AlumnoDao alumnoDao, ReniecClient reniecClient,  RedisService redisService) {
        this.alumnoValidate = alumnoValidate;
        this.alumnoDao = alumnoDao;
        this.reniecClient = reniecClient;

        this.redisService = redisService;
    }
    @Value("${token.api.reniec}")
    private String tokenReniec;

    @Value("${time.expiration.sunat.info}")
    private String timeExpirationSunactInfo;
    @Override
    public ResponseBase findAllStudent() {
        List<Alumno> alumnosAll = alumnoDao.findAll().stream().filter(alumno -> alumno.getStatus()==1).collect(Collectors.toList());
        Optional<List<Alumno>> oAlumno = Optional.of(alumnosAll);
        if (oAlumno.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,oAlumno);

        }
        else {
            return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ZERO_ROWS,Optional.empty());

        }
    }

    @Override
    public ResponseBase findByIdStudent(Long id) {
        log.info("Intentando recuperar datos de la caché para el ID: {}", id);
        String redisCache = redisService.getValueByKey("NC:GESTION:ALUMNO:" + id);
        if (redisCache!=null)
        {
            log.info("Datos recuperados de la caché: {}", redisCache);
            return UtilRedis.convertFromJson(redisCache,ResponseBase.class);
        }
       else{
            Optional<Alumno> alum = alumnoDao.findById(id);
            String redisData = UtilRedis.convertToJsonEntity(alum.get());
            redisService.saveKeyValue("NC:GESTION:ALUMNO:"+id,redisData,Integer.parseInt(timeExpirationSunactInfo));
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,alum);
        }
    }

    @Override
    public ResponseBase updateStudent(RequestAlumno alumno, Long id) {
        boolean existAlumno = alumnoDao.existsById(id);
        if (existAlumno)
        {
            Optional<Alumno> alumnoEncontrado = alumnoDao.findById(id);
            boolean valido = alumnoValidate.validarAlumnoUpdate(alumno);
            if (!valido)
            {
                Alumno alumnoEn = getAlumno(alumno,true,alumnoEncontrado.get());
                alumnoEn.setApellidos(alumno.getApellidos());
                alumnoEn.setNombres(alumno.getNombres());
                alumnoDao.save(alumnoEn);
                return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(alumnoEn));
            }
            else {
                return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.of("Campos null o vacios"));

            }
        }

        return  new ResponseBase(Constants.CODE_ERROR_EXIST,Constants.MESS_NON_DATA,Optional.empty());

    }

    @Override
    public ResponseBase addStudent(RequestAlumno alumno) {
        boolean validateStudent = alumnoValidate.validarAlumno(alumno);
        if (!validateStudent)
        {
            Alumno alumnoNew= getReniecAlumno(alumno);
            alumnoDao.save(alumnoNew);
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(alumnoNew));

        }
        else {
            return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.empty());

        }
    }

    @Override
    public ResponseBase deleteStudent(Long id) {
        Optional<Alumno> alumnoEn = alumnoDao.findById(id);
        if (alumnoEn.isPresent())
        {
            Alumno alumno = alumnoEn.get();
            alumno.setStatus(0);
            alumno.setDateDelete(timestamp());
            alumno.setUserDelete("User");
            alumnoDao.save(alumno);
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of("Alumno Deshabilitado exitosamente"));

        }
        else {
            return new ResponseBase(Constants.CODE_ERROR_EXIST,Constants.MESS_NON_DATA,Optional.empty());

        }
    }

    public Timestamp timestamp ()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    public Alumno getAlumno(RequestAlumno requestAlumno,boolean isUpdate,Alumno alumno)
    {
        if (!isUpdate)
        {
            alumno.setDateCreate(timestamp());
            alumno.setUserCreate("User");
            alumno.setStatus(1);
        }
        if (isUpdate)
        {
            alumno.setDateModif(timestamp());
            alumno.setUserModif("User");
            alumno.setStatus(1);
        }
        return alumno;
    }


    private Alumno getReniecAlumno(RequestAlumno requestAlumno)
    {
        ResponseReniec reniec =getExecutionReniec(requestAlumno.getDni());
        Alumno alumno = new Alumno();
        if (reniec!=null)
        {
            alumno.setDni(reniec.getNumeroDocumento());
            alumno.setNombres(reniec.getNombres());
            alumno.setApellidos(reniec.getApellidoPaterno()+" "+reniec.getApellidoMaterno());

        }
        else {
            return  null;
        }
        return getAlumno(requestAlumno,false,alumno);

    }


    private ResponseReniec getExecutionReniec(String numero)
    {
        String authorization ="Bearer "+tokenReniec;
        return reniecClient.getInfoReniec(numero,authorization);
    }




}
