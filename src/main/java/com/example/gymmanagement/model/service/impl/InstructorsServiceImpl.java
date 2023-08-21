package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Classes;
import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.ClassesRepository;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.model.repository.MembersRepository;
import com.example.gymmanagement.model.service.InstructorsService;

import java.math.BigDecimal;
import java.util.List;

public class InstructorsServiceImpl implements InstructorsService {
    private final InstructorRepository instructorsRepository;
    private final MembersRepository membersRepository;
    private final ClassesRepository classesRepository;

    public InstructorsServiceImpl() {
        this.instructorsRepository = new InstructorRepository();
        this.membersRepository = new MembersRepository();
        this.classesRepository = new ClassesRepository();
    }

    @Override
    public void addInstructor(Instructors instructor) {
        instructorsRepository.addInstructor(instructor);
    }

    @Override
    public void updateInstructor(Instructors instructor) {
        instructorsRepository.updateInstructor(instructor);
    }

    @Override
    public void deleteInstructor(int instructorId) {
        instructorsRepository.updateInstructorStatus(instructorId, 4);
    }

    @Override
    public Instructors getInstructorById(int instructorId) {
        return instructorsRepository.getInstructorById(instructorId);
    }

    @Override
    public List<Instructors> getAllInstructors() {
        return instructorsRepository.getAllInstructors();
    }

    @Override
    public BigDecimal calculateSalary(int instructorId) {
        Instructors instructor = instructorsRepository.getInstructorById(instructorId);

        if (instructor != null) {
            BigDecimal baseSalary = instructor.getBaseSalary();
            int experienceYears = instructor.getExperienceYears();

            List<Members> membersList = membersRepository.getMembersByInstructorId(instructorId);
            int numberOfMembers = membersList.size();

            // Lấy số lượng lớp học từ ClassesRepository
            List<Classes> classesList = classesRepository.getClassesByInstructorId(instructorId);
            int numberOfClasses = classesList.size();


            // Tính toán lương dựa trên cơ sở thông tin
            // Lương base + số năm kinh nghiệm
            BigDecimal experienceSalary = baseSalary.multiply(BigDecimal.valueOf(experienceYears * 0.05));
            BigDecimal memberSalary = BigDecimal.valueOf(numberOfMembers * 10); // Đặt hệ số tùy ý
            BigDecimal classSalary = BigDecimal.valueOf(numberOfClasses * 20); // Đặt hệ số tùy ý

            BigDecimal totalSalary = baseSalary.add(experienceSalary).add(memberSalary).add(classSalary);


            return totalSalary;
        }

        return BigDecimal.ZERO; // Trả về 0 nếu không tìm thấy HLV
    }

    @Override
    public int getNumberOfClassesByInstructorId(int instructorId) {
        return classesRepository.getClassesByInstructorId(instructorId).size();
    }
    @Override
    public int getTotalInstructor(){return instructorsRepository.getTotalInstructor();}
}
