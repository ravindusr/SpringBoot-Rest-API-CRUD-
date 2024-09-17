package edu.icet.crm.student.service;

import edu.icet.crm.student.dto.StudentDTO;
import edu.icet.crm.student.entity.Student;
import edu.icet.crm.student.repository.StudentRepository;
import edu.icet.crm.student.util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl  {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public String addStudent(StudentDTO studentDTO){

        if (studentRepository.existsById(studentDTO.getId())){
            return varList.RSP_DUPLICATED;
        }else {
            studentRepository.save(modelMapper.map(studentDTO, Student.class));
            return varList.RSP_SUCCESS;
        }
    }

    public String updateStudent(StudentDTO studentDTO){
        if (studentRepository.existsById(studentDTO.getId())){
            studentRepository.save(modelMapper.map(studentDTO,Student.class));
            return varList.RSP_SUCCESS;
        }else {
            return varList.RSP_NO_DATA_FOUND;
        }
    }

    public List<StudentDTO> getAllStudents(){
        List<Student> studentList = studentRepository.findAll();
        return modelMapper.map(studentList,new TypeToken<ArrayList<StudentDTO>>(){
        }.getType());
    }

    public StudentDTO serachStudent(int id){
        if (studentRepository.existsById(id)){
            Student student = studentRepository.findById(id).orElse(null);
            return modelMapper.map(student,StudentDTO.class);


        }else {
            return null;
        }
    }

    public String deleteStudent(int id){
        if (studentRepository.existsById(id)){
            studentRepository.deleteById(id);
            return varList.RSP_SUCCESS;

        }else {
            return varList.RSP_NO_DATA_FOUND;
        }
    }


}
