package edu.icet.crm.student.controller;

import edu.icet.crm.student.dto.ResponseDTO;
import edu.icet.crm.student.dto.StudentDTO;
import edu.icet.crm.student.service.StudentServiceImpl;
import edu.icet.crm.student.util.varList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentServiceImpl service;

    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping("/create")
     public ResponseEntity addStudent(@RequestBody StudentDTO studentDTO){
         try{
            String res = service.addStudent(studentDTO);
            if (res.equals("00")){
                responseDTO.setCode(varList.RSP_SUCCESS);
                responseDTO.setMassage("Success");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals("06")) {
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMassage("Student Already Registerd");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }else {
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setMassage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
         }catch (Exception ex){
             responseDTO.setCode(varList.RSP_ERROR);
             responseDTO.setMassage(ex.getMessage());
             responseDTO.setContent(null);
             return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

    @PutMapping("/update")
    public ResponseEntity updateStudent(@RequestBody StudentDTO studentDTO){
        try{
            String res = service.updateStudent(studentDTO);
            if (res.equals("00")){
                responseDTO.setCode(varList.RSP_SUCCESS);
                responseDTO.setMassage("Success");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals("01")) {
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setMassage("Student Not Found ");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }else {
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setMassage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setMassage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllStudents(){
        try {
            List<StudentDTO> studentDTOList = service.getAllStudents();
            responseDTO.setCode(varList.RSP_SUCCESS);
            responseDTO.setMassage("Success");
            responseDTO.setContent(studentDTOList);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception ex){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setMassage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchStudent/{id}")
    public  ResponseEntity serachStudent(@PathVariable int id){
        try{
            StudentDTO studentDTO = service.serachStudent(id);
            if (studentDTO !=null){
                responseDTO.setCode(varList.RSP_SUCCESS);
                responseDTO.setMassage("Success");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setMassage("No Student Available for this id ");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setMassage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity deleteStudent(@PathVariable int id){
        try{
            String  res = service.deleteStudent(id);
            if (res.equals("00")){
                responseDTO.setCode(varList.RSP_SUCCESS);
                responseDTO.setMassage("Success");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setMassage("No Student Available for this id ");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setMassage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
